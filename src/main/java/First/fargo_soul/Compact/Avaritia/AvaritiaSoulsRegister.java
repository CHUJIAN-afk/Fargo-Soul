package First.fargo_soul.Compact.Avaritia;


import First.fargo_soul.Compact.Avaritia.InfinityPower.AvaritiaPower;
import First.fargo_soul.Compact.Avaritia.InfinityPower.Soul.*;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AvaritiaSoulsRegister {

    public static final DeferredRegister.Items AvaritiaSouls;
    public static final DeferredItem<SoulItem> BlazingBone_Soul;
    public static final DeferredItem<SoulItem> CrystalMatrix_Soul;
    public static final DeferredItem<SoulItem> Infinity_Soul;
    public static final DeferredItem<SoulItem> Neutron_Soul;
    public static final DeferredItem<SoulItem> Avaritia_Power;

    static {
        AvaritiaSouls = DeferredRegister.createItems(Fargo_soul.MODID);
        BlazingBone_Soul = AvaritiaSouls.registerItem("blazing_bone_soul", BlazingBoneSoul::new);
        CrystalMatrix_Soul = AvaritiaSouls.registerItem("crystal_matrix_soul", CrystalMatrixSoul::new);
        Infinity_Soul = AvaritiaSouls.registerItem("infinity_soul", InfinitySoul::new);
        Neutron_Soul = AvaritiaSouls.registerItem("neutron_soul", NeutronSoul::new);
        Avaritia_Power = AvaritiaSouls.registerItem("avaritia_power", AvaritiaPower::new);
    }

    public static void register(IEventBus eventBus) {
        AvaritiaSouls.register(eventBus);
    }

}
