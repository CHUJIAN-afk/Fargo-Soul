package first.fargo_soul.common.item.terraSoul.forestPower;

import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.ForestPower;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class ShadowWoodSoul extends SoulItem {

    public ShadowWoodSoul(Item.Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, ShadowWoodSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, ShadowWoodSoul.class);
                soulInfo.setMaxCooldown(100);
                if (soulInfo.isReady() && SoulUtils.getSoulTarget(ticker, 10) instanceof LivingEntity target) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    SoulAbilityData.SoulInfo info = SoulUtils.getSoulInfo(target, this.getClass().getSimpleName() + ticker.getStringUUID());
                    info.setEnabled(true);
                }
            }
        }
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, ShadowWoodSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(attacker, this.getClass().getSimpleName() + target.getStringUUID());
                if (soulInfo.isEnabled()) {
                    soulInfo.setEnabled(false);
                    int amount = CurioUtils.isEquipped(target, ForestPower.class) ? 3 : 2;
                    SoulUtils.attack(ShadowWoodSoul.class, attacker, target, attacker, DamageTypes.MAGIC, amount);
                    target.heal(amount);
                }
            }
        }
    }

}
