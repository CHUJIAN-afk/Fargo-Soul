package First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Network.Packet.PenetratingNinjaSoulPacket;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static First.fargo_soul.Item.Soul.SoulsRegister.MonkSoul;

public class PenetratingNinjaSoul extends SoulItem {

    public PenetratingNinjaSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                MonkSoul.get()
        );
    }


    @OnlyIn(Dist.CLIENT)
    public static void PenetratingNinjaHandler(LocalPlayer player) {
        if (CurioUtils.isEquipped(player, SoulsRegister.PenetratingNinjaSoul.get())) {
            player.noPhysics = true;
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> {
                player.noPhysics = false;
            }, 1, TimeUnit.SECONDS);
            PacketDistributor.sendToServer(new PenetratingNinjaSoulPacket());
        }
    }

}
