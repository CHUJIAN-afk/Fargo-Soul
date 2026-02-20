package First.fargo_soul.common.item.terraSoul.cosmicPower;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.CosmicPower;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.client.player.Input;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
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

import java.util.Map;
import java.util.concurrent.TimeUnit;

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
                double chance = CurioUtils.isEquipped(attacker, CosmicPower.class) ? 0.8 : 0.2;
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(attacker, MeteorSoul.class);
                soulInfo.setMaxCooldown(20);
                if (soulInfo.isReady()) {
                    Level level = attacker.level();
                    int count = 0;
                    while (target.getRandom().nextDouble() < chance) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        SoulUtils.executorService.schedule(() -> {
                            SmallFireball fireball = new SmallFireball(EntityType.SMALL_FIREBALL, level);
                            fireball.clearFire();
                            SoulUtils.shootTargetFromAttaker(fireball, attacker, target, 2, 2);
                            SoulUtils.setAbilityInvulnerable(fireball);
                            SoulUtils.playSound(level, fireball.position(), SoundEvents.GHAST_SHOOT, fireball.getSoundSource());
                        }, count * 200L, TimeUnit.MILLISECONDS);
                        count++;
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
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, MeteorSoul.class, SoulRenderType.Cooldown);
    }

}