package First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.NaturePower.NaturePower;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class RainCloudSoul extends SoulItem {

    public RainCloudSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIGHT_PURPLE));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (event.getSource().is(DamageTypeTags.IS_LIGHTNING) && SoulUtils.isEquipped(target, RainCloudSoul.class)) {
                    event.setCanceled(true);
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                Level level = target.level();
                double chance = SoulUtils.isEquipped(target, NaturePower.class) && level.isRaining() ? 0.4 : 0.1;
                if (SoulUtils.isEquipped(target, RainCloudSoul.class) && target.getRandom().nextDouble() < chance) {
                    LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightning.setPos(attacker.getBoundingBox().getCenter());
                    level.addFreshEntity(lightning);
                }
            }
        }

    }

}
