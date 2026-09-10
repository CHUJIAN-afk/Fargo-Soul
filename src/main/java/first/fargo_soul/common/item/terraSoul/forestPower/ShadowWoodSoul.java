package first.fargo_soul.common.item.terraSoul.forestPower;

import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.common.entity.BloodDrop;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.FargoSoulMobEffectRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShadowWoodSoul extends SoulItem {

    public ShadowWoodSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(Player player) {
        if (player.tickCount % 20 == 0) {
            List<LivingEntity> enemies = SoulTargetCache.get(player).getEntitiesInRadius(player.getBoundingBox().getCenter(), 6, null);
            for (LivingEntity enemy : enemies) {
                enemy.addEffect(new MobEffectInstance(FargoSoulMobEffectRegister.Hemorrhage, 120));
            }
        }
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (target.hasEffect(FargoSoulMobEffectRegister.Hemorrhage)) {
            Info info = SoulInfoData.getSoulInfo(attacker, FargoSoulSoulInfoRegister.SHADOW_WOOD_SOUL_INFO);
            if (info == null) {
                info = new Info();
                SoulInfoData.putSoulInfo(attacker, info);
            }
            if (info.cooldown <= 0) {
                info.cooldown = 10;
                Vec3 from = target.getBoundingBox().getCenter();
                List<LivingEntity> others = SoulTargetCache.get(attacker).getEntitiesInRadius(target.getBoundingBox().getCenter(), 8, other -> other != target);
                if (!others.isEmpty()) {
                    RandomSource random = attacker.getRandom();
                    int count = 2 + random.nextInt(2);
                    for (int i = 0; i < count; i++) {
                        LivingEntity chase = others.get(random.nextInt(others.size()));
                        BloodDrop drop = new BloodDrop();
                        drop.setDamageSourceSupplier(owner -> owner.damageSources().playerAttack(owner));
                        drop.setPos(from);
                        drop.setVelocity(from.offsetRandom(random, 1).subtract(from).normalize().scale(0.5f));
                        drop.setDamage(2);
                        drop.setChaseTarget(chase);
                        drop.join(attacker);
                    }
                }
            }
        }
    }

    public static final class Info extends SoulInfo {

        public int cooldown = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.SHADOW_WOOD_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (SoulItemData.isEquipped(living, FargoSoulItemRegister.ShadowWoodSoulItem.get())) {
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
