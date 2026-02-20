package First.fargo_soul.common.item.terraSoul;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.event.modEvent.PlayerFlyEvent;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class LifePower extends SoulItem {

    public LifePower(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                BeeSoulItem.get(),
                BeetleSoulItem.get(),
                PumpkinSoulItem.get(),
                SpiderSoulItem.get(),
                TurtleSoulItem.get()
        );
    }

    @Override
    public void fly(PlayerFlyEvent event) {
        if (CurioUtils.isEquipped(event.getEntity(), LifePower.class)) {
            event.setAllowingFly(true);
            event.addMaxFlyTime(400);
        }
    }

    @Override
    public void tick(LivingEntity ticker) {
        Level level = ticker.level();
        if (!level.isClientSide()) {
            if (CurioUtils.isEquipped(ticker, LifePower.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, LifePower.class);
                if (ticker.tickCount % 20 == 0 && soulInfo.getDuration() > 0) {
                    if (ticker.getHealth() < ticker.getMaxHealth()) {
                        ticker.heal(5);
                    }
                }
                if (level.getBlockState(ticker.blockPosition()).getBlock() instanceof FlowerBlock) {
                    soulInfo.setDuration(200);
                    level.destroyBlock(ticker.blockPosition(), false);
                    RandomSource random = ticker.getRandom();
                    for (int i = 0; i < 7; i++) {
                        Bee bee = new Bee(EntityType.BEE, level);
                        Vec3 position = ticker.getBoundingBox().getCenter().add(random.nextFloat() - 0.5F, random.nextFloat() - 0.5F, random.nextFloat() - 0.5F);
                        bee.setPos(position);
                        SoulUtils.addEntity(level, bee);
                    }
                }
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide() && CurioUtils.isEquipped(target, LifePower.class)) {
            if (event.getSource().getDirectEntity() instanceof LivingEntity attacker && target != attacker && !CurioUtils.isEquipped(attacker, LifePower.class)) {
                SoulUtils.attack(LifePower.class, attacker, attacker, target, DamageTypes.CACTUS, event.getAmount() * 5);
            }
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(target, LifePower.class);
            if (soulInfo.getDuration() > 0) {
                event.setAmount(event.getAmount() * 0.8F);
            }
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, LifePower.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(attacker, LifePower.class);
                if (soulInfo.getDuration() > 0) {
                    event.setAmount(event.getAmount() * 1.3F);
                }
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, LifePower.class, SoulRenderType.Duration);
    }

}
