package First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import static First.fargo_soul.Utils.CustomUtils.random;

public class TitaniumSoul extends SoulItem {

    public TitaniumSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }


    public static void TitaniumSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.TitaniumSoul.get())) {
            long TitaniumSoul = player.getPersistentData().getLong("TitaniumSoul");
            long GameTime = player.serverLevel().getGameTime();
            float chance = (1 - (player.getHealth() / player.getMaxHealth())) * 0.15f;
            if (TitaniumSoul < (GameTime - 600)) {
                event.setCanceled(true);
                player.serverLevel().playSound(
                        null,
                        player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ANVIL_PLACE,
                        SoundSource.PLAYERS,
                        1.0f,
                        random.nextFloat() * 0.4f + 0.4f
                );
            } else if (random.nextFloat() < chance) {
                event.setCanceled(true);
            }
            player.getPersistentData().putLong("TitaniumSoul", GameTime);
        }
    }

    public static void TitaniumSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.TitaniumSoul.get())) {
            long TitaniumSoul = player.getPersistentData().getLong("TitaniumSoul");
            long GameTime = player.serverLevel().getGameTime();
            float chance = (1 - (player.getHealth() / player.getMaxHealth()));
            if (TitaniumSoul > (GameTime - 600) && random.nextFloat() < chance) {
                ParticleUtils.spawnParticleLine(
                        player.serverLevel(),
                        player.getEyePosition().add((1 - random.nextDouble(2)), (1 - random.nextDouble(2)), (1 - random.nextDouble(2))),
                        player.position().add((1 - random.nextDouble(2)), (1 - random.nextDouble(2)), (1 - random.nextDouble(2))),
                        ParticleTypes.CLOUD,
                        1,
                        0.2f
                );
            }
        }
    }

}

