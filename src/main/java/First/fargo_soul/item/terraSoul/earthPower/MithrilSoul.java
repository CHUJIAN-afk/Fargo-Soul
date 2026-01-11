package First.fargo_soul.item.terraSoul.earthPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.EarthPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.RenderUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

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
    public List<Component> getGuiTooltip(Player player) {
        List<Component> tooltip = super.getGuiTooltip(player);
        SoulAbilityData.SoulInfo soulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(MithrilSoul.class);
        tooltip.add(RenderUtils.createCooldownTooltip(this, "秘银知识冷却", soulInfo));
        tooltip.add(RenderUtils.createDurationTooltip(this, "秘银知识", soulInfo));
        return tooltip;
    }

}
