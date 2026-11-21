package first.fargo_soul;

import com.tterrag.registrate.Registrate;
import first.fargo_soul.register.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.fml.common.Mod;

@Mod(FargoSoul.MODID)
public class FargoSoul {

    public static final String MODID = "fargo_soul";
    public static final Registrate REGISTRATE = Registrate.create(MODID).defaultCreativeTab((ResourceKey<CreativeModeTab>) null);

    public FargoSoul() {
        FargoSoulItemRegister.init();
        FargoSoulCreativeModeTabRegister.init();
        FargoSoulMobEffectRegister.init();
        FargoSoulAttributeRegister.init();
        FargoSoulAttachmentRegister.init();
        SummonerAttachmentEntityRegister.init();
        FargoSoulSoulInfoRegister.init();
        new FargoSoulLanguageProvider(MODID).init();
        new FargoSoulLootProvider(MODID).init();
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path.toLowerCase());
    }
}
