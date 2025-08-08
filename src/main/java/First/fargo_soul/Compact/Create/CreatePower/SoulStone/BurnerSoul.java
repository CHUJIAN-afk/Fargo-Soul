package First.fargo_soul.Compact.Create.CreatePower.SoulStone;

import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.mixin.create.Accessor.BlazeBurnerBlockEntityAccessor;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

public class BurnerSoul extends SoulItem {

    public BurnerSoul(Properties properties) {
        super(properties);
    }

    public final List<Component> AttributeList = List.of(
            Component.literal("抚摸烈焰人可以让它短暂进入超级加热状态").withStyle(ChatFormatting.BLUE),
            Component.literal("超级燃烧状态结束后会进入一段时间普通燃烧状态").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.literal("“超频!”").withStyle(ChatFormatting.DARK_GRAY)
    );


    @Override
    public List<Component> getAttributeList() {
        return AttributeList;
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

    public static void BurnerSoulRightClickBlockHandler(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity() instanceof Player player && CurioUtils.isEquipped(player, CreateSoulsRegister.Burner_Soul.get())) {
            BlockPos blockPos = event.getPos();
            Level level = player.level();
            if (level.getBlockEntity(blockPos) instanceof BlazeBurnerBlockEntity burner && !burner.getActiveFuel().equals(BlazeBurnerBlockEntity.FuelType.SPECIAL)) {
                if (level.isClientSide()) {
                    player.swing(event.getHand());
                }
                level.playSound(null, burner.getBlockPos(), SoundEvents.BLAZE_SHOOT, SoundSource.BLOCKS,
                        .125f + level.random.nextFloat() * .125f, .75f - level.random.nextFloat() * .25f);
                ((BlazeBurnerBlockEntityAccessor) burner).setActiveFuel(BlazeBurnerBlockEntity.FuelType.SPECIAL);
                ((BlazeBurnerBlockEntityAccessor) burner).setRemainingBurnTime(Math.min(burner.getRemainingBurnTime() + 100, 100));
                burner.updateBlockState();
            }
        }
    }


}
