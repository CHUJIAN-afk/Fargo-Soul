package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = FargoSoul.MODID, value = Dist.CLIENT)
public class FargoSoulModelRegister {

    private static final List<ModelResourceLocation> MODELS = new ArrayList<>();

    public static final ModelResourceLocation TERRA_BLADE = standalone("minion/terra_blade");
    public static final ModelResourceLocation CHLOROPHYTE_CRYSTAL = standalone("minion/chlorophyte_crystal");
    public static final ModelResourceLocation SHADOW_ORB = standalone("minion/shadow_orb");

    private static ModelResourceLocation standalone(String path) {
        ModelResourceLocation location = ModelResourceLocation.standalone(FargoSoul.rl(path));
        MODELS.add(location);
        return location;
    }

    @SubscribeEvent
    public static void registerAdditional(ModelEvent.RegisterAdditional event) {
        MODELS.forEach(event::register);
    }
}