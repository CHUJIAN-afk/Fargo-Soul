package First.fargo_soul.common.item.terraSoul.terraPower;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.TerraPower;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.concurrent.TimeUnit;

public class CopperSoul extends SoulItem {

    public CopperSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(attacker, CopperSoul.class);
            soulInfo.setMaxCooldown(40);
            if (soulInfo.isReady() && CurioUtils.isEquipped(attacker, CopperSoul.class) && attacker.getRandom().nextDouble() < (target.isInWaterOrRain() ? 0.2 : 0.1)) {
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                Level level = attacker.level();
                for (LivingEntity living : SoulUtils.getTargetList(attacker, target.getBoundingBox().inflate(2))) {
                    if (!CurioUtils.isEquipped(living, CopperSoul.class)) {
                        Vec3 delta = target.getBoundingBox().getCenter().subtract(living.getBoundingBox().getCenter()).normalize();
                        living.push(delta);
                    }
                }
                LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightning.setPos(target.getBoundingBox().getCenter());
                lightning.setDamage(lightning.getDamage() * 2.0f);
                SoulUtils.addEntity(level, lightning);
                SoulUtils.setAbilityInvulnerable(lightning);
                if (CurioUtils.isEquipped(attacker, TerraPower.class)) {
                    SoulUtils.executorService.schedule(() -> {
                        if (attacker.isAlive()) {
                            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                            lightningBolt.setPos(target.getBoundingBox().getCenter());
                            lightningBolt.setDamage(lightningBolt.getDamage() * 2.0f);
                            SoulUtils.addEntity(level, lightning);
                            SoulUtils.setAbilityInvulnerable(lightning);
                        }
                    }, 1, TimeUnit.SECONDS);
                }
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, CopperSoul.class, SoulRenderType.Cooldown);
    }

}
