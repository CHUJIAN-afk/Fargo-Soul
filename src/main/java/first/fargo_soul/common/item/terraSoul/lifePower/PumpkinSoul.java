package first.fargo_soul.common.item.terraSoul.lifePower;

import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import first.lyra.common.attachment.InvincibleData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PumpkinSoul extends SoulItem {

    public PumpkinSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void hurt(@Nullable Entity attacker, @NotNull Player target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (target.getItemBySlot(EquipmentSlot.HEAD).is(Items.CARVED_PUMPKIN)) {
            modifiers.add(new ValueModifier(-0.15f, ValueOperation.ADD_MULTIPLIED_TOTAL));
        }
    }

    @Override
    public void tick(Player player) {
        BlockPos below = player.blockPosition().below();
        BlockState state = player.level().getBlockState(below);
        if (state.is(Blocks.PUMPKIN) || state.is(Blocks.CARVED_PUMPKIN)) {
            player.level().destroyBlock(below, false);
            player.heal(player.getMaxHealth() * 0.05f);
            List<LivingEntity> enemies = SoulTargetCache.get(player).getEntitiesInRadius(player.getBoundingBox().getCenter(), 3, null);
            for (LivingEntity enemy : enemies) {
                InvincibleData.attack(enemy)
                        .attacker(player.getUUID())
                        .damageSource(player.damageSources().playerAttack(player))
                        .damageAmount(8)
                        .invincibleTime(5)
                        .apply();
            }
        }
    }
}