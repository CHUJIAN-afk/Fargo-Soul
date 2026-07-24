package first.fargo_soul.common.item.terraSoul;

import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.gui.SoulRenderType;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

import static first.fargo_soul.register.FargoSoulItemRegister.*;

public class WillPower extends SoulItem {

    public WillPower(Properties properties) {
        super();
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                GladiatorSoulItem.get(),
                GoldSoulItem.get(),
                PlatinumSoulItem.get(),
                RedRidingSoulItem.get(),
                ValhallaKnightSoulItem.get()
        );
    }

    @Override
    public void heal(LivingHealEvent event) {
        LivingEntity living = event.getEntity();
        if (CurioUtils.isEquipped(living, WillPower.class) && !living.level().isClientSide()) {
            event.setAmount(event.getAmount() * 1.25f);
        }
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, WillPower.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(attacker, WillPower.class);
                soulInfo.setDuration(40);
                soulInfo.addStacks();
                event.setAmount(event.getAmount() * (1 + (float) soulInfo.getStacks() / soulInfo.getMaxStacks()));
            }
        }
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide() && CurioUtils.isEquipped(ticker, WillPower.class)) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, WillPower.class);
            soulInfo.setMaxStacks(200);
            if (soulInfo.getDuration() == 0) {
                soulInfo.shrinkStacks();
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, WillPower.class, SoulRenderType.Stack);
        soulRenderManager.add(this, WillPower.class, SoulRenderType.Duration);
    }

}
