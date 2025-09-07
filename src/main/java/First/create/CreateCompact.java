package First.create.Create;

import net.neoforged.fml.ModList;

public class CreateCompact {

    public static final String MODID = "create";

    public static boolean isLoadCreate() {
        return ModList.get().isLoaded(MODID);
    }


}
