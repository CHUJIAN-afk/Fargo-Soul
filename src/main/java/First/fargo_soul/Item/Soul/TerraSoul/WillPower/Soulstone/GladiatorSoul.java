package First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone;

import First.fargo_soul.Entity.Arrow.Banner;
import First.fargo_soul.Entity.EntityRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.KeyUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import static First.fargo_soul.Utils.CustomUtils.random;

public class GladiatorSoul extends SoulItem {

    public GladiatorSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }


    public static void GladiatorSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.GladiatorSoul.get())) {
            if (!player.serverLevel().getEntitiesOfClass(Banner.class, player.getBoundingBox().inflate(4)).isEmpty()) {
                event.setAmount(event.getAmount() * 0.92f); }
        }
    }

    public static void GladiatorSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.GladiatorSoul.get())) {
            long GladiatorSoul = player.getPersistentData().getLong("GladiatorSoulBanner");
            if (GladiatorSoul < player.serverLevel().getGameTime() && KeyUtils.isShift(player)) {
                player.getPersistentData().putLong("GladiatorSoulBanner", player.serverLevel().getGameTime() + 200);
                Banner banner = new Banner(EntityRegister.Banner.get(), player.serverLevel());
                banner.setPos(player.position());
                player.serverLevel().addFreshEntity(banner);

            }
        }
    }

    public static void GladiatorSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.GladiatorSoul.get()) && event.getEntity() instanceof LivingEntity livingEntity && event.getSource().getWeaponItem() != null) {
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
                    arrow.getPersistentData().putBoolean("soul", true);
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
