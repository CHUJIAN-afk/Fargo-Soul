package First.fargo_soul.common.item.terraSoul;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class EarthPower extends SoulItem {

    public EarthPower(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                AdamantiteSoulItem.get(),
                CobaltSoulItem.get(),
                MithrilSoulItem.get(),
                OrichalcumSoulItem.get(),
                PalladiumSoulItem.get(),
                TitaniumSoulItem.get()
        );
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            if (CurioUtils.isEquipped(attacker, EarthPower.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(attacker, EarthPower.class);
                soulInfo.shrinkStacks(5);
                soulInfo.setCooldown(40);
                if (soulInfo.getMaxStacks() > 0) {
                    float scale = 1 + (0.4f * soulInfo.getStacks() / soulInfo.getMaxStacks());
                    event.setAmount(event.getAmount() * scale);
                }
            }
        }
        if (event.getEntity() instanceof LivingEntity target) {
            if (CurioUtils.isEquipped(target, EarthPower.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(target, EarthPower.class);
                if (soulInfo.getMaxStacks() > 0) {
                    float scale = 1 - (0.25f * soulInfo.getStacks() / soulInfo.getMaxStacks());
                    event.setAmount(event.getAmount() * scale);
                }
            }
        }
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            boolean equipped = CurioUtils.isEquipped(ticker, EarthPower.class);
            SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(ticker, EarthPower.class);
            AttributeUtils.condition(
                    ticker,
                    Attributes.ATTACK_SPEED,
                    AttributeUtils.base(EarthPowerItem.getId(), (double) soulInfo.getStacks() / soulInfo.getMaxStacks()),
                    equipped && soulInfo.getMaxStacks() > 0 && soulInfo.getStacks() > 0
            );
            if (equipped) {
                soulInfo.setMaxCooldown(40);
                soulInfo.setMaxStacks(400);
                if (soulInfo.isReady()) {
                    soulInfo.addStacks();
                }
                if (ticker.tickCount % 20 == 0 && ticker.getHealth() < ticker.getMaxHealth() && soulInfo.getMaxStacks() > 0) {
                    ticker.heal(5 * ((float) soulInfo.getStacks() / soulInfo.getMaxStacks()));
                }
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, EarthPower.class, SoulRenderType.Cooldown);
        soulRenderManager.add(this, EarthPower.class, SoulRenderType.Stack);
    }

}
