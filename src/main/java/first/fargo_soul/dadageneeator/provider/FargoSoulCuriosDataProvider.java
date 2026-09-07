package first.fargo_soul.dadageneeator.provider;

import first.fargo_soul.FargoSoul;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class FargoSoulCuriosDataProvider extends CuriosDataProvider {

    public FargoSoulCuriosDataProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(FargoSoul.MODID, packOutput, existingFileHelper, lookupProvider);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {
        this.createEntities("curio").addPlayer().addSlots("curio");
    }
}
