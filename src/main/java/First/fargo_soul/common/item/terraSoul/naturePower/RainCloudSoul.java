package First.fargo_soul.common.item.terraSoul.naturePower;

import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.NaturePower;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class RainCloudSoul extends SoulItem {

    public RainCloudSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (event.getSource().is(DamageTypeTags.IS_LIGHTNING) && CurioUtils.isEquipped(target, RainCloudSoul.class)) {
                event.setCanceled(true);
            }
        }
        if (!event.isCanceled() && event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            Level level = target.level();
            double chance = CurioUtils.isEquipped(target, NaturePower.class) && level.isRaining() ? 0.4 : 0.1;
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(target, RainCloudSoul.class);
            soulInfo.setMaxCooldown(20);
            if (soulInfo.isReady() && !attacker.equals(target) && CurioUtils.isEquipped(target, RainCloudSoul.class) && target.getRandom().nextDouble() < chance) {
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightning.setPos(attacker.getBoundingBox().getCenter());
                SoulUtils.addEntity(level, lightning);
            }
        }
    }

}
