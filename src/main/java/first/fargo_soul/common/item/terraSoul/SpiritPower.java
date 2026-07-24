package first.fargo_soul.common.item.terraSoul;

import first.fargo_soul.common.event.modEvent.PlayerFlyEvent;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.spiritPower.GhostSoul;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

import java.util.List;

import static first.fargo_soul.register.FargoSoulItemRegister.*;

public class SpiritPower extends SoulItem {

    public SpiritPower(Properties properties) {
        super();
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                AncientHolySoulItem.get(),
                ForbiddenSoulItem.get(),
                GhostSoulItem.get(),
                HolySoulItem.get(),
                TekeSoulItem.get()
        );
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            if (ticker.tickCount % 20 == 0 && CurioUtils.isEquipped(ticker, SpiritPower.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, GhostSoul.class);
                if (soulInfo.getStacks() < soulInfo.getMaxStacks()) {
                    soulInfo.addStacks(5);
                } else if (ticker.getHealth() < ticker.getMaxHealth()) {
                    ticker.heal(5);
                }
            }
        }
    }

    @Override
    public void fly(PlayerFlyEvent event) {
        if (CurioUtils.isEquipped(event.getEntity(), SpiritPower.class)) {
            event.setMaxFlyTime(event.getMaxFlyTime() * 2);
        }
    }

    @Override
    public void heal(LivingHealEvent event) {
        LivingEntity living = event.getEntity();
        if (!living.level().isClientSide() && CurioUtils.isEquipped(living, SpiritPower.class)) {
            event.setAmount(event.getAmount() * 1.7f);
        }
    }

}
