package First.fargo_soul.item.terraSoul.willPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.WillPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.AttributeRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.RenderUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class RedRidingSoul extends SoulItem {

    public RedRidingSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(RedRidingSoul.class);
            AttributeUtils.condition(
                    ticker,
                    Attributes.MOVEMENT_SPEED,
                    ItemRegister.RedRidingSoulItem.getId(),
                    soulInfo.getStacks() * 0.01f,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                    CurioUtils.isEquipped(ticker, RedRidingSoul.class) && soulInfo.getStacks() > 0
            );
            AttributeUtils.condition(
                    ticker,
                    AttributeRegister.ArmorPierce,
                    ItemRegister.RedRidingSoulItem.getId(),
                    soulInfo.getStacks() * 0.01f,
                    AttributeModifier.Operation.ADD_VALUE,
                    CurioUtils.isEquipped(ticker, RedRidingSoul.class) && soulInfo.getStacks() > 0
            );
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, RedRidingSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(RedRidingSoul.class);
                soulInfo.setMaxStacks(CurioUtils.isEquipped(attacker, WillPower.class) ? 15 : 10);
                soulInfo.addStacks();
                if (target.getArmorValue() > 0) {
                    event.setAmount(event.getAmount() * 1.2f);
                }
            }
        }
        if (!event.isCanceled() && event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, RedRidingSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(RedRidingSoul.class);
                soulInfo.removeStacks();
            }
        }
    }

    @Override
    public List<Component> getGuiTooltip(Player player) {
        List<Component> tooltip = super.getGuiTooltip(player);
        SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(player, RedRidingSoul.class);
        tooltip.add(RenderUtils.createStackTooltip(this, "游击", soulInfo));
        return tooltip;
    }

}
