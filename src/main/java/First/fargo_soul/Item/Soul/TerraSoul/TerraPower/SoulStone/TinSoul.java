package First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.CustomUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class TinSoul extends SoulItem {

    public TinSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.BLUE));
    }


    public static void TinSoulLivingDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.TinSoul.get())) {
            player.setOnGround(true);
            double TinSoul = player.getPersistentData().getDouble("TinSoul");
            TinSoul = Math.max(TinSoul, 0.1);
            TinSoul = Math.min(TinSoul, 0.6);
            if (CustomUtils.random.nextDouble() < TinSoul && event.getEntity() instanceof LivingEntity livingEntity) {
                player.getPersistentData().putDouble("TinSoul", TinSoul + 0.1);
                event.setAmount(event.getAmount() * 2.0f);
                ParticleUtils.spawnParticleSphere(
                        player.serverLevel(),
                        livingEntity.getBoundingBox().getCenter(),
                        ParticleTypes.CRIT,
                        0.5f,
                        8,
                        0.5f,
                        0.01f
                );
            }
        }
    }

    public static void TinSoulLivingDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.TinSoul.get())) {
            double TinSoul = player.getPersistentData().getDouble("TinSoul");
            TinSoul = Math.max(TinSoul, 0.1);
            TinSoul = Math.min(TinSoul, 0.6);
            player.getPersistentData().putDouble("TinSoul", TinSoul);
        }
    }

}

