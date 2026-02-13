package First.fargo_soul.client.tooltip;

import First.fargo_soul.common.item.base.SoulItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
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
		this.renderList = new ArrayList<>(soulItem.getSoulItemList(soulItem));
		this.width = (renderList.size() + 1) * 16;
	}

	@Override
	public int getWidth(@NotNull Font font) {
		return 0;
	}

	@Override
	public int getHeight() {
		return -2;
	}

	@Override
	public void renderImage(@NotNull Font font, int tooltipX, int tooltipY, @NotNull GuiGraphics guiGraphics) {
		PoseStack pose = guiGraphics.pose();
		pose.pushPose();
		tooltipY -= 25;
		TooltipRenderUtil.renderTooltipBackground(
				guiGraphics,
				tooltipX,
				tooltipY,
				width,
				16,
				16,
				-267386864,
				-267386864,
				soulItem.getSoulRarity().getARGB(),
				soulItem.getSoulRarity().getInverseARGB()
		);
		pose.translate(tooltipX, tooltipY, 0);
		guiGraphics.renderItem(soulItem.getDefaultInstance(), 0, 0);
		for (Item item : renderList) {
			guiGraphics.renderItem(item.getDefaultInstance(), (renderList.indexOf(item) + 1) * 16, 0);
		}
		pose.popPose();
	}

}