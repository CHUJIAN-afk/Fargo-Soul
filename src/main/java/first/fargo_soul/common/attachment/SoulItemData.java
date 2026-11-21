package first.fargo_soul.common.attachment;

import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.register.FargoSoulAttachmentRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.*;
import java.util.function.Consumer;

public class SoulItemData implements AttachmentSyncHandler<SoulItemData>, INBTSerializable<CompoundTag> {

    private final Set<SoulItem> roots = new LinkedHashSet<>();
    private final Set<SoulItem> equipped = new HashSet<>();
    private final Set<SoulItem> disabled = new HashSet<>();
    private final Set<SoulItem> cache = new HashSet<>();
    private boolean syncCache = false;
    private boolean canSprint = false;
    private int maxFlyTime = 0;
    private boolean renderLavaFog = true;
    private boolean renderFireOverlay = true;

    public Set<SoulItem> getCache() {
        return cache;
    }

    public Set<SoulItem> getRoots() {
        return roots;
    }

    public Set<SoulItem> getEquipped() {
        return equipped;
    }

    public Set<SoulItem> getDisabled() {
        return disabled;
    }

    public int getMaxFlyTime() {
        return maxFlyTime;
    }

    public boolean isRenderLavaFog() {
        return renderLavaFog;
    }

    public boolean isCanSprint() {
        return canSprint;
    }

