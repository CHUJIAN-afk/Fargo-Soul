package First.fargo_soul.Curios.Soul.EarthPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
import First.fargo_soul.Curios.Souls;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CobaltSoul extends SoulItem {

    public CobaltSoul(Properties properties) {
        super(properties);
    }

    public final List<SoulItem> soulItemList = List.of(
            Souls.AncientCobaltSoul.get()
    );

    @Override
    public List<SoulItem> getCurioItemList() {
        return this.soulItemList;
    }

    public List<Component> list1 =
            soulItemList.stream()
                    .flatMap(curioItem -> curioItem.getAttributeList().stream())
                    .collect(Collectors.toList());

    public List<Component> list2 = List.of(
            Component.literal("从地面跳起时为你提供0.2秒无敌帧，该无敌帧不能叠加").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> AttributeList = Stream.concat(
            list1.stream(),
            list2.stream()
    ).collect(Collectors.toList());

    public List<Component> TooltipList = List.of(
            Component.literal("“真不敢相信这竟然不是钯金”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void CobaltSoulJumpHandler(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.CobaltSoul.get()) && player.onGround()) {
            if (player.invulnerableTime < 14) {
                player.invulnerableTime += 4;
            }
        }
    }


}
