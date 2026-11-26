package first.fargo_soul.common.item.terraSoul.terraPower;

import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.entity.LightningOrb;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.FargoSoulMobEffectRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import first.lyra.api.LyraHelper;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.core.HolderLookup;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.nbt.CompoundTag;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.util.RandomSource;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.LivingEntity;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.player.Player;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CopperSoul extends SoulItem {

    public CopperSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        Info soulInfo = SoulInfoData.getSoulInfo(attacker, FargoSoulSoulInfoRegister.COPPER_SOUL_INFO);
        if (soulInfo == null || soulInfo.cooldown <= 0) {
            boolean resonance = attacker.hasEffect(FargoSoulMobEffectRegister.TerraResonance);
            float chance = (target.isInWaterOrRain() ? 0.3f : 0.1f) + (resonance ? 0.2f : 0);
            RandomSource random = attacker.getRandom();
            if (random.nextFloat() < chance) {
                if (soulInfo == null) {
                    soulInfo = new Info();
                    SoulInfoData.putSoulInfo(attacker, soulInfo);
                }
                soulInfo.cooldown = 100;
                Vec3 eye = attacker.getEyePosition();
                Vec3 dir = eye.add(target.getBoundingBox().getCenter().subtract(eye)).offsetRandom(random, 0.5f).subtract(eye).normalize();
                LightningOrb orb = new LightningOrb();
                orb.init(new PathNode(eye, dir));
                orb.setVelocity(dir);
                float bonus = resonance ? 1.8f : 1.0f;
                orb.setDamage(4 * bonus);
                orb.setOwner(attacker);
                LyraHelper.get(attacker).add(orb);
            }
        }
    }

    public static final class Info extends SoulInfo {

        public int cooldown = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.COPPER_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (SoulItemData.isEquipped(living, FargoSoulItemRegister.CopperSoulItem.get())) {
                if (--cooldown <= 0) {
                    setRemove(true);
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
