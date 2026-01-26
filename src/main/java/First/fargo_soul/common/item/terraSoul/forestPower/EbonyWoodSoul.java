package First.fargo_soul.common.item.terraSoul.forestPower;

import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.ForestPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class EbonyWoodSoul extends SoulItem {

    public EbonyWoodSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, EbonyWoodSoul.class)) {
                List<LivingEntity> livingEntityList = ticker.level().getEntitiesOfClass(LivingEntity.class, ticker.getBoundingBox().inflate(3));
                livingEntityList.removeIf(livingEntity -> CurioUtils.isEquipped(livingEntity, EbonyWoodSoul.class));
                for (LivingEntity target : livingEntityList) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(ticker.getScoreboardName());
                    soulInfo.setMaxStacks(CurioUtils.isEquipped(ticker, ForestPower.class) ? 200 : 100);
                    soulInfo.addStacks();
                }
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, EbonyWoodSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName());
                float newDamage = event.getAmount() * (1 + (soulInfo.getStacks() * 0.001f));
                event.setAmount(newDamage);
            }
            if (CurioUtils.isEquipped(target, EbonyWoodSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(attacker.getScoreboardName());
                float newDamage = event.getAmount() * (1 - (soulInfo.getStacks() * 0.0005f));
                event.setAmount(newDamage);
            }
        }
    }

}
