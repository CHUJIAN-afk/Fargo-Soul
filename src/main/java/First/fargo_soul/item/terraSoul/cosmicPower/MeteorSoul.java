package First.fargo_soul.item.terraSoul.cosmicPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.CosmicPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class MeteorSoul extends SoulItem {

    public MeteorSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }
    
    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker) {
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.MOVEMENT_SPEED,
                        ItemRegister.MeteorSoulItem.getId(),
                        0.15,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        CurioUtils.isEquipped(attacker, MeteorSoul.class)
                );
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, MeteorSoul.class)) {
                    double chance = CurioUtils.isEquipped(attacker, CosmicPower.class) ? 0.1 : 0.05;
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(MeteorSoul.class);
                    soulInfo.setMaxCooldown(2);
                    if (soulInfo.isReady() && target.getRandom().nextDouble() < chance) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        Level level = attacker.level();
                        SmallFireball fireball = new SmallFireball(EntityType.SMALL_FIREBALL, level);
                        SoulUtils.shootTargetFromAttaker(fireball, attacker, target, 2, 2);
                        SoulUtils.setAbilityInvulnerable(fireball);
                        SoulUtils.playSound(
                                level,
                                fireball.position(),
                                SoundEvents.GHAST_SHOOT,
                                SoundSource.PLAYERS
                        );
                    }
                }
            }
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void MeteorSoulMovementInputHandler(MovementInputUpdateEvent event) {
            if (event.getEntity() instanceof LocalPlayer player && CurioUtils.isEquipped(player, MeteorSoul.class)) {
                if (player.getDeltaMovement().y() < 0 && event.getInput().shiftKeyDown) {
                    player.addDeltaMovement(new Vec3(0, Math.max(player.getDeltaMovement().y() * 1.05, -1.0), 0));
                }
            }
        }

    }

}
