package first.fargo_soul.common.item.terraSoul.lifePower;

import first.fargo_soul.common.event.modEvent.PlayerFlyEvent;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.LifePower;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class BeeSoul extends SoulItem {

    public BeeSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, BeeSoul.class)) {
                if (ticker.tickCount % (CurioUtils.isEquipped(ticker, LifePower.class) ? 20 : 40) == 0) {
                    Level level = ticker.level();
                    List<Bee> beeList = level.getEntitiesOfClass(Bee.class, ticker.getBoundingBox().inflate(5));
                    for (Bee bee : beeList) {
                        bee.heal(1);
                        SoulUtils.playSound(
                                level,
                                bee.position(),
                                SoundEvents.BEE_LOOP,
                                SoundSource.PLAYERS
                        );
                    }
                    BlockState blockState = level.getBlockState(ticker.blockPosition());
                    if (blockState.getBlock() instanceof FlowerBlock) {
                        ticker.heal(1);
                    }
                }
            }
        }
    }

    @Override
    public void targetChange(LivingChangeTargetEvent event) {
        if (event.getEntity() instanceof Bee && event.getNewAboutToBeSetTarget() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, BeeSoul.class)) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (event.getSource().is(DamageTypes.FALL) && CurioUtils.isEquipped(target, BeeSoul.class)) {
                event.setCanceled(true);
            }
            if (target instanceof Bee && event.getSource().getEntity() instanceof LivingEntity attacker && CurioUtils.isEquipped(attacker, BeeSoul.class)) {
                event.setCanceled(true);
            }
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
            if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, BeeSoul.class) && !CurioUtils.isEquipped(target, BeeSoul.class)) {
                Level level = attacker.level();
                List<Bee> beeList = level.getEntitiesOfClass(Bee.class, attacker.getBoundingBox().inflate(5));
                for (Bee bee : beeList) {
                    bee.setTarget(target);
                }
            }
        }
    }

    @Override
    public void fly(PlayerFlyEvent event) {
        event.setAllowingFly(true);
    }
}
