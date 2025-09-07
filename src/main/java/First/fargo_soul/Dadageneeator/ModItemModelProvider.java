package First.fargo_soul.Dadageneeator;

import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Fargo_soul.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        addModels(SoulsRegister.TerraSoul.get());
        addModels(SoulsRegister.UniverseSoul.get());
        addModels(CreateSoulsRegister.Create_Power.get());
    }

    /**
     * 传入父魔石以注册所有子魔石模型
     */
    private void addModels(SoulItem soulItem) {
        basicItem(soulItem);
        CurioUtils.getAllCurioItems(soulItem.getSoulItemList()).stream().distinct().toList().forEach(this::basicItem);
    }

}