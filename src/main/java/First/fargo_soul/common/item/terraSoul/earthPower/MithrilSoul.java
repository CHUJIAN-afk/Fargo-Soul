package First.fargo_soul.common.item.terraSoul.earthPower;

import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.EarthPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class MithrilSoul extends SoulItem {

    public MithrilSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, MithrilSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(MithrilSoul.class);
                soulInfo.setMaxCooldown(60);
                if (soulInfo.isReady()) {
                    soulInfo.setDuration(CurioUtils.isEquipped(attacker, EarthPower.class) ? 60 : 120);
                }
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                ResourceLocation resourceLocation = ItemRegister.MithrilSoulItem.getId();
                AttributeUtils.condition(attacker, Attributes.ATTACK_SPEED, AttributeUtils.base(resourceLocation, 0.5), soulInfo.getDuration() > 0);
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, MithrilSoul.class, SoulRenderType.Cooldown);
        soulRenderManager.add(this, MithrilSoul.class, SoulRenderType.Duration);
    }

}
