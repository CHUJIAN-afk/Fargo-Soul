package First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.ForestPower.ForestPower;
import First.fargo_soul.Utils.SoulUtils;
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

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event{

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, EbonyWoodSoul.class)) {
                    List<LivingEntity> livingEntityList = attacker.level().getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox().inflate(3));
                    livingEntityList.removeIf(livingEntity -> SoulUtils.isEquipped(livingEntity, EbonyWoodSoul.class));
                    for (LivingEntity target : livingEntityList) {
                        SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName());
                        soulInfo.maxStacks = SoulUtils.isEquipped(attacker, ForestPower.class) ? 200 : 100;
                        soulInfo.addStacks();
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, EbonyWoodSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName());
                    float newDamage = event.getAmount() * (1 + (soulInfo.stacks * 0.001f));
                    event.setAmount(newDamage);
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(target, EbonyWoodSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName());
                    float newDamage = event.getAmount() * (1 - (soulInfo.stacks * 0.0005f));
                    event.setAmount(newDamage);
                }
            }
        }

    }

}
