package First.fargo_soul.item.terraSoul.cosmicPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.CosmicPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.RenderUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.client.player.Input;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.Map;

public class MeteorSoul extends SoulItem {

    public MeteorSoul(Properties properties) {
        super(properties);
    }

    @Override
    public Map<Holder<Attribute>, AttributeModifier> getAttributeModifiers() {
        return Map.of(
                Attributes.MOVEMENT_SPEED, AttributeUtils.base(ItemRegister.MeteorSoulItem.getId(), 0.15)
        );
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, MeteorSoul.class)) {
                double chance = CurioUtils.isEquipped(attacker, CosmicPower.class) ? 0.1 : 0.05;
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(MeteorSoul.class);
                soulInfo.setMaxCooldown(20);
                if (soulInfo.isReady()) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    Level level = attacker.level();
                    while (target.getRandom().nextDouble() < chance) {
                        SmallFireball fireball = new SmallFireball(EntityType.SMALL_FIREBALL, level);
                        SoulUtils.shootTargetFromAttaker(fireball, attacker, target, 2, 2);
                        SoulUtils.setAbilityInvulnerable(fireball);
                        SoulUtils.playSound(level, fireball.position(), SoundEvents.GHAST_SHOOT, SoundSource.PLAYERS);
                    }
                }
            }
        }
    }

    @Override
    public void movementInput(@UnknownNullability Player player, Input input) {
        if (player.getDeltaMovement().y() < 0 && input.shiftKeyDown && CurioUtils.isEquipped(player, MeteorSoul.class)) {
            player.addDeltaMovement(new Vec3(0, Math.max(player.getDeltaMovement().y() * 1.05, -1.0), 0));
        }
    }

    @Override
    public List<Component> getGuiTooltip(Player player) {
        List<Component> tooltip = super.getGuiTooltip(player);
        SoulAbilityData.SoulInfo soulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(MeteorSoul.class);
        tooltip.add(RenderUtils.createCooldownTooltip(this, "流星冷却", soulInfo));
        return tooltip;
    }
    
}