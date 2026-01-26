package First.fargo_soul.common.item.terraSoul.terraPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.TerraPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.AttributeRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

public class TinSoul extends SoulItem {

    public TinSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void criticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        if (player.getAttribute(AttributeRegister.CriticalChance) instanceof AttributeInstance CriticalChance && player.getAttribute(AttributeRegister.CriticalDamage) instanceof AttributeInstance CriticalDamage) {
            double CriticalChanceAmount = (CurioUtils.isEquipped(player, TerraPower.class) ? 0.2 : 0.1) - CriticalChance.getValue();
            AttributeUtils.condition(
                    player,
                    AttributeRegister.CriticalChance,
                    ItemRegister.TinSoulItem.getId(),
                    CriticalChanceAmount,
                    AttributeModifier.Operation.ADD_VALUE,
                    CurioUtils.isEquipped(player, TinSoul.class) && CriticalChanceAmount > 0
            );
            double CriticalDamageAmount = 2.0 - CriticalDamage.getValue();
            AttributeUtils.condition(
                    player,
                    AttributeRegister.CriticalDamage,
                    ItemRegister.TinSoulItem.getId(),
                    CriticalDamageAmount,
                    AttributeModifier.Operation.ADD_VALUE,
                    CurioUtils.isEquipped(player, TinSoul.class) && CriticalDamageAmount > 0
            );
            SoulAbilityData.SoulInfo SoulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TinSoul.class);
            SoulInfo.setMaxStacks((CurioUtils.isEquipped(player, TerraPower.class) ? 100 : 60));
            AttributeUtils.condition(
                    player,
                    AttributeRegister.CriticalChance,
                    ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "tin_soul_addition"),
                    SoulInfo.getStacks() * 0.01,
                    AttributeModifier.Operation.ADD_VALUE,
                    CurioUtils.isEquipped(player, TinSoul.class) && SoulInfo.getStacks() > 0
            );
            if (CurioUtils.isEquipped(player, TinSoul.class)) {
                if (player.getRandom().nextDouble() < CriticalChance.getValue()) {
                    SoulInfo.addStacks(10);
                    event.setCriticalHit(true);
                    event.setDamageMultiplier((float) CriticalDamage.getValue());
                }
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            if (CurioUtils.isEquipped(player, TinSoul.class)) {
                SoulAbilityData.SoulInfo SoulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TinSoul.class);
                SoulInfo.shrinkStacks(SoulInfo.getStacks() / 2);
            }
        }
    }

}

