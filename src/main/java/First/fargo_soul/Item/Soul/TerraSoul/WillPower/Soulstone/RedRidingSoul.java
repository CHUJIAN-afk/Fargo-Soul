package First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import static First.fargo_soul.Utils.CustomUtils.random;

public class RedRidingSoul extends SoulItem {

    public RedRidingSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }


    public static void RedRidingSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.RedRidingSoul.get())) {
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
