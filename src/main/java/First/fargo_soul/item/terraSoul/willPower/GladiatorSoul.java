package First.fargo_soul.item.terraSoul.willPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.WillPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class GladiatorSoul extends SoulItem {

    public GladiatorSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, GladiatorSoul.class)) {
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
            if (CurioUtils.isEquipped(target, GladiatorSoul.class)) {
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
            if (CurioUtils.isEquipped(attacker, GladiatorSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GladiatorSoul.class);
                soulInfo.setMaxStacks(CurioUtils.isEquipped(attacker, WillPower.class) ? 12 : 24);
                soulInfo.addStacks();
                soulInfo.setMaxCooldown(20);
                if (soulInfo.getCooldown() == 0 && soulInfo.getStacks() == soulInfo.getMaxStacks()) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
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
                        arrow.setBaseDamage(arrow.getBaseDamage() * (livingEntityList.size() < 3 ? 1.8 : 1.0));
                        SoulUtils.shootTargetFromAttaker(
                                arrow,
                                attacker,
                                target,
                                1,
                                SoulUtils.random.nextFloat(0.8f, 1.6f)
                        );
                        SoulUtils.setAbilityInvulnerable(arrow);
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

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, GladiatorSoul.class, SoulRenderType.Cooldown);
        soulRenderManager.add(this, GladiatorSoul.class, SoulRenderType.Stack);
    }

}
