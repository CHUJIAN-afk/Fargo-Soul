package First.fargo_soul.item.terraSoul.spiritPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.SpiritPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.RenderUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.List;

public class TekeSoul extends SoulItem {

    public TekeSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.getSoulInfo(ticker, TekeSoul.class).setMaxCooldown(CurioUtils.isEquipped(ticker, SpiritPower.class) ? 3600 : 6000);
        }
    }

    @Override
    public void death(LivingDeathEvent event) {
        if (!event.isCanceled() && event.getEntity() instanceof TamableAnimal animal && animal.getOwner() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TekeSoul.class);
            soulInfo.setMaxCooldown(CurioUtils.isEquipped(event.getEntity(), SpiritPower.class) ? 3600 : 6000);
            if (soulInfo.getCooldown() == 0 && CurioUtils.isEquipped(attacker, TekeSoul.class)) {
                animal.heal(attacker.getHealth() * 0.25f);
                event.setCanceled(true);
            }
        }
    }

    @Override
    public List<Component> getGuiTooltip(Player player) {
        List<Component> tooltip = super.getGuiTooltip(player);
        SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(player, TekeSoul.class);
        tooltip.add(RenderUtils.createCooldownTooltip(this, "复活冷却", soulInfo));
        return tooltip;
    }

}
