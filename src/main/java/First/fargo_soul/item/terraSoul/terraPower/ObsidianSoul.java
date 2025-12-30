package First.fargo_soul.item.terraSoul.terraPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.TerraPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;


public class ObsidianSoul extends SoulItem {

    public ObsidianSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.ORANGE));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                int amount = 8 - attacker.getArmorValue();
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.ARMOR,
                        ItemRegister.ObsidianSoulItem.getId(),
                        amount,
                        AttributeModifier.Operation.ADD_VALUE,
                        CurioUtils.isEquipped(attacker, ObsidianSoul.class) && amount > 0
                );
                if (CurioUtils.isEquipped(attacker, ObsidianSoul.class)) {
                    SoulAbilityData.SoulInfo SoulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(ObsidianSoul.class);
                    SoulInfo.setMaxCooldown(CurioUtils.isEquipped(attacker, TerraPower.class) ? 30 : 60);
                    if (SoulInfo.getCooldown() == 0 && SoulUtils.getSoulTarget(attacker, 10) instanceof LivingEntity target) {
                        SoulInfo.setCooldown(SoulInfo.getMaxCooldown());
                        Level level = attacker.level();
                        SmallFireball fireball = new SmallFireball(EntityType.SMALL_FIREBALL, level);
                        SoulUtils.shootTargetFromAttaker(fireball, attacker, target, 1, CurioUtils.isEquipped(attacker, TerraPower.class) ? 1.3 : 1.0);
                        SoulUtils.setAbilityInvulnerable(fireball);
                        ParticleUtils.spawnParticleSphere(
                                (ServerLevel) level,
                                fireball.position(),
                                ParticleTypes.LAVA,
                                0.2f,
                                5,
                                0.2f
                        );
                        SoulUtils.playSound(
                                level,
                                fireball.position(),
                                SoundEvents.DRAGON_FIREBALL_EXPLODE,
                                SoundSource.PLAYERS
                        );
                    }
                }
            }
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void RenderFog(ViewportEvent.RenderFog event) {
            Camera camera = event.getCamera();
            Minecraft minecraft = Minecraft.getInstance();
            BlockPos blockPos = camera.getBlockPosition();
            if (minecraft.level instanceof Level level && minecraft.player instanceof LocalPlayer player) {
                if (CurioUtils.isEquipped(player, ObsidianSoul.class)) {
                    FluidState fluidState = level.getFluidState(blockPos);
                    if (camera.getPosition().y < blockPos.getY() + fluidState.getHeight(level, blockPos)) {
                        Fluid fluid = fluidState.getType();
                        Entity entity = camera.getEntity();
                        if (!entity.isSpectator() && fluid.equals(Fluids.FLOWING_LAVA)) {
                            event.setNearPlaneDistance(-4.0f);
                            event.setFarPlaneDistance(20.0f);
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if ((event.getSource().is(DamageTypes.LAVA) || event.getSource().is(DamageTypeTags.IS_FIRE)) && CurioUtils.isEquipped(target, ObsidianSoul.class)) {
                    event.setCanceled(true);
                }
            }
        }

    }

}