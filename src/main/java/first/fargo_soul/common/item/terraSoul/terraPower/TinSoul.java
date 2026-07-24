package first.fargo_soul.common.item.terraSoul.terraPower;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.TerraPower;
import first.fargo_soul.register.AttributeRegister;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.utils.AttributeUtils;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

public class TinSoul extends SoulItem {

    public TinSoul(Properties properties) {
        super();
    }

    @Override
    public void criticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        if (player.getAttribute(AttributeRegister.CriticalChance) instanceof AttributeInstance CriticalChance && player.getAttribute(AttributeRegister.CriticalDamage) instanceof AttributeInstance CriticalDamage) {
            double CriticalChanceAmount = (CurioUtils.isEquipped(player, TerraPower.class) ? 0.2 : 0.1) - CriticalChance.getValue();
            AttributeUtils.condition(
                    player,
                    AttributeRegister.CriticalChance,
                    FargoSoulItemRegister.TinSoulItem.getId(),
                    CriticalChanceAmount,
                    AttributeModifier.Operation.ADD_VALUE,
                    CurioUtils.isEquipped(player, TinSoul.class) && CriticalChanceAmount > 0
            );
            double CriticalDamageAmount = 2.0 - CriticalDamage.getValue();
            AttributeUtils.condition(
                    player,
                    AttributeRegister.CriticalDamage,
                    FargoSoulItemRegister.TinSoulItem.getId(),
                    CriticalDamageAmount,
                    AttributeModifier.Operation.ADD_VALUE,
                    CurioUtils.isEquipped(player, TinSoul.class) && CriticalDamageAmount > 0
            );
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(player, TinSoul.class);
            soulInfo.setMaxStacks((CurioUtils.isEquipped(player, TerraPower.class) ? 100 : 60));
            AttributeUtils.condition(
                    player,
                    AttributeRegister.CriticalChance,
                    ResourceLocation.fromNamespaceAndPath(FargoSoul.MODID, "tin_soul_addition"),
                    soulInfo.getStacks() * 0.01,
                    AttributeModifier.Operation.ADD_VALUE,
                    CurioUtils.isEquipped(player, TinSoul.class) && soulInfo.getStacks() > 0
            );
            if (CurioUtils.isEquipped(player, TinSoul.class)) {
                if (player.getRandom().nextDouble() < CriticalChance.getValue()) {
                    soulInfo.addStacks(10);
                    event.setCriticalHit(true);
                    event.setDamageMultiplier((float) CriticalDamage.getValue());
                }
            }
        }
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            if (CurioUtils.isEquipped(player, TinSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(player, TinSoul.class);
                soulInfo.shrinkStacks(soulInfo.getStacks() / 2);
            }
        }
    }

}

