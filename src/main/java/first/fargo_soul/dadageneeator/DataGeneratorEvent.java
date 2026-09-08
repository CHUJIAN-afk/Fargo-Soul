package first.fargo_soul.dadageneeator;

import first.fargo_soul.FargoSoul;
import net.minecraft.core.HolderLookup;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import top.theillusivec4.curios.api.CuriosDataProvider;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class DataGeneratorEvent {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        //饰品栏
        event.getGenerator().addProvider(event.includeServer(), new CuriosDataProvider(FargoSoul.MODID, event.getGenerator().getPackOutput(), event.getExistingFileHelper(), event.getLookupProvider()) {
            @Override
            public void generate(HolderLookup.Provider provider, ExistingFileHelper existingFileHelper) {
                this.createEntities("curio").addPlayer().addSlots("curio");
            }
        });
    }
}
