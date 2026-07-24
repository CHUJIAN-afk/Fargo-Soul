package first.fargo_soul.common.item.terraSoul.earthPower;

import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.gui.SoulRenderType;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.EarthPower;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.utils.AttributeUtils;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class MithrilSoul extends SoulItem {

    public MithrilSoul(Properties properties) {
        super();
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, MithrilSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(attacker, MithrilSoul.class);
                soulInfo.setMaxCooldown(60);
                if (soulInfo.isReady()) {
                    soulInfo.setDuration(CurioUtils.isEquipped(attacker, EarthPower.class) ? 60 : 120);
                }
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                ResourceLocation resourceLocation = FargoSoulItemRegister.MithrilSoulItem.getId();
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
