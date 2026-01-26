package First.fargo_soul.common.item.terraSoul.naturePower;

import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class CrimsonSoul extends SoulItem {

    public CrimsonSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            Level level = target.level();
            List<LivingEntity> livingEntityList = level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(5), livingEntity -> CurioUtils.isEquipped(livingEntity, CrimsonSoul.class));
            livingEntityList.remove(target);
            for (LivingEntity attacker : livingEntityList) {
                float healAmount = event.getAmount() * (0.1f + ((attacker.getMaxHealth() - attacker.getHealth()) / attacker.getMaxHealth()) * 0.3f);
                attacker.heal(healAmount);
            }
        }
    }

}
