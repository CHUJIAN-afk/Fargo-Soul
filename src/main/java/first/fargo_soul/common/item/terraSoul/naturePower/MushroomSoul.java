package first.fargo_soul.common.item.terraSoul.naturePower;

import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.register.EffectRegister;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.ParticleUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

public class MushroomSoul extends SoulItem {

    public MushroomSoul(Properties properties) {
        super();
    }

    @Override
    public void itemUseFinish(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (event.getItem().is(Items.MUSHROOM_STEW) && CurioUtils.isEquipped(attacker, MushroomSoul.class)) {
                float healAmount = 4 + (attacker.getMaxHealth() - attacker.getHealth()) * 0.1f;
                attacker.heal(healAmount);
                attacker.addEffect(new MobEffectInstance(EffectRegister.FungalEmpowerment, 219));
            }
        }
    }

    @Override
    public void death(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, MushroomSoul.class)) {
                Level level = target.level();
                ItemStack itemStack = target.getRandom().nextBoolean() ? Items.BROWN_MUSHROOM.getDefaultInstance() : Items.RED_MUSHROOM.getDefaultInstance();
                SoulUtils.addItemEetity(level, itemStack, target.getBoundingBox().getCenter());
                ParticleUtils.spawnParticleSphere((ServerLevel) target.level(), target.getBoundingBox().getCenter(), ParticleTypes.WARPED_SPORE, 1.0f, 1, 0.5f);
            }
        }
    }

}