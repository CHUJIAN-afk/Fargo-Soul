package First.fargo_soul.Dadageneeator;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    private final SoulItem soulItem;

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper, String modid, SoulItem soulItem) {
        super(output, modid, existingFileHelper);
        this.soulItem = soulItem;
    }

    @Override
    protected void registerModels() {
        basicItem(soulItem);
        for (SoulItem item : CurioUtils.getAllCurioItems(soulItem.getSoulItemList())) {
            basicItem(item);
        }
    }

}