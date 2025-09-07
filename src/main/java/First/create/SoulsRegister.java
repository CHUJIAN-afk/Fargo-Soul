package First.create;


import First.create.CreatePower.CreatePower;
import First.create.CreatePower.SoulStone.*;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SoulsRegister {

    public static final DeferredRegister.Items Souls;
    public static final DeferredItem<SoulItem> CardBoard_Soul;
    public static final DeferredItem<SoulItem> DeepDiving_Soul;
    public static final DeferredItem<SoulItem> Goggles_Soul;
    public static final DeferredItem<SoulItem> Burner_Soul;
    public static final DeferredItem<SoulItem> CogWheel_Soul;
    public static final DeferredItem<SoulItem> Create_Power;

    static {
        Souls = DeferredRegister.createItems(Fargo_soul.MODID);
        CardBoard_Soul = Souls.registerItem("cardboard_soul", CardBoardSoul::new);
        DeepDiving_Soul = Souls.registerItem("deep_diving_soul", DeepDivingSoul::new);
        Goggles_Soul = Souls.registerItem("goggles_soul", GogglesSoul::new);
        Burner_Soul = Souls.registerItem("burner_soul", BurnerSoul::new);
        CogWheel_Soul = Souls.registerItem("cog_wheel_soul", CogWheelSouL::new);
        Create_Power = Souls.registerItem("create_power", CreatePower::new);
    }

    public static void register(IEventBus eventBus) {
        Souls.register(eventBus);
    }

}
