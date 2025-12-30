package First.fargo_soul.dadageneeator.provider;

import First.fargo_soul.register.BlockRegister;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BaseItemModelProvider extends ItemModelProvider {

    public BaseItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        SoulUtils.RegisterSoulList.forEach(this::basicItem);

        simpleBlockItem(BlockRegister.CosmicCrucible.get());
    }

}
