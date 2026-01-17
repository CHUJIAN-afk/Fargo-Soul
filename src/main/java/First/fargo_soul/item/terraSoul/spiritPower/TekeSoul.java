package First.fargo_soul.item.terraSoul.spiritPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.SpiritPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

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
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, TekeSoul.class, SoulRenderType.Cooldown);
    }

}
