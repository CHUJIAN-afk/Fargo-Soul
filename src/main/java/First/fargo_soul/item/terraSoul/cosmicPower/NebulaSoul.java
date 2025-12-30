package First.fargo_soul.item.terraSoul.cosmicPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.CosmicPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class NebulaSoul extends SoulItem {

    public NebulaSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.RED));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (CurioUtils.isEquipped(attacker, NebulaSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(NebulaSoul.class);
                    soulInfo.setMaxCooldown(CurioUtils.isEquipped(attacker, CosmicPower.class) ? 40 : 60);
                    if (soulInfo.isReady()) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        Level level = attacker.level();
                        if (SoulUtils.getSoulTarget(attacker, 20) instanceof LivingEntity target) {
                            float amount = (8 + target.getMaxHealth() * 0.02f) * (CurioUtils.isEquipped(attacker, CosmicPower.class) ? 1.5f : 1f);
                            SoulUtils.attack(attacker, target, DamageTypes.MAGIC, amount);
                            attacker.heal(amount);
                            ParticleUtils.spawnMovingParticleLine(
                                    (ServerLevel) level,
                                    new Vec3(target.getRandomX(256), level.getMaxBuildHeight(), target.getRandomZ(256)),
                                    target.getBoundingBox().getCenter(),
                                    ParticleTypes.DRAGON_BREATH,
                                    100,
                                    0.25f,
                                    0,
                                    5,
                                    50
                            );
                            SoulUtils.playSound(
                                    level,
                                    target.position(),
                                    SoundEvents.EVOKER_CAST_SPELL,
                                    SoundSource.PLAYERS
                            );
                        }
                    }
                }
            }
        }

    }

}
