package First.fargo_soul.item.terraSoul.spiritPower;

import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.SpiritPower;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class HolySoul extends SoulItem {

    public HolySoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIGHT_PURPLE));
    }

    @EventBusSubscriber
    public static class Event {

        @SubscribeEvent
        public static void HolySoulHealHandler(LivingHealEvent event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (CurioUtils.isEquipped(attacker, HolySoul.class)) {
                    event.setAmount(event.getAmount() * 1.4f);
                    if (event.getAmount() > 0) {
                        Level level = attacker.level();
                        int value = CurioUtils.isEquipped(attacker, SpiritPower.class) ? 2 : 1;
                        List<LivingEntity> targetList = level.getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox().inflate(value), livingEntity -> attacker instanceof Player ? livingEntity instanceof Enemy : (livingEntity instanceof Mob mob && attacker.equals(mob.getTarget())));
                        for (LivingEntity target : targetList) {
                            Vec3 direction = target.getBoundingBox().getCenter().subtract(attacker.getBoundingBox().getCenter()).normalize();
                            target.knockback(1.5, -direction.x, -direction.z);
                        }
                    }
                }
            }
        }

    }

}
