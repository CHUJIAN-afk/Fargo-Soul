package First.fargo_soul.Compact.Create.CreatePower;

import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

public class CreatePower extends SoulItem {

    public CreatePower(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                CreateSoulsRegister.Burner_Soul.get(),
                CreateSoulsRegister.CardBoard_Soul.get(),
                CreateSoulsRegister.CogWheel_Soul.get(),
                CreateSoulsRegister.DeepDiving_Soul.get(),
                CreateSoulsRegister.Goggles_Soul.get()
        );
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

    public final List<Component> TooltipList = List.of(
            Component.literal("“你突然有一种建造工厂的冲动”").withStyle(ChatFormatting.DARK_GRAY)
    );

}
