package First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import static First.fargo_soul.Item.Soul.SoulsRegister.ShadowWoodSoul;

public class ShadowWoodSoul extends SoulItem {

    public ShadowWoodSoul(Item.Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }


    public static void ShadowWoodSoulTickHandler(PlayerTickEvent.Post event){
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, ShadowWoodSoul.get()) && player.tickCount % 60 == 0){
            ParticleUtils.spawnExpandingParticleCircle(
                    player.serverLevel(),
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    ParticleTypes.DRAGON_BREATH,
                    4,
                    40,
                    0.0f,
                    0,
                    10,
                    20
            );
        }
    }

    public static void ShadowWoodSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, ShadowWoodSoul.get()) && event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            float distance = player.distanceTo(livingEntity);
            if (distance <= 4) {
                player.heal(event.getAmount() * 0.1f);
            }
        }
    }
}
