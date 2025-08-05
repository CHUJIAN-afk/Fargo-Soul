package First.fargo_soul.Compact.Create;


import First.fargo_soul.Compact.Create.CreatePower.CreatePower;
import First.fargo_soul.Compact.Create.CreatePower.SoulStone.*;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreateSoulsRegister {

    public static final DeferredRegister.Items CreateSouls;

    public static final DeferredItem<SoulItem> CardBoard_Soul;
    public static final DeferredItem<SoulItem> DeepDiving_Soul;
    public static final DeferredItem<SoulItem> Goggles_Soul;
    public static final DeferredItem<SoulItem> Burner_Soul;
    public static final DeferredItem<SoulItem> CogWheel_Soul;
    public static final DeferredItem<SoulItem> Create_Power;
    public static final DeferredItem<SoulItem> Potato_Soul;

    static {
        CreateSouls = DeferredRegister.createItems(Fargo_soul.MODID);
        CardBoard_Soul = CreateSouls.registerItem("cardboard_soul", CardBoardSoul::new);
        DeepDiving_Soul = CreateSouls.registerItem("deep_diving_soul", DeepDivingSoul::new);
        Goggles_Soul = CreateSouls.registerItem("goggles_soul", GogglesSoul::new);
        Burner_Soul = CreateSouls.registerItem("burner_soul", BurnerSoul::new);
        CogWheel_Soul = CreateSouls.registerItem("cog_wheel_soul", CogWheelSouL::new);
        Potato_Soul = CreateSouls.registerItem("potato_soul", PotatoSoul::new);
        Create_Power = CreateSouls.registerItem("create_power", CreatePower::new);
    }

    public static void register(IEventBus eventBus) {
        if (CreateCompact.isLoadCreate()) {
            CreateSouls.register(eventBus);
        }
    }

}
