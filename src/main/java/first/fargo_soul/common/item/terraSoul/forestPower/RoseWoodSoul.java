package first.fargo_soul.common.item.terraSoul.forestPower;

import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.ForestPower;
import first.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class RoseWoodSoul extends SoulItem {

    public RoseWoodSoul(Properties properties) {
        super();
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target) {
            if (!target.onGround() && CurioUtils.isEquipped(attacker, RoseWoodSoul.class) && !CurioUtils.isEquipped(target, RoseWoodSoul.class)) {
                Vec3 delta = attacker.getBoundingBox().getCenter().subtract(target.getBoundingBox().getCenter()).normalize();
                float strength = CurioUtils.isEquipped(attacker, ForestPower.class) ? 2.0f : 1.0f;
                target.knockback(strength, delta.x, delta.z);
            }
        }
    }

}
