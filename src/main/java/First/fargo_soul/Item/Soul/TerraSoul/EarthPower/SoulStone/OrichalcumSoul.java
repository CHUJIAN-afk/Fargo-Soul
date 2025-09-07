package First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.CustomUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.Random;

public class OrichalcumSoul extends SoulItem {

    public OrichalcumSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }


    public static void OrichalcumSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.OrichalcumSoul.get()) && event.getEntity() instanceof LivingEntity livingEntity) {
            if (event.getSource().is(Tags.DamageTypes.IS_POISON) && livingEntity.getEffect(EffectRegister.OrichalcumPoisoning) != null) {
                event.setAmount(event.getAmount() * 3.5f);
            }
            livingEntity.hurt(player.damageSources().magic(), event.getAmount() * 0.05f);
            livingEntity.invulnerableTime = 0;
            livingEntity.addEffect(new MobEffectInstance(EffectRegister.OrichalcumPoisoning, 100));
            Random random = CustomUtils.random;
            ParticleUtils.spawnMovingParticleLine(
                    player.serverLevel(),
                    livingEntity.getBoundingBox().getCenter().add((2 - random.nextDouble(4)), (1 - random.nextDouble(2)), (2 - random.nextDouble(4))),
                    livingEntity.getBoundingBox().getCenter(),
                    ParticleTypes.CHERRY_LEAVES,
                    10,
                    0.0f,
                    0,
                    5,
                    50
            );
        }
    }

}
