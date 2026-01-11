package First.fargo_soul.item.terraSoul.forestPower;

import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.ForestPower;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class RoseWoodSoul extends SoulItem {

    public RoseWoodSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target) {
            if (!attacker.equals(target) && !target.onGround() && CurioUtils.isEquipped(attacker, RoseWoodSoul.class) && !CurioUtils.isEquipped(target, RoseWoodSoul.class)) {
                Vec3 delta = attacker.getBoundingBox().getCenter().subtract(target.getBoundingBox().getCenter()).normalize();
                delta.scale(CurioUtils.isEquipped(attacker, ForestPower.class) ? 2 : 1);
                target.push(delta);
                target.hasImpulse = true;
            }
        }
    }

}
