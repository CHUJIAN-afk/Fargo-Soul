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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
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
        if (!player.level().isClientSide()) {
            List<ItemEntity> items = player.level().getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(4), item -> !item.hasPickUpDelay());
            for (ItemEntity item : items) {
                Vec3 delta = player.getBoundingBox().getCenter().subtract(item.getBoundingBox().getCenter()).normalize();
                item.setDeltaMovement(item.getDeltaMovement().add(delta.scale(0.1f)));
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
