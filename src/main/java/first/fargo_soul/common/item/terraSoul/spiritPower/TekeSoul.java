package first.fargo_soul.common.item.terraSoul.spiritPower;

import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.gui.SoulRenderType;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.SpiritPower;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class TekeSoul extends SoulItem {

    public TekeSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, TekeSoul.class);
            soulInfo.setMaxCooldown(CurioUtils.isEquipped(ticker, SpiritPower.class) ? 3600 : 6000);
        }
    }

    @Override
    public void death(LivingDeathEvent event) {
        LivingEntity living = event.getEntity();
        if (!event.isCanceled() && living instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() instanceof LivingEntity owner && !owner.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(owner, TekeSoul.class);
            if (soulInfo.isReady() && CurioUtils.isEquipped(owner, TekeSoul.class)) {
                living.heal(owner.getHealth() * 0.25f);
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, TekeSoul.class, SoulRenderType.Cooldown);
    }

}
