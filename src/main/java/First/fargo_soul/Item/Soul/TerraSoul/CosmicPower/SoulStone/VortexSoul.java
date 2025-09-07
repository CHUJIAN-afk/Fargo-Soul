package First.fargo_soul.Item.Soul.TerraSoul.CosmicPower.SoulStone;

import First.fargo_soul.Client.KeyBinding;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Network.Packet.StardustSoulPacket;
import First.fargo_soul.Network.Packet.VortexSoulPacket;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class VortexSoul extends SoulItem {

    public VortexSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.RED));
    }

    private float energy = 0;
    private final float maxEnergy = 3600;


    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void VortexSoulInputHandler(InputEvent.Key event) {
            if (event.getKey() == KeyBinding.VortexSoulKey.getKey().getValue() && KeyBinding.VortexSoulKey.consumeClick()) {
                if (Minecraft.getInstance().player instanceof LocalPlayer player && SoulUtils.getSoulItemFromSoulData(player, VortexSoul.class) instanceof VortexSoul vortexSoul) {
                    PacketDistributor.sendToServer(new VortexSoulPacket(++vortexSoul.energy >= vortexSoul.maxEnergy));
                }
            }
        }

    }

}
