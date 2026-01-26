package First.fargo_soul.common.item.terraSoul.deathPower;

import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.DeathPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;


public class NinjaSoul extends SoulItem {

    public NinjaSoul(Properties properties) {
        super(properties);
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
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            ResourceLocation resourceLocation = ItemRegister.NinjaSoulItem.getId();
            SoulAbilityData.SoulInfo soulInfo = ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(NinjaSoul.class);
            soulInfo.setMaxStacks(CurioUtils.isEquipped(ticker, DeathPower.class) ? 500 : 300);
            boolean equipped = CurioUtils.isEquipped(ticker, NinjaSoul.class);
            if (equipped) {
                soulInfo.addStacks();
            }
            AttributeUtils.condition(ticker, Attributes.ATTACK_DAMAGE, AttributeUtils.base(resourceLocation, 0.01), equipped);
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getDirectEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(NinjaSoul.class);
            soulInfo.removeStacks();
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, NinjaSoul.class, SoulRenderType.Stack);
    }

}