    public static boolean isEquipped(LivingEntity living, ItemLike itemLike) {
        if (living instanceof Player player && player.isAlive() && itemLike.asItem() instanceof SoulItem soulItem) {
            SoulItemData data = player.getData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA);
            return data.getCache().contains(soulItem);
        }
        return false;
    }

    /** 判断玩家是否实际装备了指定魂石（包含被关闭的子魂石），用于防止重复装备。 */
    public static boolean hasEquipped(LivingEntity living, ItemLike itemLike) {
        if (living instanceof Player player && player.isAlive() && itemLike.asItem() instanceof SoulItem soulItem) {
            SoulItemData data = player.getData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA);
            return data.getEquipped().contains(soulItem);
        }
        return false;
    }

    public static void forEach(Player player, Consumer<SoulItem> consumer) {
        SoulItemData data = player.getData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA);
        data.getCache().forEach(consumer);
    }

    public void tick(Player player) {
        canSprint = false;
        maxFlyTime = 0;
        renderLavaFog = true;
        renderFireOverlay = true;
        Collection<AttributeInstance> attributes = player.getAttributes().getSyncableAttributes();
        for (AttributeInstance attribute : attributes) {
            Set<AttributeModifier> modifiers = attribute.getModifiers();
            for (AttributeModifier modifier : modifiers) {
                if (modifier.id().getPath().contains("_volatile")) {
                    attribute.removeModifier(modifier);
                }
            }
        }
        boolean anyFly = false;
        List<ValueModifier> flyModifiers = new ArrayList<>();
        for (SoulItem soulItem : cache) {
            if (!canSprint && soulItem.canSprint(player)) {
                canSprint = true;
            }
            if (soulItem.canFly(player)) {
                anyFly = true;
            }
            soulItem.getMaxFlyTime(player, flyModifiers);
            if (renderLavaFog && !soulItem.renderLavaFog(player)) {
                renderLavaFog = false;
            }
            if (renderFireOverlay && !soulItem.renderFireOverlay(player)) {
                renderFireOverlay = false;
            }
            soulItem.getAttributeModifiers(player).forEach((soulAttribute, attributeModifier) -> {
                AttributeInstance attribute = player.getAttribute(soulAttribute);
                if (attribute != null) {
                    AttributeModifier volatileModifier = new AttributeModifier(attributeModifier.id().withSuffix("_volatile"), attributeModifier.amount(), attributeModifier.operation());
                    if (attribute.hasModifier(volatileModifier.id())) {
                        attribute.removeModifier(volatileModifier.id());
                    }
                    attribute.addPermanentModifier(volatileModifier);
                }
            });
        }
        if (anyFly) {
            player.fallDistance = 0;
            maxFlyTime = (int) ValueModifier.getModifierAfter(100f, flyModifiers);
        }
        player.syncData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA);
        syncCache = false;
    }

    public void update(Player player) {
        roots.clear();
        ICuriosItemHandler handler = CuriosApi.getCuriosInventory(player).orElse(null);
        if (handler != null) {
            IItemHandlerModifiable curios = handler.getEquippedCurios();
            int slots = handler.getSlots();
            for (int i = 0; i < slots; i++) {
                ItemStack itemStack = curios.getStackInSlot(i);
                if (!itemStack.isEmpty() && itemStack.getItem() instanceof SoulItem soulItem) {
                    roots.add(soulItem);
                }
            }
        }
        rebuildCache();
        syncCache = true;
    }

    /** 更新关闭列表并立即刷新魂石激活缓存与关联玩家数据。 */
    public void applyDisabled(Player player, Collection<SoulItem> disabledSouls) {
        disabled.clear();
        disabled.addAll(disabledSouls);
        if (player.level().isClientSide()) {
            rebuildCache();
        } else {
            update(player);
            tick(player);
        }
    }

    private void rebuildCache() {
        equipped.clear();
        for (SoulItem root : roots) {
            flatten(root, equipped);
        }
        cache.clear();
        for (SoulItem soulItem : equipped) {
            if (!disabled.contains(soulItem)) {
                cache.add(soulItem);
            }
        }
    }

    private static void flatten(SoulItem soulItem, Set<SoulItem> result) {
        if (result.add(soulItem)) {
            for (SoulItem child : soulItem.getSoulItemList()) {
                flatten(child, result);
            }
        }
    }

    private static void writeSoulSet(RegistryFriendlyByteBuf buf, Set<SoulItem> set) {
        buf.writeVarInt(set.size());
        for (SoulItem soulItem : set) {
            buf.writeResourceLocation(BuiltInRegistries.ITEM.getKey(soulItem));
        }
    }

    private static void readSoulSet(RegistryFriendlyByteBuf buf, Set<SoulItem> target) {
        target.clear();
        int size = buf.readVarInt();
        for (int i = 0; i < size; i++) {
            Item item = BuiltInRegistries.ITEM.get(buf.readResourceLocation());
            if (item instanceof SoulItem soulItem) {
                target.add(soulItem);
            }
        }
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();
        ListTag list = new ListTag();
        for (SoulItem soulItem : disabled) {
            list.add(StringTag.valueOf(BuiltInRegistries.ITEM.getKey(soulItem).toString()));
        }
        tag.put("disabled", list);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
        disabled.clear();
        ListTag list = tag.getList("disabled", Tag.TAG_STRING);
        for (int i = 0; i < list.size(); i++) {
            ResourceLocation id = ResourceLocation.tryParse(list.getString(i));
            if (id != null && BuiltInRegistries.ITEM.get(id) instanceof SoulItem soulItem) {
                disabled.add(soulItem);
            }
        }
        rebuildCache();
    }

    @Override
    public void write(@NotNull RegistryFriendlyByteBuf registryFriendlyByteBuf, @NotNull SoulItemData soulItemData, boolean initialSync) {
        boolean sendFull = initialSync || soulItemData.syncCache;
        registryFriendlyByteBuf.writeBoolean(sendFull);
        if (sendFull) {
            writeSoulSet(registryFriendlyByteBuf, soulItemData.roots);
            writeSoulSet(registryFriendlyByteBuf, soulItemData.disabled);
        }
        registryFriendlyByteBuf.writeBoolean(soulItemData.canSprint);
        registryFriendlyByteBuf.writeInt(soulItemData.maxFlyTime);
        registryFriendlyByteBuf.writeBoolean(soulItemData.renderLavaFog);
        registryFriendlyByteBuf.writeBoolean(soulItemData.renderFireOverlay);
    }

    @Override
    public @NotNull SoulItemData read(@NotNull IAttachmentHolder iAttachmentHolder, @NotNull RegistryFriendlyByteBuf registryFriendlyByteBuf, @Nullable SoulItemData soulItemData) {
        SoulItemData data = soulItemData != null ? soulItemData : new SoulItemData();
        if (registryFriendlyByteBuf.readBoolean()) {
            readSoulSet(registryFriendlyByteBuf, data.roots);
            readSoulSet(registryFriendlyByteBuf, data.disabled);
            data.rebuildCache();
        }
        data.canSprint = registryFriendlyByteBuf.readBoolean();
        data.maxFlyTime = registryFriendlyByteBuf.readInt();
        data.renderLavaFog = registryFriendlyByteBuf.readBoolean();
        data.renderFireOverlay = registryFriendlyByteBuf.readBoolean();
        return data;
    }
}
