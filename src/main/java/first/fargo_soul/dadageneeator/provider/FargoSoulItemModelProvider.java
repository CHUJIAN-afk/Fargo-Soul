package first.fargo_soul.dadageneeator.provider;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class FargoSoulItemModelProvider extends ItemModelProvider {

    public static final Map<ResourceLocation, BiConsumer<ResourceLocation, FargoSoulItemModelProvider>> ItemModelGenerate = new HashMap<>();

    public FargoSoulItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ItemModelGenerate.entrySet()
                .removeIf(entry -> {
                    ResourceLocation key = entry.getKey();
                    BiConsumer<ResourceLocation, FargoSoulItemModelProvider> consumer = entry.getValue();
                    consumer.accept(key, this);
                    return true;
                });
    }
}
