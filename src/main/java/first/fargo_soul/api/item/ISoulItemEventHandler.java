package first.fargo_soul.api.item;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.ISoulItemCache;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class ISoulItemEventHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void LivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource damageSource = event.getSource();
        Entity entity = damageSource.getEntity();
        Level level = target.level();
        boolean isClient = level.isClientSide();
        DamageContainer container = event.getContainer();
        if (entity instanceof LivingEntity attacker) {
            List<ISoulItem> list = ISoulItemCache.getList(attacker);
            for (ISoulItem iSoulItem : list) {
                iSoulItem.attack(target, attacker, container, isClient);
            }
        }
        List<ISoulItem> list = ISoulItemCache.getList(target);
        for (ISoulItem iSoulItem : list) {
            iSoulItem.target(target, entity, container, isClient);
        }
    }







}
