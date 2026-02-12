package First.fargo_soul.common.item.terraSoul.terraPower;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.TerraPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CopperSoul extends SoulItem {

    public CopperSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            SoulAbilityData.SoulInfo SoulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(CopperSoul.class);
            SoulInfo.setMaxCooldown(40);
            if (!attacker.equals(target) && SoulInfo.getCooldown() == 0 && CurioUtils.isEquipped(attacker, CopperSoul.class) && attacker.getRandom().nextDouble() < (target.isInWaterOrRain() ? 0.2 : 0.1)) {
                SoulInfo.setCooldown(SoulInfo.getMaxCooldown());
                Level level = attacker.level();
                List<LivingEntity> livingEntityList = level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(2), livingEntity -> attacker instanceof Player ? livingEntity instanceof Enemy : ((livingEntity instanceof Mob mob && attacker.equals(mob.getTarget()) || livingEntity instanceof Player)));
                for (LivingEntity livingEntity : livingEntityList) {
                    Vec3 delta = target.getBoundingBox().getCenter().subtract(livingEntity.getBoundingBox().getCenter()).normalize();
                    livingEntity.push(delta);
                    livingEntity.hasImpulse = true;
                }
                LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightning.setPos(target.getBoundingBox().getCenter());
                lightning.setDamage(lightning.getDamage() * 2.0f);
                SoulUtils.addEntity(level, lightning);
                lightning.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SoulItem.class).setEnabled(true);
                if (CurioUtils.isEquipped(attacker, TerraPower.class)) {
                    SoulUtils.executorService.schedule(() -> {
                        if (attacker.isAlive()) {
                            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                            lightningBolt.setPos(target.getBoundingBox().getCenter());
                            lightningBolt.setDamage(lightningBolt.getDamage() * 2.0f);
                            SoulUtils.addEntity(level, lightning);
                            lightning.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SoulItem.class).setEnabled(true);
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
