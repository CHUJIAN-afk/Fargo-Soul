package First.fargo_soul.common.item.terraSoul.lifePower;

import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.LifePower;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class SpiderSoul extends SoulItem {

    public SpiderSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof TamableAnimal animal && !animal.level().isClientSide()) {
            if (animal.getOwner() instanceof LivingEntity attacker && CurioUtils.isEquipped(attacker, SpiderSoul.class)) {
                double chance = CurioUtils.isEquipped(attacker, LifePower.class) ? 0.4 : 0.2;
                if (animal.getRandom().nextDouble() < chance) {
                    float damage = event.getAmount();
                    float scale = SoulUtils.getSoulInfo(attacker, LifePower.class).getDuration() > 0 ? 4 : 2;
                    float newDamage = CurioUtils.isEquipped(attacker, LifePower.class) ? damage * scale : damage + Math.min(damage * 0.5f, 100);
                    event.setAmount(newDamage);
                }
            }
        }
    }

}
