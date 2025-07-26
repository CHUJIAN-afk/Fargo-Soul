package First.fargo_soul.Item.Soul.CosmicPower.SoulStone;

import First.fargo_soul.Client.KeyBinding;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Network.Packet.StardustSoulPacket;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class StardustSoul extends SoulItem {

    public StardustSoul (Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("按下“冻结”键后会冻结时间，持续6秒，该效果有3600秒冷却时间").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“你成为了替身使者”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }
    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void StardustSoulInputHandler(InputEvent.Key event) {
        if (KeyBinding.StardustSoulKey.consumeClick() && Minecraft.getInstance().player instanceof LocalPlayer player && CurioUtils.isEquipped(player, Souls.StardustSoul.get())) {
            PacketDistributor.sendToServer(new StardustSoulPacket());
        }
    }




}
