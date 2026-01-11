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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.ArrayList;
import java.util.List;


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
    public List<Component> getGuiTooltip(Player player) {
        List<Component> list = new ArrayList<>();
        SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(player, AdamantiteSoul.class);
        list.add(RenderUtils.createStackTooltip(this, "精金镀层冷却", soulInfo));
        list.add(RenderUtils.createDurationTooltip(this, "精金镀层", soulInfo));
        return list;
    }

}
