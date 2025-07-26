package First.fargo_soul.Item.Soul.WillPower.Soulstone;

import First.fargo_soul.Entity.AbstractArrow.AbstractArrowRegister;
import First.fargo_soul.Entity.AbstractArrow.Banner.Banner;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.KeyUtils;
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
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

import static First.fargo_soul.Utils.MathUtils.random;

public class GladiatorSoul extends SoulItem {

    public GladiatorSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("箭雨将倾泄在被攻击的敌人身上，造成远程伤害，该效果有2秒冷却").withStyle(ChatFormatting.BLUE),
            Component.literal("双击潜行键召唤一个持续15秒的旗子，该效果有10秒冷却").withStyle(ChatFormatting.BLUE),
            Component.literal("靠近旗子会大幅增强箭雨").withStyle(ChatFormatting.BLUE),
            Component.literal("靠近旗子时可免疫击退，获得8%伤害减免和8%伤害加成").withStyle(ChatFormatting.BLUE),
            Component.literal("旗子同时只能存在一个").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“你不觉得刺激吗？”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void GladiatorSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.GladiatorSoul.get())) {
            if (!player.serverLevel().getEntitiesOfClass(Banner.class, player.getBoundingBox().inflate(4)).isEmpty()) {
                event.setAmount(event.getAmount() * 0.92f);
                //AttributeUtils.addAttributeModifier(player, Attributes.ATTACK_KNOCKBACK, Souls.GladiatorSoul.getId(), 1.0, AttributeModifier.Operation.ADD_VALUE);
                //ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                //executorService.schedule(() -> AttributeUtils.removeAttributeModifier(player, Attributes.ATTACK_KNOCKBACK, Souls.GladiatorSoul.getId()), 50, TimeUnit.MILLISECONDS);
            }
        }
    }

    public static void GladiatorSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.GladiatorSoul.get())) {
            long GladiatorSoul = player.getPersistentData().getLong("GladiatorSoulBanner");
            if (GladiatorSoul < player.serverLevel().getGameTime() && KeyUtils.isShift(player)) {
                player.getPersistentData().putLong("GladiatorSoulBanner", player.serverLevel().getGameTime() + 200);
                Banner banner = new Banner(AbstractArrowRegister.Banner.get(), player.serverLevel());
                banner.setPos(player.position());
                player.serverLevel().addFreshEntity(banner);

            }
        }
    }

    public static void GladiatorSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.GladiatorSoul.get()) && event.getEntity() instanceof LivingEntity livingEntity && event.getSource().getWeaponItem() != null) {
            long GladiatorSoul = player.getPersistentData().getLong("GladiatorSoul");
            if (GladiatorSoul < player.serverLevel().getGameTime()) {
                player.getPersistentData().putLong("GladiatorSoul", player.serverLevel().getGameTime() + 40);
                int size = 4;
                if (!player.serverLevel().getEntitiesOfClass(Banner.class, player.getBoundingBox().inflate(4)).isEmpty()) {
                    size = 16;
                    event.setAmount(event.getAmount() * 1.08f);
                }
                for (int i = 0; i < size; i++) {
                    Arrow arrow = new Arrow(EntityType.ARROW, player.serverLevel());
                    Vec3 Pos = new Vec3(livingEntity.getRandomX(4), livingEntity.getRandomY() + 6, livingEntity.getRandomZ(4));
                    arrow.setPos(Pos);
                    arrow.setBaseDamage(1);
                    Vec3 vec3 = livingEntity.getHitbox().getCenter().subtract(Pos).normalize();
                    if (size == 16) {
                        arrow.setBaseDamage(arrow.getBaseDamage() * 3);
                    }
                    arrow.shoot(vec3.x, vec3.y, vec3.z, 0.25f + random.nextFloat(0.5f), 1.0F);
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
    }








}
