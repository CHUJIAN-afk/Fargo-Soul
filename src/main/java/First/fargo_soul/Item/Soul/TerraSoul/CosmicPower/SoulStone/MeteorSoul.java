package First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.client.player.LocalPlayer;
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

    private float energy = 0;
    private final float maxEnergy = 10;

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage1(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (SoulUtils.getSoulItemFromSoulData(attacker, MeteorSoul.class) instanceof MeteorSoul meteorSoul) {
                    if (target.getRandom().nextDouble() < 0.05 && ++meteorSoul.energy >= meteorSoul.maxEnergy) {
                        Level level = attacker.level();
                        SmallFireball fireball = new SmallFireball(EntityType.SMALL_FIREBALL, level);
                        Vec3 Pos = new Vec3(target.getRandomX(4), target.getRandomY() + 8, target.getRandomZ(4));
                        fireball.setPos(Pos);
                        Vec3 vec3 = target.getHitbox().getCenter().subtract(Pos).normalize();
                        fireball.shoot(vec3.x, vec3.y, vec3.z, 2F, 1.0F);
                        level.addFreshEntity(fireball);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker) {
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.MOVEMENT_SPEED,
                        SoulsRegister.MeteorSoul.getId(),
                        0.15,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        SoulUtils.getSoulItemFromSoulData(attacker, MeteorSoul.class) instanceof MeteorSoul
                );
            }
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void MeteorSoulMovementInputHandler(MovementInputUpdateEvent event) {
            if (event.getEntity() instanceof LocalPlayer player && SoulUtils.getSoulItemFromSoulData(player, MeteorSoul.class) instanceof MeteorSoul) {
                if (player.getDeltaMovement().y() < 0 && event.getInput().shiftKeyDown) {
                    player.addDeltaMovement(new Vec3(0, Math.max(player.getDeltaMovement().y() * 1.05, -1.0), 0));
                }
            }
        }

    }

}
