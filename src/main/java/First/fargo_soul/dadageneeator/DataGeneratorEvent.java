package First.fargo_soul.dadageneeator;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.dadageneeator.provider.BaseItemModelProvider;
import First.fargo_soul.dadageneeator.provider.SoulRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class DataGeneratorEvent {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        boolean includeClient = event.includeClient();
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        //配方生成
        generator.addProvider(includeClient, new SoulRecipeProvider(packOutput, lookupProvider));
        //基本物品模型模型生成
        generator.addProvider(includeClient, new BaseItemModelProvider(packOutput, FargoSoul.MODID, existingFileHelper));
    }

}
