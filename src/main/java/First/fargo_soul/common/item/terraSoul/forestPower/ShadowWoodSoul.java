package First.fargo_soul.common.item.terraSoul.forestPower;

import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.ForestPower;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class ShadowWoodSoul extends SoulItem {

    public ShadowWoodSoul(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, ShadowWoodSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = getInfo(ticker, ShadowWoodSoul.class);
                soulInfo.setMaxCooldown(100);
                if (soulInfo.isReady() && SoulUtils.getSoulTarget(ticker, 10) instanceof LivingEntity target) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    getInfo(target, ticker.getStringUUID() + "ShadowWoodSoul").setEnabled(true);
                }
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, ShadowWoodSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = getInfo(attacker, target.getStringUUID() + "ShadowWoodSoul");
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
