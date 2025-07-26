package First.fargo_soul.Item.Soul.DeathPower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Network.Packet.PenetratingNinjaSoulPacket;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static First.fargo_soul.Item.Soul.Souls.MonkSoul;

public class PenetratingNinjaSoul extends SoulItem {

    public PenetratingNinjaSoul(Properties properties) {
        super(properties);
    }

    public final List<SoulItem> soulItemList = List.of(
            MonkSoul.get()
    );

    public List<Component> list1 =
            soulItemList.stream()
                    .flatMap(curioItem -> curioItem.getAttributeList().stream())
                    .collect(Collectors.toList());

    public List<Component> list2 = List.of(
            Component.literal("冲刺后的1秒内可以穿过墙壁").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> AttributeList = Stream.concat(
            list1.stream(),
            list2.stream()
    ).collect(Collectors.toList());

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    public List<Component> TooltipList = List.of(
            Component.literal("“藏匿于墙中的村庄”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<SoulItem> getCurioItemList() {
        return this.soulItemList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void PenetratingNinjaHandler(LocalPlayer player) {
        if (CurioUtils.isEquipped(player, Souls.PenetratingNinjaSoul.get())) {
            player.noPhysics = true;
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> {
                player.noPhysics = false;
            }, 1, TimeUnit.SECONDS);
            PacketDistributor.sendToServer(new PenetratingNinjaSoulPacket());
        }
    }

}
