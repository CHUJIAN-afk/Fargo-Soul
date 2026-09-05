package first.fargo_soul.dadageneeator;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.dadageneeator.provider.FargoSoulCuriosDataProvider;
import first.fargo_soul.dadageneeator.provider.FargoSoulLangProvider;
import first.lyra.dataGenerator.provider.LyraBlockTagsProvider;
import first.lyra.dataGenerator.provider.LyraItemModelProvider;
import first.lyra.dataGenerator.provider.LyraItemTagsProvider;
import first.lyra.dataGenerator.provider.LyraRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class DataGeneratorEvent {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        boolean client = event.includeClient();
        boolean server = event.includeServer();
        //语言
        generator.addProvider(client, new FargoSoulLangProvider(packOutput, FargoSoul.MODID, "en_us"));
        generator.addProvider(client, new FargoSoulLangProvider(packOutput, FargoSoul.MODID, "zh_cn"));
        //物品模型模型
        generator.addProvider(client, new LyraItemModelProvider(packOutput, existingFileHelper, FargoSoul.MODID));
        //饰品栏
        generator.addProvider(server, new FargoSoulCuriosDataProvider(packOutput, existingFileHelper, lookupProvider));
        //配方
        generator.addProvider(server, new LyraRecipeProvider(packOutput, lookupProvider));
        //方块标签
        LyraBlockTagsProvider blockTagsProvider = new LyraBlockTagsProvider(packOutput, lookupProvider, existingFileHelper, FargoSoul.MODID);
        generator.addProvider(server, blockTagsProvider);
        //物品标签
        generator.addProvider(server, new LyraItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper, FargoSoul.MODID));
    }
}
