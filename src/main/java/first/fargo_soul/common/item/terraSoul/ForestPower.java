package first.fargo_soul.common.item.terraSoul;

import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.common.entity.SnowBall;
import first.fargo_soul.common.entity.TrackingBlood;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.FargoSoulMobEffectRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

import static first.fargo_soul.register.FargoSoulItemRegister.*;

public class ForestPower extends SoulItem {

    public ForestPower(Properties properties) {
        super(properties);
    }

    @Override
    public Set<SoulItem> getSoulItemList() {
        return Set.of(WoodSoulItem.get(), PineWoodSoulItem.get(), RoseWoodSoulItem.get(), EbonyWoodSoulItem.get(), ShadowWoodSoulItem.get(), PalmWoodSoulItem.get(), PearlWoodSoulItem.get());
    }

    @Override
    public void tick(Player player) {
        Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.FOREST_POWER_INFO);
        if (info == null) {
            info = new Info();
            SoulInfoData.putSoulInfo(player, info);
        }
        if (player.tickCount % 40 == 0) {
            Vec3 eye = player.getEyePosition();
            List<LivingEntity> targets = SoulTargetCache.get(player).getEntitiesInRadius(player.getBoundingBox().getCenter(), 12, null);
            if (!targets.isEmpty()) {
                LivingEntity target = targets.get(player.getRandom().nextInt(targets.size()));
                Vec3 dir = target.getBoundingBox().getCenter().subtract(eye).normalize();
                SnowBall ball = new SnowBall();
                ball.setDamageSourceSupplier(owner -> owner.damageSources().playerAttack(owner));
                ball.setPos(eye);
                ball.setVelocity(dir);
                ball.setDamage(6);
                ball.join(player);
            }
        }
        if (player.tickCount % 20 == 0) {
            List<LivingEntity> enemies = SoulTargetCache.get(player).getEntitiesInRadius(player.getBoundingBox().getCenter(), 12, null);
            for (LivingEntity enemy : enemies) {
                enemy.addEffect(new MobEffectInstance(MobEffects.WITHER, 200));
                enemy.addEffect(new MobEffectInstance(FargoSoulMobEffectRegister.Bleeding, 200));
            }
        }
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (target.hasEffect(MobEffects.WITHER)) {
            modifiers.add(new ValueModifier(8, ValueOperation.ADD_VALUE));
        }
        if (target.hasEffect(FargoSoulMobEffectRegister.Bleeding)) {
            Info info = SoulInfoData.getSoulInfo(attacker, FargoSoulSoulInfoRegister.FOREST_POWER_INFO);
            if (info == null) {
                info = new Info();
                SoulInfoData.putSoulInfo(attacker, info);
            }
            if (info.cooldown <= 0) {
                info.cooldown = 20;
                Vec3 from = target.getBoundingBox().getCenter();
                RandomSource random = attacker.getRandom();
                int count = 3 + random.nextInt(4);
                for (int i = 0; i < count; i++) {
                    TrackingBlood blood = new TrackingBlood();
                    blood.setPos(from);
                    blood.setVelocity(from.offsetRandom(random, 1).subtract(from).normalize().scale(1.5f));
                    blood.join(attacker);
                }
            }
        }
    }

    @Override
    public void criticalHit(Player player, LivingEntity target, List<ValueModifier> modifiers) {
        Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.FOREST_POWER_INFO);
        if (info != null && info.burstTime > 0) {
            modifiers.add(new ValueModifier(0.4f, ValueOperation.ADD_MULTIPLIED_BASE));
        }
    }

    @Override
    public void pickup(Player player, ItemStack itemStack) {
        Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.FOREST_POWER_INFO);
        if (info == null) {
            info = new Info();
            SoulInfoData.putSoulInfo(player, info);
        }
        info.burstTime = 160;
    }

    @Override
    public void updateSpecialPrices(Player player, Villager villager, List<ValueModifier> modifiers) {
        modifiers.add(new ValueModifier(-0.5f, ValueOperation.ADD_MULTIPLIED_TOTAL));
    }

    public static final class Info extends SoulInfo {

        public int cooldown = 0;
        public int burstTime = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.FOREST_POWER_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (!SoulItemData.isEquipped(living, FargoSoulItemRegister.ForestPowerItem.get())) {
                setRemove(true);
            }
            if (cooldown > 0) {
                cooldown--;
            }
            if (burstTime > 0) {
                burstTime--;
            }
            if (burstTime <= 0 && cooldown <= 0) {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putInt("cooldown", cooldown);
            tag.putInt("burstTime", burstTime);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            cooldown = tag.getInt("cooldown");
            burstTime = tag.getInt("burstTime");
        }
    }
}
