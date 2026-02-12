package First.fargo_soul.common.item.terraSoul.earthPower;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.EarthPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;


public class AdamantiteSoul extends SoulItem {

    public AdamantiteSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            ResourceLocation resourceLocation = ItemRegister.AdamantiteSoulItem.getId();
            SoulAbilityData.SoulInfo soulInfo = ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(AdamantiteSoul.class);
            if (soulInfo.getDuration() == 0) {
                soulInfo.removeStacks();
            }
            AttributeUtils.condition(ticker, Attributes.ATTACK_SPEED, AttributeUtils.base(resourceLocation, soulInfo.getStacks() * 0.05), CurioUtils.isEquipped(ticker, AdamantiteSoul.class) && soulInfo.getStacks() > 0);
            AttributeUtils.condition(ticker, Attributes.MOVEMENT_SPEED, AttributeUtils.base(resourceLocation, 0.15), CurioUtils.isEquipped(ticker, EarthPower.class) && soulInfo.getStacks() >= soulInfo.getMaxStacks());
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, AdamantiteSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(AdamantiteSoul.class);
                soulInfo.setMaxStacks(8);
                soulInfo.addStacks();
                soulInfo.setDuration(100);
                if (target instanceof Mob mob && mob.getTarget() != null && target.getRandom().nextDouble() < 0.05 && soulInfo.getStacks() == soulInfo.getMaxStacks()) {
                    mob.setTarget(null);
                }
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, AdamantiteSoul.class, SoulRenderType.Stack);
        soulRenderManager.add(this, AdamantiteSoul.class, SoulRenderType.Duration);
    }

}
