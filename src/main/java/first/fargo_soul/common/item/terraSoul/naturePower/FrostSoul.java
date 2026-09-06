package first.fargo_soul.common.item.terraSoul.naturePower;

import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.entity.SnowBall;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FrostSoul extends SoulItem {

    public FrostSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        Info info = SoulInfoData.getSoulInfo(attacker, FargoSoulSoulInfoRegister.FROST_SOUL_INFO);
        if (info == null) {
            info = new Info();
            SoulInfoData.putSoulInfo(attacker, info);
        }
        if (info.cooldown <= 0) {
            boolean coldBiome = attacker.level().getBiome(BlockPos.containing(attacker.getEyePosition())).value().coldEnoughToSnow(BlockPos.containing(attacker.getEyePosition()));
            info.cooldown = coldBiome ? 8 : 20;
            Vec3 eye = attacker.getEyePosition();
            Vec3 dir = target.getBoundingBox().getCenter().subtract(eye).normalize();
            for (int i = 0; i < 2; i++) {
                SnowBall ball = new SnowBall(attacker.damageSources().playerAttack(attacker), eye, dir.scale(2));
                ball.setDamage(5);
                ball.join(attacker);
            }
        }
    }

    public static final class Info extends SoulInfo {

        public int cooldown = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.FROST_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (SoulItemData.isEquipped(living, FargoSoulItemRegister.FrostSoulItem.get())) {
                if (cooldown > 0) {
                    cooldown--;
                }
            } else {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putInt("cooldown", cooldown);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            cooldown = tag.getInt("cooldown");
        }
    }
}