package First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.CustomUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.CopperSoul;

public class CopperSoul extends SoulItem {

    public CopperSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.ORANGE));
    }


    public static void CopperSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, CopperSoul.get()) && event.getEntity() instanceof LivingEntity livingEntity) {
            long serverTickCount = player.serverLevel().getGameTime();
            long LastCopperSoul = player.getPersistentData().getLong("LastCopperSoul");
            double random = 0.1;
            if ((player.level().canSeeSky(livingEntity.blockPosition()) && player.level().isRaining() || livingEntity.isInWater())) {
                random = 0.2;
            }
            if (serverTickCount > LastCopperSoul && CustomUtils.random.nextDouble() < random) {
                player.getPersistentData().putLong("LastCopperSoul", serverTickCount + 100);
                livingEntity.hurt(player.damageSources().lightningBolt(), event.getAmount() * 0.75f);
                livingEntity.invulnerableTime = 0;
                ParticleUtils.spawnParticleLine(
                        player.serverLevel(),
                        player.getEyePosition().add(0, 0.5, 0),
                        livingEntity.getEyePosition(),
                        ParticleTypes.ELECTRIC_SPARK,
                        100,
                        0.0f
                );
                List<LivingEntity> livingEntityList = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(5));
                livingEntityList.removeIf(livingEntity1 -> livingEntity1.equals(player) || livingEntity1.equals(livingEntity));
                if (livingEntityList.isEmpty()) return;
                for (int i = 0; i < Math.min(livingEntityList.size(), 5); i++) {
                    LivingEntity livingEntity1 = livingEntityList.get(i);
                    livingEntity1.hurt(player.damageSources().lightningBolt(), event.getAmount() * 0.25f);
                    ParticleUtils.spawnParticleLine(
                            player.serverLevel(),
                            livingEntity.getEyePosition().add(0, 0.5, 0),
                            livingEntity1.getEyePosition(),
                            ParticleTypes.ELECTRIC_SPARK,
                            50,
                            0.1f
                    );
                }
            }
        }
    }


}
