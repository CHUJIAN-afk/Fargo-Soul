package first.fargo_soul.common.item.terraSoul.deathPower;

import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.gui.SoulRenderType;
import first.fargo_soul.common.event.modEvent.SprintEvent;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.DeathPower;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class PenetratingNinjaSoul extends SoulItem {

    public PenetratingNinjaSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide() && CurioUtils.isEquipped(ticker, PenetratingNinjaSoul.class)) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, PenetratingNinjaSoul.class);
            soulInfo.setMaxCooldown(400);
            ticker.setNoGravity(false);
            if (soulInfo.getDuration() > 0) {
                List<LivingEntity> livingEntityList = ticker.level().getEntitiesOfClass(LivingEntity.class, ticker.getBoundingBox());
                for (LivingEntity target : livingEntityList) {
                    if (target != ticker) {
                        float amount = ticker.getMaxHealth() * 0.1f;
                        int strength = 3;
                        if (CurioUtils.isEquipped(ticker, DeathPower.class)) {
                            amount += target.getMaxHealth() * 0.05f;
                            strength++;
                        }
                        SoulUtils.attack(this.getClass(), ticker, ticker, target, DamageTypes.WITHER, amount);
                        target.knockback(strength, ticker.getX(), ticker.getZ());
                    }
                }
            }
        }
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, PenetratingNinjaSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(target, PenetratingNinjaSoul.class);
                if (soulInfo.getDuration() > 0) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public void sprintClient(SprintEvent.Client event) {
        LivingEntity livingEntity = event.getEntity();
        if (CurioUtils.isEquipped(livingEntity, PenetratingNinjaSoul.class)) {
            event.setSprinting(true);
        }
    }

    @Override
    public void sprintServer(SprintEvent.Server event) {
        LivingEntity livingEntity = event.getEntity();
        if (CurioUtils.isEquipped(livingEntity, PenetratingNinjaSoul.class)) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(livingEntity, PenetratingNinjaSoul.class);
            if (soulInfo.isReady()) {
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                soulInfo.setDuration(20);
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, PenetratingNinjaSoul.class, SoulRenderType.Cooldown);
    }

}
