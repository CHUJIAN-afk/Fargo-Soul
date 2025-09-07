package First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import static First.fargo_soul.Item.Soul.SoulsRegister.EbonyWoodSoul;

public class EbonyWoodSoul extends SoulItem {

    public EbonyWoodSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }


    public static void EbonyWoodDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, EbonyWoodSoul.get())) {
            int size = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(10)).size();
            event.setAmount(event.getAmount() + Math.min(size / 2, 5));
        }
    }

    public static void EbonyWoodDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, EbonyWoodSoul.get())) {
            int size = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(10)).size();
            event.setAmount(event.getAmount() * ((100 - (float) Math.min(size / 2, 5)) / 100));
        }
    }

    public static void EbonyWoodTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, EbonyWoodSoul.get()) && player.tickCount % 60 == 0) {
            ParticleUtils.spawnExpandingParticleCircle(
                    player.serverLevel(),
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    ParticleTypes.SOUL,
                    10,
                    10,
                    0.0f,
                    0,
                    100,
                    20
            );
        }
    }
}
