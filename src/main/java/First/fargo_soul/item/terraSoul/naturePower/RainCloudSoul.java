package First.fargo_soul.item.terraSoul.naturePower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.NaturePower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
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

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (event.getSource().is(DamageTypeTags.IS_LIGHTNING) && CurioUtils.isEquipped(target, RainCloudSoul.class)) {
                    event.setCanceled(true);
                }
            }
            if (!event.isCanceled() && event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                Level level = target.level();
                double chance = CurioUtils.isEquipped(target, NaturePower.class) && level.isRaining() ? 0.4 : 0.1;
                SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(RainCloudSoul.class);
                soulInfo.setMaxCooldown(20);
                if (soulInfo.isReady() && !attacker.equals(target) && CurioUtils.isEquipped(target, RainCloudSoul.class) && target.getRandom().nextDouble() < chance) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightning.setPos(attacker.getBoundingBox().getCenter());
                    SoulUtils.addEntity(level, lightning);
                }
            }
        }

    }

}
