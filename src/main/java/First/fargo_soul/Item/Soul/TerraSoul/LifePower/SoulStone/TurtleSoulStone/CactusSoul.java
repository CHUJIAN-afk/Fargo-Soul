package First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone.TurtleSoulStone;

import First.fargo_soul.Entity.Arrow.NeedleProjectile;
import First.fargo_soul.Entity.EntityRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class CactusSoul extends SoulItem {
    public CactusSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }


    public static void CactusSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.CactusSoul.get())) {
            if (event.getSource().is(DamageTypes.CACTUS)) {
                event.setCanceled(true);
            }
        }
    }

    public static void CactusSoulDamageHandler2(LivingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.CactusSoul.get())) {
            long CactusSoul = player.getPersistentData().getLong("CactusSoul");
            long gameTime = player.serverLevel().getGameTime();
            if (CactusSoul < gameTime) {
                player.getPersistentData().putLong("CactusSoul", gameTime + 5);
                ServerLevel level = player.serverLevel();
                for (int i = 0; i < 20; i++) {
                    NeedleProjectile needle = new NeedleProjectile(EntityRegister.Needle.get(), level);
                    needle.setOwner(player);
                    needle.setBaseDamage(2.0);
                    double theta = level.random.nextDouble() * Math.PI * 2;
                    double phi = Math.acos(2 * level.random.nextDouble() - 1);
                    double r = 0.5 + level.random.nextDouble() * 0.3;
                    Vec3 offset = new Vec3(r * Math.sin(phi) * Math.cos(theta), r * Math.sin(phi) * Math.sin(theta), r * Math.cos(phi));
                    Vec3 spawnPos = player.position().add(offset);
                    Vec3 velocity = offset.normalize().scale(0.8);
                    needle.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, player.getYRot(), player.getXRot());
                    needle.shoot(velocity.x, velocity.y, velocity.z, 0.6f, 6.0f);
                    level.addFreshEntity(needle);
                }
            }
        }
    }


    public static void CactusSoulDeathHandler(LivingDeathEvent event) {
        if (!event.isCanceled() && event.getSource().getDirectEntity() instanceof NeedleProjectile needleProjectile && event.getEntity() instanceof LivingEntity livingEntity && livingEntity.level() instanceof ServerLevel level) {
            for (int i = 0; i < 80; i++) {
                NeedleProjectile needle = new NeedleProjectile(EntityRegister.Needle.get(), level);
                needle.setOwner(needleProjectile.getOwner());
                needle.setBaseDamage(2.0);
                double theta = level.random.nextDouble() * Math.PI * 2;
                double phi = Math.acos(2 * level.random.nextDouble() - 1);
                double r = 0.5 + level.random.nextDouble() * 0.3;
                Vec3 offset = new Vec3(r * Math.sin(phi) * Math.cos(theta), r * Math.sin(phi) * Math.sin(theta), r * Math.cos(phi));
                Vec3 spawnPos = livingEntity.position().add(offset);
                Vec3 velocity = offset.normalize().scale(0.8);
                needle.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, livingEntity.getYRot(), livingEntity.getXRot());
                needle.shoot(velocity.x, velocity.y, velocity.z, 0.6f, 6.0f);
                level.addFreshEntity(needle);
            }
        }
    }




}
