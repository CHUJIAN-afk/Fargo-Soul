package First.fargo_soul.common.item.terraSoul;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.register.AttributeRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class CosmicPower extends SoulItem {

    public CosmicPower(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                BlazeSoulItem.get(),
                NebulaSoulItem.get(),
                StardustSoulItem.get(),
                VortexSoulItem.get(),
                MeteorSoulItem.get(),
                WizardSoulItem.get()
        );
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, CosmicPower.class)) {
                SoulUtils.getSoulInfo(attacker, switch (attacker.getRandom().nextInt(5)) {
                    case 0 -> "CosmicPowerBlazeSoul";
                    case 1 -> "CosmicPowerNebulaSoul";
                    case 2 -> "CosmicPowerStardustSoul";
                    case 3 -> "CosmicPowerMeteorSoul";
                    default -> "CosmicPowerVortexSoul";
                }).setDuration(60);
            }
            if (SoulUtils.getSoulInfo(attacker, "CosmicPowerStardustSoul").isActive()) {
                event.setAmount(event.getAmount() * 1.25f);
            }
        }
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, CosmicPower.class)) {
                if (SoulUtils.getSoulInfo(target, "CosmicPowerBlazeSoul").isActive()) {
                    event.setAmount(event.getAmount() * 0.85f);
                }
            }
        }
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            boolean equipped = CurioUtils.isEquipped(ticker, CosmicPower.class);
            AttributeUtils.condition(
                    ticker,
                    AttributeRegister.CriticalChance,
                    AttributeUtils.base(CosmicPowerItem.getId(), 0.25),
                    equipped && SoulUtils.getSoulInfo(ticker, "CosmicPowerVortexSoul").isActive()
            );
            AttributeUtils.condition(
                    ticker,
                    Attributes.MAX_HEALTH,
                    AttributeUtils.value(CosmicPowerItem.getId(), 60),
                    equipped && SoulUtils.getSoulInfo(ticker, "CosmicPowerMeteorSoul").isActive()
            );
            if (equipped && ticker.tickCount % 20 == 0 && (SoulUtils.getSoulInfo(ticker, "CosmicPowerNebulaSoul").isActive()) && ticker.getHealth() < ticker.getMaxHealth()) {
                ticker.heal(5);
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, "CosmicPowerBlazeSoul", SoulRenderType.Duration);
        soulRenderManager.add(this, "CosmicPowerNebulaSoul", SoulRenderType.Duration);
        soulRenderManager.add(this, "CosmicPowerStardustSoul", SoulRenderType.Duration);
        soulRenderManager.add(this, "CosmicPowerVortexSoul", SoulRenderType.Duration);
        soulRenderManager.add(this, "CosmicPowerMeteorSoul", SoulRenderType.Duration);
    }

}
