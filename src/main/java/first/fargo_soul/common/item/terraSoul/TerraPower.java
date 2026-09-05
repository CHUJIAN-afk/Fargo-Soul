package first.fargo_soul.common.item.terraSoul;

import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.entity.LightningOrb;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.FargoSoulMobEffectRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.api.LyraHelper;
import first.lyra.common.attachment.AttachmentEntityData;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

import static first.fargo_soul.register.FargoSoulItemRegister.*;

public class TerraPower extends SoulItem {

    public TerraPower(Properties properties) {
        super(properties);
    }

    @Override
    public Set<SoulItem> getSoulItemList() {
        return Set.of(CopperSoulItem.get(), IronSoulItem.get(), LeadSoulItem.get(), ObsidianSoulItem.get(), SilverSoulItem.get(), TinSoulItem.get(), TungstenSoulItem.get());
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        Info soulInfo = SoulInfoData.getSoulInfo(attacker, FargoSoulSoulInfoRegister.TERRA_POWER_INFO);
        if (soulInfo == null) {
            soulInfo = new Info();
            SoulInfoData.putSoulInfo(attacker, soulInfo);
        }
        if (soulInfo.cooldown <= 0) {
            int orbCount = LyraHelper.get(attacker).getEntityData().get(AttachmentEntityData.Type.Projectile, SummonerAttachmentEntityRegister.LIGHTNING_ORB.get()).size();
            float chance = 0.2f + Math.min(orbCount, 5) * 0.08f;
            RandomSource random = attacker.getRandom();
            if (random.nextFloat() < chance) {
                soulInfo.cooldown = (int) (20 * (1 - Math.min(orbCount, 5) * 0.12f));
                int count = 3 + random.nextInt(4);
                Vec3 eye = attacker.getEyePosition();
                for (int i = 0; i < count; i++) {
                    Vec3 dir = eye.add(attacker.getLookAngle()).offsetRandom(random, 0.5f).subtract(eye).normalize();
                    LightningOrb orb = new LightningOrb(attacker.damageSources().lightningBolt(), eye, dir);
                    float bonus = attacker.hasEffect(FargoSoulMobEffectRegister.TerraResonance) ? 1.8f : 1.0f;
                    orb.setDamage(4 * bonus);
                    orb.join(attacker);
                }
            }
        }
        if (attacker.getRandom().nextFloat() < 0.05f) {
            modifiers.add(new ValueModifier(1, ValueOperation.ADD_MULTIPLIED_BASE));
            attacker.addEffect(new MobEffectInstance(FargoSoulMobEffectRegister.TerraResonance, 100));
        }
    }

    @Override
    public void hurt(@Nullable Entity attacker, @NotNull Player target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        modifiers.add(new ValueModifier(-(4 + target.getMaxHealth() * 0.02f), ValueOperation.ADD_VALUE));
    }

    public static final class Info extends SoulInfo {

        public int cooldown = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.TERRA_POWER_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (SoulItemData.isEquipped(living, FargoSoulItemRegister.TerraPowerItem.get())) {
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
