package first.fargo_soul.common.item.terraSoul.spiritPower;

import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.gui.SoulRenderType;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.SpiritPower;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.utils.AttributeUtils;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class GhostSoul extends SoulItem {

    public GhostSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, GhostSoul.class);
            soulInfo.setMaxCooldown(12000);
            soulInfo.setMinStacks(-20);
            soulInfo.setMaxStacks(CurioUtils.isEquipped(ticker, SpiritPower.class) ? 200 : 100);
            AttributeUtils.condition(ticker, Attributes.MAX_HEALTH, FargoSoulItemRegister.GhostSoulItem.getId(), soulInfo.getStacks() * 0.01f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, (CurioUtils.isEquipped(ticker, GhostSoul.class) && soulInfo.getStacks() > 0) || soulInfo.getStacks() < 0);
        }
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, GhostSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(attacker, GhostSoul.class);
                soulInfo.addStacks();
                SoulAbilityData.SoulInfo soulInfo1 = SoulUtils.getSoulInfo(target, GhostSoul.class);
                soulInfo1.shrinkStacks();
            }
        }
    }

    @Override
    public void death(LivingDeathEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(target, GhostSoul.class);
            if (CurioUtils.isEquipped(target, GhostSoul.class) && soulInfo.isReady()) {
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                target.heal(target.getMaxHealth() * 0.25f);
                event.setCanceled(true);
                target.getActiveEffects().removeIf(mobEffectInstance -> mobEffectInstance.getEffect().value().getCategory().equals(MobEffectCategory.HARMFUL));
                List<LivingEntity> livingEntityList = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(3), livingEntity -> target instanceof Player ? livingEntity instanceof Enemy : !(livingEntity instanceof Monster));
                for (LivingEntity livingEntity : livingEntityList) {
                    SoulUtils.attack(this.getClass(), target, target, livingEntity, DamageTypes.MAGIC, target.getMaxHealth());
                }
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, GhostSoul.class, SoulRenderType.Cooldown);
        soulRenderManager.add(this, GhostSoul.class, SoulRenderType.Stack);
    }

}
