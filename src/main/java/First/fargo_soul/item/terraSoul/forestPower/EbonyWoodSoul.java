package First.fargo_soul.item.terraSoul.forestPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.ForestPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class EbonyWoodSoul extends SoulItem {

    public EbonyWoodSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event{

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (CurioUtils.isEquipped(attacker, EbonyWoodSoul.class)) {
                    List<LivingEntity> livingEntityList = attacker.level().getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox().inflate(3));
                    livingEntityList.removeIf(livingEntity -> CurioUtils.isEquipped(livingEntity, EbonyWoodSoul.class));
                    for (LivingEntity target : livingEntityList) {
                        SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName());
                        soulInfo.setMaxStacks(CurioUtils.isEquipped(attacker, ForestPower.class) ? 200 : 100);
                        soulInfo.addStacks();
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, EbonyWoodSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName());
                    float newDamage = event.getAmount() * (1 + (soulInfo.getStacks() * 0.001f));
                    event.setAmount(newDamage);
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && CurioUtils.isEquipped(target, EbonyWoodSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName());
                    float newDamage = event.getAmount() * (1 - (soulInfo.getStacks() * 0.0005f));
                    event.setAmount(newDamage);
                }
            }
        }

    }

}
