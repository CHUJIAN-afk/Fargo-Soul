package First.fargo_soul.Dadageneeator;

import First.fargo_soul.Event.DataGeneratorModelsEvent;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class DataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        boolean includeClient = event.includeClient();
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        DataGeneratorModelsEvent dataGeneratorModelsEvent = new DataGeneratorModelsEvent();
        NeoForge.EVENT_BUS.post(dataGeneratorModelsEvent);
        Map<String, SoulItem> map = dataGeneratorModelsEvent.getMap();
        map.forEach((modid, soulItem) -> generator.addProvider(includeClient, new ModItemModelProvider(packOutput, existingFileHelper, modid, soulItem)));
        //generator.addProvider(includeClient, new ModRecipeProvider(packOutput, lookupProvider));
    }

}
