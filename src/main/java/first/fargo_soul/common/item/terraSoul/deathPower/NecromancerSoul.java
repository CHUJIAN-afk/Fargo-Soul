package first.fargo_soul.common.item.terraSoul.deathPower;

import first.fargo_soul.common.entity.projectile.Bone;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.DeathPower;
import first.fargo_soul.register.EntityRegister;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class NecromancerSoul extends SoulItem {

    public NecromancerSoul(Properties properties) {
        super();
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, NecromancerSoul.class)) {
                Level level = target.level();
                if (target.getRandom().nextDouble() < 0.02) {
                    int size = CurioUtils.isEquipped(attacker, DeathPower.class) ? 24 : 12;
                    for (int i = 0; i < size; i++) {
                        Bone bone = new Bone(EntityRegister.BoneEntity.get(), level);
                        SoulUtils.setAbilityInvulnerable(bone);
                        SoulUtils.randomShoot(target, bone, target);
                    }
                    SoulUtils.playSound(level, target.position(), SoundEvents.SKELETON_DEATH, target.getSoundSource());
                }
            }
        }
    }

    @Override
    public void death(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, NecromancerSoul.class)) {
                if (event.getEntity() instanceof LivingEntity target) {
                    EntityType<?> type = target.getType();
                    if (type.is(EntityTypeTags.UNDEAD)) {
                        float maxHealth = target.getMaxHealth();
                        float healAmount = CurioUtils.isEquipped(attacker, DeathPower.class) ? maxHealth * 0.35f : maxHealth * 0.2f;
                        attacker.heal(healAmount);
                        ItemStack itemStack = type == EntityType.SKELETON ? Items.SKELETON_SKULL.getDefaultInstance() : type == EntityType.WITHER_SKELETON ? Items.WITHER_SKELETON_SKULL.getDefaultInstance() : ItemStack.EMPTY;
                        if (!itemStack.isEmpty()) {
                            SoulUtils.addItemEetity(attacker.level(), itemStack, target.getBoundingBox().getCenter());
                        }
                    }
                }
            }
        }
    }

}