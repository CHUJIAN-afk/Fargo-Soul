package First.fargo_soul.item.terraSoul.lifePower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.LifePower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.AttributeRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.RenderUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class BeetleSoul extends SoulItem {

    public BeetleSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.SoulInfo info = ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleMight");
            AttributeUtils.condition(
                    ticker,
                    AttributeRegister.ArmorPierce,
                    ItemRegister.BeetleSoulItem.getId(),
                    info.getStacks() * 3,
                    AttributeModifier.Operation.ADD_VALUE,
                    CurioUtils.isEquipped(ticker, BeetleSoul.class) && info.getStacks() > 0
            );
            if (CurioUtils.isEquipped(ticker, BeetleSoul.class)) {
                if (ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleEndurance") instanceof SoulAbilityData.SoulInfo soulInfo) {
                    soulInfo.setMaxStacks(CurioUtils.isEquipped(ticker, LifePower.class) ? 3 : 2);
                    soulInfo.setMaxCooldown(140);
                    if (ticker.tickCount % 140 == 0) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        soulInfo.addStacks();
                    }
                }
                if (ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleMight") instanceof SoulAbilityData.SoulInfo soulInfo) {
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
            SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(target.getScoreboardName());
            if (CurioUtils.isEquipped(target, BeetleSoul.class) && !soulInfo.isEnabled()) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, BeetleSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleEndurance");
                event.setAmount(event.getAmount() * (1 - (soulInfo.getStacks() * 0.15f)));
                soulInfo.shrinkStacks();
            }
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, BeetleSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleMight");
                soulInfo.addStacks(2);
                target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName()).setEnabled(true);
            }
        }
    }

    @Override
    public List<Component> getGuiTooltip(Player player) {
        List<Component> tooltip = super.getGuiTooltip(player);
        tooltip.add(RenderUtils.createStackTooltip(this, "甲虫耐力", player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleEndurance")));
        tooltip.add(RenderUtils.createStackTooltip(this, "甲虫力量", player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("BeetleMight")));
        return tooltip;
    }

}
