package First.fargo_soul.common.item.terraSoul.lifePower;

import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.event.modEvent.PlayerFlyEvent;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.LifePower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.AttributeRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

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
    public void fly(PlayerFlyEvent event) {
        if (CurioUtils.isEquipped(event.getEntity(), BeetleSoul.class)) {
            event.setAllowingFly(true);
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, "BeetleEndurance", SoulRenderType.Stack);
        soulRenderManager.add(this, "BeetleMight", SoulRenderType.Stack);
    }

}
