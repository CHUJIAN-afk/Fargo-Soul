package first.fargo_soul.common.item.terraSoul.deathPower;

import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.gui.SoulRenderType;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.DeathPower;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.utils.AttributeUtils;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;


public class NinjaSoul extends SoulItem {

    public NinjaSoul(Properties properties) {
        super();
    }

    @Override
    public void targetChange(LivingChangeTargetEvent event) {
        if (event.getNewAboutToBeSetTarget() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, NinjaSoul.class) && event.getEntity() instanceof Mob mob) {
                LivingEntity originalAboutToBeSetTarget = event.getOriginalAboutToBeSetTarget();
                if ((originalAboutToBeSetTarget == null || !originalAboutToBeSetTarget.equals(target)) && mob.distanceTo(target) > 6) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            ResourceLocation resourceLocation = FargoSoulItemRegister.NinjaSoulItem.getId();
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, NinjaSoul.class);
            soulInfo.setMaxStacks(CurioUtils.isEquipped(ticker, DeathPower.class) ? 500 : 300);
            boolean equipped = CurioUtils.isEquipped(ticker, NinjaSoul.class);
            if (equipped) {
                soulInfo.addStacks();
            }
            AttributeUtils.condition(ticker, Attributes.ATTACK_DAMAGE, AttributeUtils.base(resourceLocation, 0.01), equipped);
        }
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getSource().getDirectEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(attacker, NinjaSoul.class);
            soulInfo.removeStacks();
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, NinjaSoul.class, SoulRenderType.Stack);
    }

}
