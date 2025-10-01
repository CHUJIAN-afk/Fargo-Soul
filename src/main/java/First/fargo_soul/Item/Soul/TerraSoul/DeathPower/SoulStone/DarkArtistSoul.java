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
import net.minecraft.world.phys.Vec3;
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
                if (SoulUtils.isEquipped(attacker, DarkArtistSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(DarkArtistSoul.class);
                    Level level = attacker.level();
                    //箭矢
                    soulInfo.maxCooldown = 200;
                    if (soulInfo.cooldown == 0) {
                        soulInfo.cooldown = soulInfo.maxCooldown;
                        for (int i = 0; i < 8; i++) {
                            Arrow arrow = new Arrow(EntityType.ARROW, level);
                            Vec3 Pos = new Vec3(target.getRandomX(4), target.getRandomY() + 2, target.getRandomZ(4));
                            Vec3 vec3 = target.getHitbox().getCenter().subtract(Pos).normalize();
                            arrow.setPos(Pos);
                            arrow.setBaseDamage(event.getAmount() * (SoulUtils.isEquipped(attacker, DeathPower.class) ? 0.25 : 0.15f));
                            arrow.shoot(vec3.x, vec3.y, vec3.z, 1F, 1.0F);
                            level.addFreshEntity(arrow);
                            arrow.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SoulItem.class).enabled = true;
                            SoulUtils.playSound(
                                    level,
                                    Pos,
                                    SoundEvents.ARROW_SHOOT,
                                    SoundSource.PLAYERS
                            );
                        }
                    }
                    //火球
                    double chance = SoulUtils.isEquipped(attacker, DeathPower.class) ? 0.2 : 0.1;
                    if (target.getRandom().nextDouble() < chance) {
                        double x = attacker.getRandomX(2);
                        double y = attacker.getRandomY() + 2;
                        double z = attacker.getRandomZ(2);
                        Vec3 toMonster = target.getHitbox().getCenter().subtract(x, y, z).normalize();
                        SmallFireball fireball = new SmallFireball(
                                level,
                                x,
                                y,
                                z,
                                toMonster
                        );
                        fireball.setOwner(attacker);
                        level.addFreshEntity(fireball);
                        fireball.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SoulItem.class).enabled = true;
                        ParticleUtils.spawnParticleSphere(
								(ServerLevel) level,
                                x,
                                y,
                                z,
                                ParticleTypes.LAVA,
                                0.2f,
                                5,
                                0.2f
                        );
                    }
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(target, DarkArtistSoul.class)) {
                    Holder<MobEffect> blindness = MobEffects.BLINDNESS;
                    Holder<MobEffect> darkness = MobEffects.DARKNESS;
                    attacker.addEffect(new MobEffectInstance(blindness, 200));
                    attacker.addEffect(new MobEffectInstance(darkness, 200));
                }
            }
        }
    }

}

