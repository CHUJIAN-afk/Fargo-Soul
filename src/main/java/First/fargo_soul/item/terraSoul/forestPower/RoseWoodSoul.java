package First.fargo_soul.item.terraSoul.forestPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.ForestPower;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class RoseWoodSoul extends SoulItem {

    public RoseWoodSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
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


}
