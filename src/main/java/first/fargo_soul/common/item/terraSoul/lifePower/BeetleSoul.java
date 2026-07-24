package first.fargo_soul.common.item.terraSoul.lifePower;

import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.gui.SoulRenderType;
import first.fargo_soul.common.event.modEvent.PlayerFlyEvent;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.LifePower;
import first.fargo_soul.register.AttributeRegister;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.utils.AttributeUtils;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class BeetleSoul extends SoulItem {

    public BeetleSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.SoulInfo info = SoulUtils.getSoulInfo(ticker, "BeetleMight");
            AttributeUtils.condition(
                    ticker,
                    AttributeRegister.ArmorPierce,
                    FargoSoulItemRegister.BeetleSoulItem.getId(),
                    info.getStacks() * 3,
                    AttributeModifier.Operation.ADD_VALUE,
                    CurioUtils.isEquipped(ticker, BeetleSoul.class) && info.getStacks() > 0
            );
            if (CurioUtils.isEquipped(ticker, BeetleSoul.class)) {
                if (SoulUtils.getSoulInfo(ticker, "BeetleEndurance") instanceof SoulAbilityData.SoulInfo soulInfo) {
                    soulInfo.setMaxStacks(CurioUtils.isEquipped(ticker, LifePower.class) ? 3 : 2);
                    soulInfo.setMaxCooldown(140);
                    if (ticker.tickCount % 140 == 0) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        soulInfo.addStacks();
                    }
                }
                if (SoulUtils.getSoulInfo(ticker, "BeetleMight") instanceof SoulAbilityData.SoulInfo soulInfo) {
                    soulInfo.setMaxStacks(CurioUtils.isEquipped(ticker, LifePower.class) ? 6 : 4);
                    soulInfo.setMaxCooldown(20);
                    if (ticker.tickCount % 20 == 0) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        soulInfo.shrinkStacks();
                    }
                }
            }
        }
    }

    @Override
    public void targetChange(LivingChangeTargetEvent event) {
        if (event.getEntity() instanceof LivingEntity attacker && attacker.getType().is(EntityTypeTags.ARTHROPOD) && event.getNewAboutToBeSetTarget() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (!SoulUtils.recentlyAttacked(target, attacker) && CurioUtils.isEquipped(target, BeetleSoul.class)) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, BeetleSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(target, "BeetleEndurance");
                event.setAmount(event.getAmount() * (1 - (soulInfo.getStacks() * 0.15f)));
                soulInfo.shrinkStacks();
            }
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, BeetleSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(target, "BeetleMight");
                soulInfo.addStacks(2);
            }
        }
    }

    @Override
    public void fly(PlayerFlyEvent event) {
        event.setAllowingFly(true);
    }
}
