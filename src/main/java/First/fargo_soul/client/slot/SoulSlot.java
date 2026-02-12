package First.fargo_soul.client.slot;

import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class SoulSlot extends Slot {

    private final Player player;

    public SoulSlot(Container container, int slot, int x, int y, Player player) {
        super(container, slot, x, y);
        this.player = player;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        if (stack.getItem() instanceof SoulItem soulItem) {
            List<SoulItem> soulFromList = CurioUtils.getSoulFromList(CurioUtils.getSoulFromSlots(player));
            List<SoulItem> targetList = CurioUtils.getSoulFromSoul(soulItem);
            return Collections.disjoint(soulFromList, targetList);
        }
        return false;
    }

}
