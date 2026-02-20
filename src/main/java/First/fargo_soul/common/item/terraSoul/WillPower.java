package First.fargo_soul.common.item.terraSoul;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class WillPower extends SoulItem {

    public WillPower(Properties properties) {
        super(properties);
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
    public void hurt(LivingIncomingDamageEvent event) {
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
    public void tick(LivingEntity ticker) {
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
