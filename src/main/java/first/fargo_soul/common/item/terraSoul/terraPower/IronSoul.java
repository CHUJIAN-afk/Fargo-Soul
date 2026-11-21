package first.fargo_soul.common.item.terraSoul.terraPower;

import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IronSoul extends SoulItem {

    public IronSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(Player player) {
        List<Entity> items = player.level().getEntitiesOfClass(Entity.class, player.getBoundingBox().inflate(8));
        Inventory inventory = player.getInventory();
        Vec3 pos = player.position();
        for (Entity item : items) {
            if (item instanceof ItemEntity itemEntity) {
                boolean place = false;
                if (!player.isCreative()) {
                    for (int i = 0; i < inventory.items.size(); i++) {
                        ItemStack itemStack = inventory.getItem(i);
                        if (itemStack.isEmpty()) {
                            place = true;
                            break;
                        }
                        if (itemStack.getCount() < itemStack.getMaxStackSize() && ItemStack.isSameItem(itemStack, itemEntity.getItem())) {
                            place = true;
                            break;
                        }
                    }
                } else {
                    place = true;
                }
                if (place) {
                    itemEntity.setDeltaMovement(Vec3.ZERO);
                    itemEntity.teleportTo(pos.x, pos.y + 0.1, pos.z);
                    break;
                }
            } else if (item instanceof ExperienceOrb experienceOrb) {
                player.takeXpDelay = 0;
                experienceOrb.playerTouch(player);
            }
        }
    }

    @Override
    public void pickup(Player player, ItemStack itemStack) {
        Info soulInfo = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.IRON_SOUL_INFO);
        if (soulInfo == null) {
            soulInfo = new Info();
            SoulInfoData.putSoulInfo(player, soulInfo);
        }
        soulInfo.protectTime = 100;
    }

    @Override
    public void hurt(@Nullable Entity attacker, @NotNull Player target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        Info soulInfo = SoulInfoData.getSoulInfo(target, FargoSoulSoulInfoRegister.IRON_SOUL_INFO);
        if (soulInfo != null && soulInfo.protectTime > 0) {
            modifiers.add(new ValueModifier(-0.2f, ValueOperation.ADD_MULTIPLIED_TOTAL));
        }
    }

    public static final class Info extends SoulInfo {

        public int protectTime = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.IRON_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (SoulItemData.isEquipped(living, FargoSoulItemRegister.IronSoulItem.get())) {
                if (protectTime > 0) {
                    protectTime--;
                }
            } else {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putInt("protectTime", protectTime);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            protectTime = tag.getInt("protectTime");
        }
    }
}
