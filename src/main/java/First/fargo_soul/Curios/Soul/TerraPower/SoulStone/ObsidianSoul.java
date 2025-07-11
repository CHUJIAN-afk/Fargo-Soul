package First.fargo_soul.Curios.Soul.TerraPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
import First.fargo_soul.Curios.Souls;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static First.fargo_soul.Curios.Souls.ObsidianSoul;


public class ObsidianSoul extends SoulItem {

    public ObsidianSoul(Properties properties) {
        super(properties);
    }

    public final List<SoulItem> soulItemList = List.of(
            Souls.AshWoodSoul.get()
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
            Component.literal("免疫岩浆块与岩浆伤害").withStyle(ChatFormatting.BLUE),
            //Component.literal("你可以在熔岩中正常移动和游泳").withStyle(ChatFormatting.BLUE),
            //Component.literal("在熔岩中攻击会产生爆炸").withStyle(ChatFormatting.BLUE),
            Component.literal("火系伤害对你无效").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> AttributeList = Stream.concat(
            list1.stream(),
            list2.stream()
    ).collect(Collectors.toList());

    public List<Component> TooltipList = List.of(
            Component.literal("“大地在呼唤”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void ObsidianSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (!event.isCanceled() && event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, ObsidianSoul.get())) {
            if (event.getSource().type().msgId().contains("fire")) {
                event.setCanceled(true);
            }
            if (event.getSource().is(DamageTypes.LAVA)) {
                event.setCanceled(true);
            }
        }
    }


}