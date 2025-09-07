package First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone;

import First.fargo_soul.Client.KeyBinding;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Network.Packet.VortexSoulPacket;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class VortexSoul extends SoulItem {

    public VortexSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.RED));
    }


    @OnlyIn(Dist.CLIENT)
    public static void VortexSoulInputHandler(InputEvent.Key event) {
        if (KeyBinding.VortexSoulKey.consumeClick() && Minecraft.getInstance().player instanceof LocalPlayer player && CurioUtils.isEquipped(player, SoulsRegister.VortexSoul.get())) {
            PacketDistributor.sendToServer(new VortexSoulPacket());
        }
    }



}
