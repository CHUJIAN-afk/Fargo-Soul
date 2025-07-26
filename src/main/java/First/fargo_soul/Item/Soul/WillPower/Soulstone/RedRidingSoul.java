package First.fargo_soul.Item.Soul.WillPower.Soulstone;

import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

import static First.fargo_soul.Utils.MathUtils.random;

public class RedRidingSoul extends SoulItem {

    public RedRidingSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("攻击忽略2点敌怪防御力并额外造成1点伤害").withStyle(ChatFormatting.BLUE),
            Component.literal("每次连续攻击会额外忽略2点防御力并多造成1点伤害").withStyle(ChatFormatting.BLUE),
            Component.literal("如果忽略防御力大于敌人防御力，每1点溢出值还会提供1%的伤害加成").withStyle(ChatFormatting.BLUE),
            Component.literal("在累计10次攻击后，生成一阵箭雨，造成远程伤害并重置加成").withStyle(ChatFormatting.BLUE),
            Component.literal("追踪武器和召唤物不会增加这些加成").withStyle(ChatFormatting.BLUE),
            Component.literal("1秒内没有进行攻击重置加成").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“小红帽，大坏蛋！”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void RedRidingSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.RedRidingSoul.get())) {
            long lastDamage = player.getPersistentData().getLong("RedRidingSoulLastDamage");
            long gameTime = player.serverLevel().getGameTime();
            if (lastDamage + 20 > gameTime) {
                if (event.getSource().getWeaponItem() != null && event.getEntity() instanceof LivingEntity livingEntity) {
                    player.getPersistentData().putLong("RedRidingSoulLastDamage", gameTime);
                    float damage = player.getPersistentData().getFloat("RedRidingSoul");
                    player.getPersistentData().putFloat("RedRidingSoul", Math.min(damage + 1, 10));
                    float ArmorPierce = (damage * 2) - livingEntity.getArmorValue();
                    if (ArmorPierce > 0) {
                        event.setAmount(event.getAmount() * (1 + (ArmorPierce * 0.01f)));
                    }
                    event.setAmount(event.getAmount() + damage);
                    if (damage == 10) {
                        player.getPersistentData().remove("RedRidingSoul");
                        for (int i = 0; i < 10; i++) {
                            Arrow arrow = new Arrow(EntityType.ARROW, player.serverLevel());
                            arrow.getPersistentData().putBoolean("soul", true);
                            Vec3 Pos = new Vec3(livingEntity.getRandomX(4), livingEntity.getRandomY() + 6, livingEntity.getRandomZ(4));
                            arrow.setPos(Pos);
                            arrow.setBaseDamage(event.getAmount() * 0.2);
                            Vec3 vec3 = livingEntity.getHitbox().getCenter().subtract(Pos).normalize();
                            arrow.shoot(vec3.x, vec3.y, vec3.z, 1.0F, 1.0F);
                            player.level().addFreshEntity(arrow);
                            player.serverLevel().playSound(
                                    null,
                                    player.getX(), player.getY(), player.getZ(),
                                    SoundEvents.ARROW_SHOOT,
                                    SoundSource.PLAYERS,
                                    1.0f,
                                    random.nextFloat() * 0.4f + 0.4f
                            );
                        }
                    }
                }
            } else {
                player.getPersistentData().putLong("RedRidingSoulLastDamage", gameTime);
                player.getPersistentData().remove("RedRidingSoul");
            }
        }
    }
}
