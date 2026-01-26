package First.fargo_soul.common.item.terraSoul.forestPower;

import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.ForestPower;
import First.fargo_soul.register.AttachmentRegister;
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
                SoulAbilityData.SoulInfo soulInfo = ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PineWoodSoul.class);
                soulInfo.setMaxCooldown(100);
                if (soulInfo.getCooldown() == 0 && SoulUtils.getSoulTarget(ticker, 10) instanceof LivingEntity target) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    SoulAbilityData.SoulInfo info = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(ticker.getScoreboardName());
                    info.setEnabled(true);
                }
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (!attacker.equals(target) && CurioUtils.isEquipped(target, ShadowWoodSoul.class) && SoulUtils.canAttack(ShadowWoodSoul.class, attacker, attacker)) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(target.getScoreboardName());
                if (soulInfo.isEnabled()) {
                    int amount = CurioUtils.isEquipped(target, ForestPower.class) ? 3 : 2;
                    SoulUtils.attack(ShadowWoodSoul.class, attacker, target, attacker, DamageTypes.MAGIC, amount);
                    target.heal(amount);
                }
            }
        }
    }

}
