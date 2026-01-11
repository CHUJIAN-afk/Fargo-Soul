package First.fargo_soul.item.terraSoul.cosmicPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.CosmicPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class BlazeSoul extends SoulItem {

    public BlazeSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(ticker, BlazeSoul.class);
            AttributeUtils.condition(
                    ticker,
                    Attributes.KNOCKBACK_RESISTANCE,
                    AttributeUtils.base(ItemRegister.BlazeSoulItem.getId(), 1.0),
                    soulInfo.isEnabled()
            );
            if (soulInfo.isEnabled()) {
                if (ticker.tickCount % 20 == 0) {
                    soulInfo.shrinkStacks((int) Math.ceil(soulInfo.getMaxStacks() * 0.025));
                }
                if (soulInfo.getStacks() <= soulInfo.getMinStacks()) {
                    soulInfo.setEnabled(false);
                }
            }
            if (!soulInfo.isEnabled() && CurioUtils.isEquipped(ticker, BlazeSoul.class)) {
                soulInfo.setMaxStacks((int) ((ticker.getMaxHealth() * 20) + 2000));
                if (soulInfo.getStacks() >= soulInfo.getMaxStacks()) {
                    soulInfo.setEnabled(true);
                }
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, BlazeSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(BlazeSoul.class);
                float scale = soulInfo.isEnabled() ? 0.5f : (1 - (soulInfo.getStackPercentage() * 0.4f));
                event.setAmount(event.getAmount() * scale);
                if (soulInfo.isEnabled()) {
                    event.setAmount(Math.max(event.getAmount() - target.getMaxHealth() * 0.1f, 0));
                }
            }
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, BlazeSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(BlazeSoul.class);
                if (!attacker.equals(target) && soulInfo.isEnabled() && SoulUtils.canAttack(BlazeSoul.class, target, target)) {
                    soulInfo.shrinkStacks(100);
                    Level level = attacker.level();
                    List<LivingEntity> livingEntityList = level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(2));
                    livingEntityList.remove(attacker);
                    for (LivingEntity entity : livingEntityList) {
                        float amount = event.getAmount() * 2.75f + target.getMaxHealth() * 0.025f;
                        SoulUtils.attack(BlazeSoul.class, target, attacker, entity, DamageTypes.ON_FIRE, amount);
                    }
                    ParticleUtils.spawnParticleSphere(
                            (ServerLevel) level,
                            target.getX(),
                            target.getBoundingBox().getCenter().y(),
                            target.getZ(),
                            ParticleTypes.LAVA,
                            1f,
                            60,
                            0.5f
                    );
                    SoulUtils.playSound(
                            level,
                            target.position(),
                            SoundEvents.GENERIC_EXPLODE.value(),
                            SoundSource.PLAYERS
                    );
                } else {
                    soulInfo.addStacks((int) (event.getAmount() * (CurioUtils.isEquipped(attacker, CosmicPower.class) ? 0.4f : 0.25f)));
                }
            }
        }
    }

    @Override
    public List<Component> getGuiTooltip(Player player) {
        List<Component> tooltip = new ArrayList<>();
        SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(player, BlazeSoul.class);
        tooltip.add(RenderUtils.createStackTooltip(this, soulInfo.isEnabled() ? "日耀之力" : "日耀能量", soulInfo));
        return tooltip;
    }

}