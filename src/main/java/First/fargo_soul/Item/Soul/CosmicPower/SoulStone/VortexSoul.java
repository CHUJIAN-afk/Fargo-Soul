package First.fargo_soul.Item.Soul.CosmicPower.SoulStone;

import First.fargo_soul.Client.KeyBinding;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Network.Packet.VortexSoulPacket;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class VortexSoul extends SoulItem {

    public VortexSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("按下“传送”键传送至目视的位置").withStyle(ChatFormatting.BLUE),
            Component.literal("在传送位置产生一个漩涡，持续吸引并伤害附近的敌人").withStyle(ChatFormatting.BLUE),
            Component.literal("最大传送距离为512格，大于此距离无法传送").withStyle(ChatFormatting.BLUE),
            Component.literal("传送冷却时间为15秒").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“撕裂现实”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void VortexSoulInputHandler(InputEvent.Key event) {
        if (KeyBinding.VortexSoulKey.consumeClick() && Minecraft.getInstance().player instanceof LocalPlayer player && CurioUtils.isEquipped(player, Souls.VortexSoul.get())) {
            PacketDistributor.sendToServer(new VortexSoulPacket());
        }
    }



}
