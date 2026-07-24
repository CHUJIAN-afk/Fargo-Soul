package first.fargo_soul.common.item.terraSoul.forestPower;

import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.ForestPower;
import first.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

public class PearlWoodSoul extends SoulItem {

    public PearlWoodSoul(Properties properties) {
        super();
    }

    @Override
    public void criticalHit(CriticalHitEvent event) {
        if (event.getEntity() instanceof Player attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, PearlWoodSoul.class)) {
                if (event.isCriticalHit()) {
                    event.setDamageMultiplier(event.getDamageMultiplier() * 1.3f);
                } else if (attacker.getRandom().nextDouble() < 0.25) {
                    event.setCriticalHit(true);
                } else if (CurioUtils.isEquipped(attacker, ForestPower.class) && attacker.getRandom().nextDouble() < 0.25) {
                    event.setCriticalHit(true);
                }
            }
        }
    }

}
