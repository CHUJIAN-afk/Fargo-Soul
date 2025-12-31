package First.fargo_soul.dadageneeator;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.dadageneeator.provider.BaseItemModelProvider;
import First.fargo_soul.dadageneeator.provider.SoulAdvancementProvider;
import First.fargo_soul.dadageneeator.provider.SoulRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class DataGeneratorEvent {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        //物品模型模型
        generator.addProvider(event.includeClient(), new BaseItemModelProvider(packOutput, FargoSoul.MODID, existingFileHelper));
        //配方
        generator.addProvider(event.includeServer(), new SoulRecipeProvider(packOutput, lookupProvider));
        //成就
        generator.addProvider(event.includeServer(), new AdvancementProvider(packOutput, lookupProvider, existingFileHelper, List.of(new SoulAdvancementProvider())));
    }

}
