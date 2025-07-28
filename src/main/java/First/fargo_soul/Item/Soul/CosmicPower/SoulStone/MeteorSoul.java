package First.fargo_soul.Item.Soul.CosmicPower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

import static First.fargo_soul.Utils.MathUtils.random;

public class MeteorSoul extends SoulItem {

    public MeteorSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.meteor_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.meteor_soul.attribute.2").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.meteor_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void MeteorSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.MeteorSoul.get())) {
            if (event.getEntity() instanceof LivingEntity livingEntity && random.nextDouble() < 0.05) {
                SmallFireball fireball = new SmallFireball(EntityType.SMALL_FIREBALL, player.serverLevel());
                Vec3 Pos = new Vec3(livingEntity.getRandomX(4), livingEntity.getRandomY() + 8, livingEntity.getRandomZ(4));
                fireball.setPos(Pos);
                Vec3 vec3 = livingEntity.getHitbox().getCenter().subtract(Pos).normalize();
                fireball.shoot(vec3.x, vec3.y, vec3.z, 2F, 1.0F);
                player.level().addFreshEntity(fireball);
            }
        }
    }

    public static void MeteorSoulMovementInputHandler(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player && CurioUtils.isEquipped(player, Souls.MeteorSoul.get())) {
            if (player.getDeltaMovement().y() < 0 && event.getInput().shiftKeyDown) {
                player.addDeltaMovement(new Vec3(0, Math.max(player.getDeltaMovement().y() * 1.05, -1.0), 0));
            }
        }
    }

}
