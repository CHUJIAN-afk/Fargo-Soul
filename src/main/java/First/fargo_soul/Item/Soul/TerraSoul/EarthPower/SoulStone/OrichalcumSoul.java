package First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.EarthPower.EarthPower;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;


public class OrichalcumSoul extends SoulItem {

    public OrichalcumSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Incoming(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (event.getSource().is(Tags.DamageTypes.IS_POISON) && target.getEffect(EffectRegister.OrichalcumPoisoning) != null) {
                    event.setAmount(event.getAmount() * 3.5f);
                }
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                double chance = SoulUtils.isEquipped(attacker, EarthPower.class) ? 0.4 : 0.2;
                if (!attacker.equals(target) && SoulUtils.isEquipped(attacker, OrichalcumSoul.class) && target.getRandom().nextDouble() < chance) {
                    Level level = attacker.level();
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(OrichalcumSoul.class);
                    soulInfo.maxCooldown = 2;
                    if (soulInfo.cooldown == 0) {
                        soulInfo.cooldown = soulInfo.maxCooldown;
                        float amount = 1 + (event.getAmount() * 0.05f);
                        int amplifier = SoulUtils.isEquipped(attacker, EarthPower.class) ? 1 : 0;
                        SoulUtils.attack(attacker, target, DamageTypes.MAGIC, amount);
                        target.addEffect(new MobEffectInstance(EffectRegister.OrichalcumPoisoning, 99, amplifier));
                        double size = attacker.getBoundingBox().getSize();
                        Vec3 start = new Vec3(attacker.getRandomX(size), attacker.getRandomY() + size, attacker.getRandomZ(size));
                        ParticleUtils.spawnMovingParticleLine(
                                (ServerLevel) level,
                                start,
                                target.getBoundingBox().getCenter(),
                                ParticleTypes.CHERRY_LEAVES,
                                2,
                                0.5f,
                                0.005,
                                10,
                                50
                        );
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Post2(MobEffectEvent.Applicable event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, OrichalcumSoul.class) && event.getEffectInstance().is(EffectRegister.OrichalcumPoisoning)) {
                    event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
                }
            }
        }

    }

}
