package First.fargo_soul.Recipe;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.SoulUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "soul");
	}

	@Override
	public void registerRecipes(@NotNull IRecipeRegistration registration) {
		List<ItemStack> list = new ArrayList<>();
		for (Item item : BuiltInRegistries.ITEM.stream().filter(item -> item instanceof SoulItem).toList()) {
			list.add(item.getDefaultInstance());
		}
		String key = "fargo_soul.soul_converge.desc.";
		registration.addItemStackInfo(
				list,
				Component.translatable(key + "0"),
				Component.translatable(key + "1"),
				Component.translatable(key + "2"),
				Component.translatable(key + "3"),
				Component.translatable(key + "4")
		);
		key = "fargo_soul.soul_core.desc.";
		registration.addItemStackInfo(
				SoulsRegister.SoulCoreItem.get().getDefaultInstance(),
				Component.translatable(key + "0"),
				Component.translatable(key + "1")
		);
	}

}
