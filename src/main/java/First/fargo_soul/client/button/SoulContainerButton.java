package First.fargo_soul.client.button;

import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.network.SoulContainerButtonPacket;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SoulContainerButton extends Button {

    private boolean isActive;
    private final SoulItem soulItem;

    public SoulContainerButton(SoulItem soulItem) {
        super(
                0,
                0,
                36,
                18,
                Component.empty(),
                button -> {
                    if (button instanceof SoulContainerButton configButton) {
                        configButton.setActive(!configButton.isActive());
                        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(soulItem);
                        if (Minecraft.getInstance().player instanceof LocalPlayer player) {
                            player.getData(AttachmentRegister.AbilityEnabledData).setEnabled(soulItem, configButton.isActive());
                        }
                        PacketDistributor.sendToServer(new SoulContainerButtonPacket(resourceLocation, configButton.isActive()));
                    }
                },
                DEFAULT_NARRATION
        );
        this.soulItem = soulItem;
        this.isActive = Minecraft.getInstance().player instanceof LocalPlayer player && player.getData(AttachmentRegister.AbilityEnabledData).isEnabled(soulItem);
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        ItemStack defaultInstance = soulItem.getDefaultInstance();
        guiGraphics.renderItem(defaultInstance, this.getX() + 1, this.getY() + 1);
        boolean active = this.isActive();
        String text = active ? "✔" : "✘";
        int color = active ? 0xFF55FF55 : 0xFFFF5555;
        int x = this.getX() + this.width - 13;
        int y = this.getY() + (this.height - 8) / 2;
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        guiGraphics.drawString(font, text, x, y, color, true);
        if (this.isHovered()) {
            List<Component> list = CurioUtils.getSoulItemAttributesComponent(soulItem);
            if (!list.isEmpty()) {
                guiGraphics.renderTooltip(font, list, defaultInstance.getTooltipImage(), mouseX, mouseY);
            }
        }
    }

    @Override
    public void playDownSound(@NotNull SoundManager handler) {
        handler.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, this.isActive() ? 0.85F : 1.15F));
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
