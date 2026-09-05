package first.fargo_soul.common.attachment;

import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.register.FargoSoulAttachmentRegister;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class SoulItemData implements AttachmentSyncHandler<SoulItemData> {

    private final Set<SoulItem> cache = new HashSet<>();
    private boolean syncCache = false;
    private boolean canSprint = false;
    private int maxFlyTime = 0;
    private boolean renderLavaFog = true;
    private boolean renderFireOverlay = true;

    public Set<SoulItem> getCache() {
        return cache;
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

    public static void forEach(Player player, Consumer<SoulItem> consumer) {
        SoulItemData data = player.getData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA);
        data.getCache().forEach(consumer);
    }

    public void tick(Player player){
        canSprint = false;
        maxFlyTime = 0;
        renderLavaFog = true;
        renderFireOverlay = true;
        for (SoulItem soulItem : cache) {
            if (!canSprint && soulItem.canSprint(player)) {
                canSprint = true;
            }
            if (renderLavaFog && !soulItem.renderLavaFog(player)) {
                renderLavaFog = false;
            }
            if (renderFireOverlay && !soulItem.renderFireOverlay(player)) {
                renderFireOverlay = false;
            }
        }
        player.syncData(FargoSoulAttachmentRegister.SOUL_ITEM_DATA);
    }

    public void update(Player player) {
        cache.clear();
        ICuriosItemHandler handler = CuriosApi.getCuriosInventory(player).orElse(null);
        if (handler != null) {
            IItemHandlerModifiable curios = handler.getEquippedCurios();
            int slots = handler.getSlots();
            Set<SoulItem> set = new HashSet<>();
            for (int i = 0; i < slots; i++) {
                ItemStack itemStack = curios.getStackInSlot(i);
                if (!itemStack.isEmpty() && itemStack.getItem() instanceof SoulItem soulItem) {
                    set.add(soulItem);
                }
            }
            Set<SoulItem> flattened = new HashSet<>();
            for (SoulItem soulItem : set) {
                flatten(soulItem, flattened);
            }
            cache.addAll(flattened);
            syncCache  = true;
        }
    }

    private static void flatten(SoulItem soulItem, Set<SoulItem> result) {
        if (!result.add(soulItem)) {
            return;
        }
        for (SoulItem child : soulItem.getSoulItemList()) {
            flatten(child, result);
        }
    }

    @Override
    public void write(@NotNull RegistryFriendlyByteBuf registryFriendlyByteBuf, @NotNull SoulItemData soulItemData, boolean b) {
        registryFriendlyByteBuf.writeBoolean(soulItemData.syncCache);
        if (soulItemData.syncCache) {
            soulItemData.syncCache = false;
            registryFriendlyByteBuf.writeInt(soulItemData.cache.size());
            DefaultedRegistry<Item> item = BuiltInRegistries.ITEM;
            for (SoulItem soulItem : soulItemData.cache) {
                registryFriendlyByteBuf.writeResourceLocation(item.getKey(soulItem));
            }
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
            Set<SoulItem> cached = data.getCache();
            cached.clear();
            DefaultedRegistry<Item> item = BuiltInRegistries.ITEM;
            int size = registryFriendlyByteBuf.readInt();
            for (int i = 0; i < size; i++) {
                cached.add((SoulItem) item.get(registryFriendlyByteBuf.readResourceLocation()));
            }
        }
        data.canSprint = registryFriendlyByteBuf.readBoolean();
        data.maxFlyTime = registryFriendlyByteBuf.readInt();
        data.renderLavaFog = registryFriendlyByteBuf.readBoolean();
        data.renderFireOverlay = registryFriendlyByteBuf.readBoolean();
        return data;
    }
}
