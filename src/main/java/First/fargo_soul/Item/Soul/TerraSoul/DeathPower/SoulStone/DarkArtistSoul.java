package First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.DeathPower;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

public class DarkArtistSoul extends SoulItem {

    public DarkArtistSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void CurioChangeEvent(CurioChangeEvent event) {
            SoulAbilityData.updateMaxCooldown(event.getEntity(), DarkArtistSoul.class, 200);
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && SoulUtils.isEquipped(attacker, DarkArtistSoul.class)) {
                    Level level = attacker.level();
                    //箭矢
                    SoulAbilityData.SoulInfo DarkArtistArrow = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("DarkArtistArrow");
                    DarkArtistArrow.maxCooldown = 200;
                    if (DarkArtistArrow.cooldown == 0) {
                        DarkArtistArrow.cooldown = DarkArtistArrow.maxCooldown;
                        for (int i = 0; i < 8; i++) {
                            Arrow arrow = new Arrow(EntityType.ARROW, level);
                            arrow.setBaseDamage(event.getAmount() * (SoulUtils.isEquipped(attacker, DeathPower.class) ? 0.25 : 0.15f));
                            SoulUtils.shootTargetFromAttaker(arrow, attacker, target, 2, 3);
                            SoulUtils.setAbilityInvulnerable(arrow);
                            SoulUtils.playSound(
                                    level,
                                    arrow.position(),
                                    SoundEvents.ARROW_SHOOT,
                                    SoundSource.PLAYERS
                            );
                        }
                    }
                    //火球
                    SoulAbilityData.SoulInfo DarkArtistFireball = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo("DarkArtistFireball");
                    DarkArtistFireball.maxCooldown = 2;
                    double chance = SoulUtils.isEquipped(attacker, DeathPower.class) ? 0.2 : 0.1;
                    if (DarkArtistFireball.cooldown == 0 && target.getRandom().nextDouble() < chance) {
                        DarkArtistFireball.cooldown = DarkArtistFireball.maxCooldown;
                        SmallFireball fireball = new SmallFireball(EntityType.SMALL_FIREBALL, level);
                        SoulUtils.shootTargetFromAttaker(fireball, attacker, target);
                        SoulUtils.setAbilityInvulnerable(fireball);
                        ParticleUtils.spawnParticleSphere(
                                (ServerLevel) level,
                                fireball.position(),
                                ParticleTypes.LAVA,
                                0.2f,
                                5,
                                0.2f
                        );
                    }
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && SoulUtils.isEquipped(target, DarkArtistSoul.class)) {
                    Holder<MobEffect> blindness = MobEffects.BLINDNESS;
                    Holder<MobEffect> darkness = MobEffects.DARKNESS;
                    attacker.addEffect(new MobEffectInstance(blindness, 200));
                    attacker.addEffect(new MobEffectInstance(darkness, 200));
                }
            }
        }
    }

}

