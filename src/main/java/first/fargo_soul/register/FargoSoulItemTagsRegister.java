package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.CuriosConstants;

public class FargoSoulItemTagsRegister {

    /** Curios 饰品槽标签，使物品能放入 curio 槽位 */
    public static final TagKey<Item> Curio = register(ResourceLocation.fromNamespaceAndPath(CuriosConstants.MOD_ID, "curio"));

    // 创造模式分类分段特征标签（物品带标签自动归入对应分段）
    public static final TagKey<Item> SectionBasic = register("sections/basic");
    public static final TagKey<Item> SectionTerraSoul = register("sections/terra_soul");
    public static final TagKey<Item> SectionPower = register("sections/power");
    public static final TagKey<Item> SectionEnchantment = register("sections/enchantment");

    private static TagKey<Item> register(ResourceLocation location) {
        return TagKey.create(Registries.ITEM, location);
    }

    private static TagKey<Item> register(String path) {
        return TagKey.create(Registries.ITEM, FargoSoul.rl(path));
    }
}
