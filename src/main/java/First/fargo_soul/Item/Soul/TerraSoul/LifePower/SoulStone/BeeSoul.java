package First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.CustomUtils;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class BeeSoul extends SoulItem {
    public BeeSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.ORANGE));
    }


    public static void BeeSoulTickHandler1(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.BeeSoul.get())) {
            if (player.tickCount % 20 == 0) {
                List<Bee> beeList = player.serverLevel().getEntitiesOfClass(Bee.class, player.getBoundingBox().inflate(10), bee -> {
                    if (bee.getTarget() != null) {
                        return !bee.getTarget().equals(player);
                    }
                    return true;
                });
                for (Bee bee : beeList) {
                    bee.heal(1);
                }
            }
        }
    }

    public static void BeeSoulTickHandler2(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.BeeSoul.get())) {
            BlockState blockState = player.level().getBlockState(player.blockPosition());
            if (blockState.getBlock() instanceof FlowerBlock) {
                if (player.getEffect(EffectRegister.Honey) == null) {
                    player.addEffect(new MobEffectInstance(EffectRegister.Honey, 19));
                }
                if (player.tickCount % 1200 == 0) {
                    ServerLevel serverLevel = player.serverLevel();
                    int size = serverLevel.getEntitiesOfClass(Bee.class, player.getBoundingBox().inflate(6)).size();
                    if (size < 12) {
                        for (int i = 0; i < 3; i++) {
                            Bee bee = new Bee(EntityType.BEE, serverLevel);
                            double x = player.getRandomX(2);
                            double y = player.getRandomY();
                            double z = player.getRandomZ(2);
                            bee.setPos(x, y, z);
                            serverLevel.addFreshEntity(bee);
                        }
                        serverLevel.playSound(
                                null,
                                player.getX(), player.getY(), player.getZ(),
                                SoundEvents.BEE_LOOP,
                                SoundSource.NEUTRAL,
                                1.0f,
                                CustomUtils.random.nextFloat() * 0.4f + 0.4f
                        );
                    }
                }
            }
        }
    }

    public static void BeeSoulTickHandler3(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.BeeSoul.get())) {
            if (player.tickCount % 200 == 0) {
                ServerLevel serverLevel = player.serverLevel();
                int size = serverLevel.getEntitiesOfClass(Bee.class, player.getBoundingBox().inflate(6), bee -> bee.getTarget() != null).size();
                if (size < 24) {
                    List<Monster> livingEntityList = serverLevel.getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(10));
                    for (Monster monster : livingEntityList) {
                        BlockState blockState = serverLevel.getBlockState(monster.blockPosition());
                        if (blockState.getBlock() instanceof FlowerBlock) {
                            for (int i = 0; i < 3; i++) {
                                Bee bee = new Bee(EntityType.BEE, serverLevel);
                                double x = monster.getRandomX(1);
                                double y = monster.getRandomY();
                                double z = monster.getRandomZ(1);
                                bee.setPos(x, y, z);
                                bee.setTarget(monster);
                                serverLevel.addFreshEntity(bee);
                            }
                            serverLevel.playSound(
                                    null,
                                    player.getX(), player.getY(), player.getZ(),
                                    SoundEvents.BEE_LOOP,
                                    SoundSource.NEUTRAL,
                                    1.0f,
                                    CustomUtils.random.nextFloat() * 0.4f + 0.4f
                            );
                        }
                    }
                }
            }
        }
    }


    public static void BeeSoulChangeTargetHandler(LivingChangeTargetEvent event) {
        if (event.getEntity() instanceof Bee && event.getNewAboutToBeSetTarget() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.BeeSoul.get())) {
            event.setCanceled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void BeeSoulMovementInputHandler(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player && CurioUtils.isEquipped(player, SoulsRegister.BeeSoul.get())) {
            Input input = event.getInput();
            int beeFlightTime = player.getPersistentData().getInt("BeeSoul");
            if (input.jumping && beeFlightTime < 60) {
                Vec3 deltaMovement = player.getDeltaMovement();
                Vec3 newDeltaMovement = new Vec3(
                        deltaMovement.x(),
                        Math.min(deltaMovement.y() + 0.1, 0.5),
                        deltaMovement.z()
                );
                player.setDeltaMovement(newDeltaMovement);
                player.getPersistentData().putInt("BeeSoul", beeFlightTime + 1);
            } else if (player.onGround()) {
                player.getPersistentData().remove("BeeSoul");
            }
        }
    }

    public static void BeeSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.BeeSoul.get())) {
            if (event.getSource().is(DamageTypes.FALL)) {
                event.setCanceled(true);
            }
        }
    }




}
