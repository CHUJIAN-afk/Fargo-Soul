package First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Utils.Utils.random;

public class DarkArtistSoul extends SoulItem {

    public DarkArtistSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.dark_artist_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.dark_artist_soul.attribute.2").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.dark_artist_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void DarkArtistSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.DarkArtistSoul.get()) && event.getEntity() instanceof LivingEntity livingEntity && event.getSource().getWeaponItem() != null) {
            long DarkArtistSoul = player.getPersistentData().getLong("DarkArtistSoul");
            if (DarkArtistSoul < player.serverLevel().getGameTime()) {
                player.getPersistentData().putLong("DarkArtistSoul", player.serverLevel().getGameTime() + 200);
                for (int i = 0; i < 8; i++) {
                    Arrow arrow = new Arrow(EntityType.ARROW, player.serverLevel());
                    arrow.getPersistentData().putBoolean("soul", true);
                    Vec3 Pos = new Vec3(livingEntity.getRandomX(4), livingEntity.getRandomY() + 2, livingEntity.getRandomZ(4));
                    arrow.setPos(Pos);
                    arrow.setBaseDamage(event.getAmount() * 0.20);
                    Vec3 vec3 = livingEntity.getHitbox().getCenter().subtract(Pos).normalize();
                    arrow.shoot(vec3.x, vec3.y, vec3.z, 1F, 1.0F);
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
            long DarkArtistSoulFire = player.getPersistentData().getLong("DarkArtistSoulFire");
            if (DarkArtistSoulFire < player.serverLevel().getGameTime()) {
                player.getPersistentData().putLong("DarkArtistSoulFire", player.serverLevel().getGameTime() + 40);
                double x = player.getX() + 0.5;
                double y = player.getY() + 1;
                double z = player.getZ();
                Vec3 toMonster = livingEntity.getHitbox().getCenter().subtract(x, y, z).normalize();
                SmallFireball fireball = new SmallFireball(
                        player.level(),
                        x,
                        y,
                        z,
                        toMonster
                );
                fireball.setOwner(player);
                player.level().addFreshEntity(fireball);
                ParticleUtils.spawnParticleSphere(
                        player.serverLevel(),
                        x,
                        y,
                        z,
                        ParticleTypes.LAVA,
                        0.2f,
                        5,
                        0.2f
                );
            }
        }
    }




}

