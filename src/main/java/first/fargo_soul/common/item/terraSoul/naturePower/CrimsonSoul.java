package first.fargo_soul.common.item.terraSoul.naturePower;

import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class CrimsonSoul extends SoulItem {

    public CrimsonSoul(Properties properties) {
        super();
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            for (LivingEntity living : SoulUtils.getTargetList(target, 5)) {
                if (CurioUtils.isEquipped(living, CrimsonSoul.class)) {
                    float healAmount = event.getAmount() * (0.1f + ((living.getMaxHealth() - living.getHealth()) / living.getMaxHealth()) * 0.3f);
                    living.heal(healAmount);
                }
            }
        }
    }

}
