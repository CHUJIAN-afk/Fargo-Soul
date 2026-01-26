package First.fargo_soul.common.item.terraSoul.earthPower;

import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.EarthPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class PalladiumSoul extends SoulItem {

    public PalladiumSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            double chance = CurioUtils.isEquipped(attacker, EarthPower.class) ? 0.2 : 0.1;
            if (CurioUtils.isEquipped(attacker, PalladiumSoul.class) && attacker.getRandom().nextDouble() < chance) {
                int maxLevel = CurioUtils.isEquipped(attacker, EarthPower.class) ? 3 : 2;
                SoulUtils.applyOrUpdateEffect(attacker, MobEffects.REGENERATION, 59, maxLevel);
            }
        }
    }

    @Override
    public void heal(LivingHealEvent event) {
        if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, PalladiumSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PalladiumSoul.class);
                soulInfo.setMaxStacks(10);
                soulInfo.addStacks((int) Math.ceil(event.getAmount()));
                if (soulInfo.getStacks() == soulInfo.getMaxStacks()) {
                    soulInfo.removeStacks();
                    attacker.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 119));
                }
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, PalladiumSoul.class, SoulRenderType.Stack);
    }

}
