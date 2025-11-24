package First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.TerraPower.TerraPower;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class TinSoul extends SoulItem {

    public TinSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.BLUE));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent(priority = EventPriority.HIGH)
        public static void Critical(CriticalHitEvent event) {
            Player player = event.getEntity();
            if (player.getAttribute(AttributeRegister.CriticalChance) instanceof AttributeInstance CriticalChance && player.getAttribute(AttributeRegister.CriticalDamage) instanceof AttributeInstance CriticalDamage) {
                double CriticalChanceAmount = (SoulUtils.isEquipped(player, TerraPower.class) ? 0.2 : 0.1) - CriticalChance.getValue();
                AttributeUtils.ConditionAttributeModifier(
                        player,
                        AttributeRegister.CriticalChance,
                        SoulsRegister.TinSoul.getId(),
                        CriticalChanceAmount,
                        AttributeModifier.Operation.ADD_VALUE,
                        SoulUtils.isEquipped(player, TinSoul.class) && CriticalChanceAmount > 0
                );
                double CriticalDamageAmount = 2.0 - CriticalDamage.getValue();
                AttributeUtils.ConditionAttributeModifier(
                        player,
                        AttributeRegister.CriticalDamage,
                        SoulsRegister.TinSoul.getId(),
                        CriticalDamageAmount,
                        AttributeModifier.Operation.ADD_VALUE,
                        SoulUtils.isEquipped(player, TinSoul.class) && CriticalDamageAmount > 0
                );
                SoulAbilityData.SoulInfo SoulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TinSoul.class);
                SoulInfo.setMaxStacks((SoulUtils.isEquipped(player, TerraPower.class) ? 100 : 60));
                AttributeUtils.ConditionAttributeModifier(
                        player,
                        AttributeRegister.CriticalChance,
                        ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "tin_soul_addition"),
                        SoulInfo.getStacks() * 0.01,
                        AttributeModifier.Operation.ADD_VALUE,
                        SoulUtils.isEquipped(player, TinSoul.class) && SoulInfo.getStacks() > 0
                );
                if (SoulUtils.isEquipped(player, TinSoul.class)) {
                    if (player.getRandom().nextDouble() < CriticalChance.getValue()) {
                        SoulInfo.addStacks(10);
                        event.setCriticalHit(true);
                        event.setDamageMultiplier((float) CriticalDamage.getValue());
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Damage(LivingDamageEvent.Post event) {
            if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
                if (SoulUtils.isEquipped(player, TinSoul.class)) {
                    SoulAbilityData.SoulInfo SoulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TinSoul.class);
                    SoulInfo.shrinkStacks(SoulInfo.getStacks() / 2);
                }
            }
        }

    }

}

