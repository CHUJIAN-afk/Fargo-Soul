package first.fargo_soul.common.item.terraSoul.willPower;

import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.gui.SoulRenderType;
import first.fargo_soul.common.event.modEvent.SprintEvent;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.WillPower;
import first.fargo_soul.register.AttributeRegister;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.utils.AttributeUtils;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class RedRidingSoul extends SoulItem {

    public RedRidingSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, RedRidingSoul.class);
            AttributeUtils.condition(
                    ticker,
                    Attributes.MOVEMENT_SPEED,
                    FargoSoulItemRegister.RedRidingSoulItem.getId(),
                    soulInfo.getStacks() * 0.01f,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                    CurioUtils.isEquipped(ticker, RedRidingSoul.class) && soulInfo.getStacks() > 0
            );
            AttributeUtils.condition(
                    ticker,
                    AttributeRegister.ArmorPierce,
                    FargoSoulItemRegister.RedRidingSoulItem.getId(),
                    soulInfo.getStacks() * 0.01f,
                    AttributeModifier.Operation.ADD_VALUE,
                    CurioUtils.isEquipped(ticker, RedRidingSoul.class) && soulInfo.getStacks() > 0
            );
        }
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, RedRidingSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(attacker, RedRidingSoul.class);
                soulInfo.setMaxStacks(CurioUtils.isEquipped(attacker, WillPower.class) ? 15 : 10);
                soulInfo.addStacks();
                if (target.getArmorValue() > 0) {
                    event.setAmount(event.getAmount() * 1.2f);
                }
            }
        }
        if (!event.isCanceled() && event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, RedRidingSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(target, RedRidingSoul.class);
                soulInfo.removeStacks();
            }
        }
    }

    @Override
    public void sprintClient(SprintEvent.Client event) {
        Player player = event.getEntity();
        if (CurioUtils.isEquipped(player, RedRidingSoul.class)) {
            SoulAbilityData.SoulInfo info = SoulUtils.getSoulInfo(player, RedRidingSoul.class);
            if (info.getStacks() == info.getMaxStacks()) {
                event.setVec3(event.getVec3().scale(1.5));
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, RedRidingSoul.class, SoulRenderType.Stack);
    }

}
