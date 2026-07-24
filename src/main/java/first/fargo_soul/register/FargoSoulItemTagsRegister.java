package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public class FargoSoulItemTagsRegister {

    public static final TagKey<Item> Curio = register(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "curio"));

    private static TagKey<Item> register(ResourceLocation location) {
        return TagKey.create(Registries.ITEM, location);
    }

    private static TagKey<Item> register(String path) {
        return TagKey.create(Registries.ITEM, FargoSoul.rl(path));
    }
}
