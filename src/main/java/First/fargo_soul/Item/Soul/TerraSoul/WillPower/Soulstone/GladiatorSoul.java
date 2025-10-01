package First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.WillPower.WillPower;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class GladiatorSoul extends SoulItem {

    public GladiatorSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, GladiatorSoul.class)) {
                    Level level = attacker.level();
                    List<LivingEntity> livingEntityList = level.getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox().inflate(4), livingEntity -> {
                        if (attacker instanceof Player) {
                            return livingEntity instanceof Enemy;
                        } else {
                            return livingEntity instanceof Player || (livingEntity instanceof Mob mob && attacker.equals(mob.getTarget()));
                        }
                    });
                    if (livingEntityList.size() >= 3) {
                        event.setAmount(event.getAmount() * 1.1f);
                    }
                }
            }
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (SoulUtils.isEquipped(target, GladiatorSoul.class)) {
                    Level level = target.level();
                    List<LivingEntity> livingEntityList = level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(4), livingEntity -> {
                        if (target instanceof Player) {
                            return livingEntity instanceof Enemy;
                        } else {
                            return livingEntity instanceof Mob mob && target.equals(mob.getTarget());
                        }
                    });
                    if (livingEntityList.size() >= 3) {
                        event.setAmount(event.getAmount() * 0.9f);
                    }
                }
            }

            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, GladiatorSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GladiatorSoul.class);
                    soulInfo.maxStacks = SoulUtils.isEquipped(attacker, WillPower.class) ? 12 : 24;
                    soulInfo.addStacks();
                    if (soulInfo.stacks == soulInfo.maxStacks) {
                        soulInfo.removeStacks();
                        Level level = attacker.level();
                        List<LivingEntity> livingEntityList = level.getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox().inflate(4), livingEntity -> {
                            if (attacker instanceof Player) {
                                return livingEntity instanceof Enemy;
                            } else {
                                return livingEntity instanceof Mob mob && attacker.equals(mob.getTarget());
                            }
                        });
                        for (int i = 0; i < 16; i++) {
                            Arrow arrow = new Arrow(EntityType.ARROW, level);
                            Vec3 Pos = new Vec3(target.getRandomX(4), target.getRandomY() + 10, target.getRandomZ(4));
                            Vec3 vec3 = target.getHitbox().getCenter().subtract(Pos).normalize();
                            arrow.setPos(Pos);
                            arrow.setBaseDamage(arrow.getBaseDamage() * (livingEntityList.size() < 3 ? 1.8 : 1.0));
                            arrow.shoot(vec3.x, vec3.y, vec3.z, SoulUtils.random.nextFloat(0.8f, 1.6f), 0.5F);
                            arrow.setOwner(attacker);
                            level.addFreshEntity(arrow);
                            SoulAbilityData.SoulInfo info = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(SoulItem.class);
                            info.enabled = true;
                        }
                        SoulUtils.playSound(
                                level,
                                target.position(),
                                SoundEvents.ARROW_SHOOT,
                                SoundSource.PLAYERS
                        );
                    }
                }
            }
        }

    }

}
