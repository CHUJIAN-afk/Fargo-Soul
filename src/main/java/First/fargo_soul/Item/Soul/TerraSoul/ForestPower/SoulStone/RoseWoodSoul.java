package First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.ForestPower.ForestPower;
import First.fargo_soul.Utils.SoulUtils;
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

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target) {
                if (!target.onGround() && SoulUtils.isEquipped(attacker, RoseWoodSoul.class) && !SoulUtils.isEquipped(target, RoseWoodSoul.class)) {
                    Vec3 delta = attacker.getBoundingBox().getCenter().subtract(target.getBoundingBox().getCenter()).normalize();
                    delta.scale(SoulUtils.isEquipped(attacker, ForestPower.class) ? 2 : 1);
                    target.push(delta);
                    target.hasImpulse = true;
                }
            }
        }

    }


}
