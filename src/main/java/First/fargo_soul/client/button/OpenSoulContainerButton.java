package First.fargo_soul.client.button;

import First.fargo_soul.common.menu.SoulContainer;
import First.fargo_soul.network.OpenSoulContainerPacket;
import First.fargo_soul.register.NetworkPacketRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class OpenSoulContainerButton extends Button {

    public OpenSoulContainerButton(int x, int y) {
        super(
                x,
                y,
                8,
                8,
                Component.empty(),
                button -> {
                    if (Minecraft.getInstance().player instanceof LocalPlayer player) {
                        if (player.containerMenu instanceof SoulContainer) {
                            Minecraft.getInstance().setScreen(new InventoryScreen(player));
                        }
                        NetworkPacketRegister.playToServer(new OpenSoulContainerPacket());
                    }
                },
                DEFAULT_NARRATION
        );
    }

}
