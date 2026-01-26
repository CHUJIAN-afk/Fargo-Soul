package First.fargo_soul.client.slot;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.utils.CurioUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
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

    public void renderSlotBg(@NotNull GuiGraphics guiGraphics) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(x - 1, y - 1, 0);
        guiGraphics.blit(FargoSoul.rl("textures/slot/empty_slot.png"), 0, 0, 0, 0, 18, 18, 18, 18);
        pose.popPose();
    }

    public void renderEmptySlot(@NotNull GuiGraphics guiGraphics) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(x - 1, y - 1, 0);
        pose.scale(0.375f, 0.375f, 1);
        guiGraphics.blit(FargoSoul.rl("textures/slot/soul_slot.png"), 0, 0, 0, 0, 48, 48, 48, 48);
        pose.popPose();
    }

}
