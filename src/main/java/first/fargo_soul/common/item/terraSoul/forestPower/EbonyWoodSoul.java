package first.fargo_soul.common.item.terraSoul.forestPower;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.ForestPower;
import first.fargo_soul.register.AttachmentRegister;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class EbonyWoodSoul extends SoulItem {

    public EbonyWoodSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, EbonyWoodSoul.class)) {
                List<LivingEntity> livingEntityList = ticker.level().getEntitiesOfClass(LivingEntity.class, ticker.getBoundingBox().inflate(3));
                livingEntityList.removeIf(livingEntity -> CurioUtils.isEquipped(livingEntity, EbonyWoodSoul.class));
                for (LivingEntity target : livingEntityList) {
                    SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(target, this.getClass().getSimpleName() + ticker.getStringUUID());
                    soulInfo.setMaxStacks(CurioUtils.isEquipped(ticker, ForestPower.class) ? 200 : 100);
                    soulInfo.addStacks();
                }
            }
        }
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, EbonyWoodSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(FargoSoul.rl(this.getClass().getSimpleName() + attacker.getStringUUID()));
                float newDamage = event.getAmount() * (1 + (soulInfo.getStacks() * 0.001f));
                event.setAmount(newDamage);
            }
            if (CurioUtils.isEquipped(target, EbonyWoodSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(FargoSoul.rl(this.getClass().getSimpleName() + attacker.getStringUUID()));
                float newDamage = event.getAmount() * (1 - (soulInfo.getStacks() * 0.0005f));
                event.setAmount(newDamage);
            }
        }
    }

}
