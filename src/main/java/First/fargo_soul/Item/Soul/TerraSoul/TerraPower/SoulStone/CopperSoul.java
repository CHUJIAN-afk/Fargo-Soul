package First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone.CobaltSoul;
import First.fargo_soul.Item.Soul.TerraSoul.TerraPower.TerraPower;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CopperSoul extends SoulItem {

    public CopperSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.ORANGE));
    }

    @EventBusSubscriber
    public static class Event {

        @SubscribeEvent
        public static void Post(LivingDamageEvent.Post event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                SoulAbilityData.SoulInfo SoulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(CobaltSoul.class);
                SoulInfo.maxCooldown = 40;
                if (SoulInfo.cooldown == 0 && SoulUtils.isEquipped(attacker, CopperSoul.class) && attacker.getRandom().nextDouble() < (target.isInWaterOrRain() ? 0.2 : 0.1)) {
                    SoulInfo.cooldown = SoulInfo.maxCooldown;
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
                    level.addFreshEntity(lightning);
                    SoulAbilityData.SoulInfo soulInfo = lightning.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SoulItem.class);
                    soulInfo.enabled = true;
                    if (SoulUtils.isEquipped(attacker, TerraPower.class)) {
                        Fargo_soul.executorService.schedule(() -> {
                            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                            lightningBolt.setPos(target.getBoundingBox().getCenter());
                            lightningBolt.setDamage(lightningBolt.getDamage() * 2.0f);
                            level.addFreshEntity(lightningBolt);
                            SoulAbilityData.SoulInfo info = lightning.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SoulItem.class);
                            info.enabled = true;
                        }, 1, TimeUnit.SECONDS);
                    }
                }
            }
        }

    }

}
