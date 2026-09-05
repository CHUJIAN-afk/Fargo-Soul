package first.fargo_soul.common.item.terraSoul;

import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import first.lyra.common.attachment.InvincibleData;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

import static first.fargo_soul.register.FargoSoulItemRegister.*;

public class CosmicPower extends SoulItem {

    public CosmicPower(Properties properties) {
        super(properties);
    }

    @Override
    public Set<SoulItem> getSoulItemList() {
        return Set.of(BlazeSoulItem.get(), METEOR_SOUL.get(), WizardSoulItem.get(), StardustSoulItem.get(), NebulaSoulItem.get(), VortexSoulItem.get());
    }

    @Override
    public void tick(Player living) {
        Info soulInfo = SoulInfoData.getSoulInfo(living, FargoSoulSoulInfoRegister.COSMIC_POWER_INFO);
        if (soulInfo == null) {
            soulInfo = new Info();
            SoulInfoData.putSoulInfo(living, soulInfo);
        }
        if (living.tickCount % 20 == 0 && soulInfo.cosmicPower < soulInfo.maxCosmicPower) {
            soulInfo.cosmicPower++;
        }
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        Info soulInfo = SoulInfoData.getSoulInfo(attacker, FargoSoulSoulInfoRegister.COSMIC_POWER_INFO);
        if (soulInfo == null) {
            soulInfo = new Info();
            SoulInfoData.putSoulInfo(attacker, soulInfo);
        }
        modifiers.add(new ValueModifier(0.8f * (soulInfo.cosmicPower / soulInfo.maxCosmicPower), ValueOperation.ADD_VALUE));
    }

    @Override
    public void hurt(@Nullable Entity attacker, @NotNull Player target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        Info soulInfo = SoulInfoData.getSoulInfo(target, FargoSoulSoulInfoRegister.COSMIC_POWER_INFO);
        if (soulInfo != null) {
            modifiers.add(new ValueModifier(-0.2f * (soulInfo.cosmicPower / soulInfo.maxCosmicPower), ValueOperation.ADD_MULTIPLIED_TOTAL));
            float damage = container.getOriginalDamage();
            float maxHealth = target.getMaxHealth();
            if (damage > maxHealth * 0.5f && soulInfo.cosmicPower >= 50) {
                soulInfo.cosmicPower -= 50;
                modifiers.add(new ValueModifier(-0.5f, ValueOperation.ADD_MULTIPLIED_TOTAL));
            }
        }
    }

    @Override
    public boolean death(Player player, DamageSource source, boolean canceled) {
        SoulTargetCache targetCache = SoulTargetCache.get(player);
        List<LivingEntity> list = targetCache.getEntitiesInRadius(player.getBoundingBox().getCenter(), 6, null);
        for (LivingEntity living : list) {
            InvincibleData.attack(living)
                    .damageSource(player.damageSources().magic())
                    .damageAmount(player.getMaxHealth() * 3.6f)
                    .apply();
        }
        return super.death(player, source, canceled);
    }

    public static final class Info extends SoulInfo {

        public float cosmicPower = 0;
        public final float maxCosmicPower = 100;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.COSMIC_POWER_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (!SoulItemData.isEquipped(living, FargoSoulItemRegister.CosmicPowerItem.get())) {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putFloat("cosmicPower", cosmicPower);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            cosmicPower = tag.getFloat("cosmicPower");
        }
    }
}
