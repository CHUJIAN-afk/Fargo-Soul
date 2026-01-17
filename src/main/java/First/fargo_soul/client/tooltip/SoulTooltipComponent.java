package First.fargo_soul.client.tooltip;

import First.fargo_soul.item.base.SoulItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SoulTooltipComponent implements ClientTooltipComponent, TooltipComponent {

	private final SoulItem soulItem;
	private final List<Item> renderList;
	private final int width;

	public SoulTooltipComponent(@NotNull SoulItem soulItem) {
		this.soulItem = soulItem;
		this.renderList = new ArrayList<>(soulItem.getSoulItemList());
		this.width = (renderList.size() + 1) * 16;
	}

	@Override
	public int getWidth(@NotNull Font font) {
		return width;
	}

	@Override
	public int getHeight() {
		return 16;
	}

	@Override
	public void renderImage(@NotNull Font font, int tooltipX, int tooltipY, @NotNull GuiGraphics guiGraphics) {
		PoseStack pose = guiGraphics.pose();
		pose.pushPose();
		pose.translate(tooltipX, tooltipY, 0);
		guiGraphics.renderItem(soulItem.getDefaultInstance(), 0, 0);
		for (Item item : renderList) {
			guiGraphics.renderItem(item.getDefaultInstance(), (renderList.indexOf(item) + 1) * 16, 0);
		}
		pose.popPose();
	}

}