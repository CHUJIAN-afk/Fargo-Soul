package First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.CustomUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class MeteorSoul extends SoulItem {

    public MeteorSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));

    }

    public static void MeteorSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.MeteorSoul.get())) {
            if (event.getEntity() instanceof LivingEntity livingEntity && CustomUtils.random.nextDouble() < 0.05) {
                SmallFireball fireball = new SmallFireball(EntityType.SMALL_FIREBALL, player.serverLevel());
                Vec3 Pos = new Vec3(livingEntity.getRandomX(4), livingEntity.getRandomY() + 8, livingEntity.getRandomZ(4));
                fireball.setPos(Pos);
                Vec3 vec3 = livingEntity.getHitbox().getCenter().subtract(Pos).normalize();
                fireball.shoot(vec3.x, vec3.y, vec3.z, 2F, 1.0F);
                player.level().addFreshEntity(fireball);
            }
        }
    }

    public static void MeteorSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ResourceLocation resourceLocation = SoulsRegister.MeteorSoul.getId();
            if (CurioUtils.isEquipped(player, SoulsRegister.MeteorSoul.get())) {
                AttributeUtils.addAttributeModifier(player, Attributes.MOVEMENT_SPEED, resourceLocation, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
            } else {
                AttributeUtils.removeAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation);
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    public static void MeteorSoulMovementInputHandler(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player && CurioUtils.isEquipped(player, SoulsRegister.MeteorSoul.get())) {
            if (player.getDeltaMovement().y() < 0 && event.getInput().shiftKeyDown) {
                player.addDeltaMovement(new Vec3(0, Math.max(player.getDeltaMovement().y() * 1.05, -1.0), 0));
            }
        }
    }

}
