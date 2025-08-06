package First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Network.Packet.PenetratingNinjaSoulPacket;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static First.fargo_soul.Item.Soul.SoulsRegister.MonkSoul;

public class PenetratingNinjaSoul extends SoulItem {

    public PenetratingNinjaSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    public final List<SoulItem> soulItemList = List.of(
            MonkSoul.get()
    );

    public final List<Component> list1 =
            soulItemList.stream()
                    .flatMap(curioItem -> curioItem.getAttributeList().stream())
                    .collect(Collectors.toList());


    public final List<Component> list2 = List.of(
            Component.translatable("item.fargo_soul.penetrating_ninja_soul.attribute.1").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> AttributeList = Stream.concat(
            list1.stream(),
            list2.stream()
    ).collect(Collectors.toList());

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.penetrating_ninja_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
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
