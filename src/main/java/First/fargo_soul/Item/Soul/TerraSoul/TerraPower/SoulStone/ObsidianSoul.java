package First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.TerraPower.TerraPower;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.SoulUtils;
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

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                int amount = 8 - attacker.getArmorValue();
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.ARMOR,
                        SoulsRegister.ObsidianSoul.getId(),
                        amount,
                        AttributeModifier.Operation.ADD_VALUE,
                        SoulUtils.isEquipped(attacker, ObsidianSoul.class) && amount > 0
                );
                if (SoulUtils.isEquipped(attacker, ObsidianSoul.class)) {
                    SoulAbilityData.SoulInfo SoulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(ObsidianSoul.class);
                    SoulInfo.maxCooldown  = SoulUtils.isEquipped(attacker, TerraPower.class) ? 30 : 60;
                    if (SoulInfo.cooldown == 0 && SoulUtils.getSoulTarget(attacker, 10) instanceof LivingEntity target) {
                        SoulInfo.cooldown = SoulInfo.maxCooldown;
                        Level level = attacker.level();
                        SmallFireball fireball = new SmallFireball(EntityType.SMALL_FIREBALL, level);
                        SoulUtils.shootTargetFromAttaker(fireball, attacker, target, 1, SoulUtils.isEquipped(attacker, TerraPower.class) ? 1.3 : 1.0);
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
                if (SoulUtils.isEquipped(player, ObsidianSoul.class)) {
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
                if ((event.getSource().is(DamageTypes.LAVA) || event.getSource().is(DamageTypeTags.IS_FIRE)) && SoulUtils.isEquipped(target, ObsidianSoul.class)) {
                    event.setCanceled(true);
                }
            }
        }

    }

}