package First.fargo_soul.Compact.Create.CreatePower.SoulStone;

import First.fargo_soul.Compact.Create.CreatePower.CreateSoulItem;
import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.mixin.create.Accessor.BlazeBurnerBlockEntityAccessor;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class BurnerSoul extends CreateSoulItem {

    public BurnerSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    public static void BurnerSoulRightClickBlockHandler(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity() instanceof Player player && CurioUtils.isEquipped(player, CreateSoulsRegister.Burner_Soul.get())) {
            BlockPos blockPos = event.getPos();
            Level level = player.level();
            if (level.getBlockEntity(blockPos) instanceof BlazeBurnerBlockEntity burner && !burner.getActiveFuel().equals(BlazeBurnerBlockEntity.FuelType.SPECIAL)) {
                if (level.isClientSide()) {
                    player.swing(event.getHand());
                }
                level.playSound(null, burner.getBlockPos(), SoundEvents.BLAZE_SHOOT, SoundSource.BLOCKS, .125f + level.random.nextFloat() * .125f, .75f - level.random.nextFloat() * .25f);
                ((BlazeBurnerBlockEntityAccessor) burner).setActiveFuel(BlazeBurnerBlockEntity.FuelType.SPECIAL);
                ((BlazeBurnerBlockEntityAccessor) burner).setRemainingBurnTime(Math.min(burner.getRemainingBurnTime() + 100, 100));
                burner.updateBlockState();
            }
        }
    }

}
