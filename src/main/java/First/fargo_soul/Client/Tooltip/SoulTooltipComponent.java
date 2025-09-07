package First.fargo_soul.Client.Tooltip;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public record SoulTooltipComponent(int width, int height,
								   SoulItem soulItem) implements ClientTooltipComponent, TooltipComponent {

	@Override
	public int getWidth(@NotNull Font font) {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void renderImage(@NotNull Font font, int tooltipX, int tooltipY, @NotNull GuiGraphics guiGraphics) {
		PoseStack pose = guiGraphics.pose();
		pose.pushPose();
		pose.translate(tooltipX, tooltipY, 0);
		List<SoulItem> soulItemList = soulItem.getSoulItemList();
		guiGraphics.renderItem(soulItem.getDefaultInstance(), 0, 0);
		for (int i = 0; i < soulItemList.size(); i++) {
			guiGraphics.renderItem(soulItemList.get(i).getDefaultInstance(), (i + 1) * 16, 0);
		}
		pose.popPose();
	}

}