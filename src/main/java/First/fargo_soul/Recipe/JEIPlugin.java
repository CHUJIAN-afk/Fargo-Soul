package First.fargo_soul.Recipe;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.SoulUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
		List<SoulItem> soulItemList = new ArrayList<>();
		SoulItem soulItem = SoulsRegister.TerraSoul.get();
		soulItemList.add(soulItem);
		soulItemList.addAll(SoulUtils.getAllCurioItems(soulItem.getSoulItemList()));
		List<ItemStack> list = new ArrayList<>();
		for (SoulItem item : soulItemList) {
			list.add(item.getDefaultInstance());
		}
		final String TRANSLATION_KEY_SOUL_CONVERGE_DESC_0 = "fargo_soul.soul_converge.desc.0";
		final String TRANSLATION_KEY_SOUL_CONVERGE_DESC_1 = "fargo_soul.soul_converge.desc.1";
		final String TRANSLATION_KEY_SOUL_CONVERGE_DESC_2 = "fargo_soul.soul_converge.desc.2";
		final String TRANSLATION_KEY_SOUL_CONVERGE_DESC_3 = "fargo_soul.soul_converge.desc.3";
		final String TRANSLATION_KEY_SOUL_CONVERGE_DESC_4 = "fargo_soul.soul_converge.desc.4";
		final String TRANSLATION_KEY_SOUL_CORE_DESC_1 = "fargo_soul.soul_core.desc.1";
		final String TRANSLATION_KEY_SOUL_CORE_DESC_2 = "fargo_soul.soul_core.desc.2";
		registration.addItemStackInfo(
				list,
				Component.translatable(TRANSLATION_KEY_SOUL_CONVERGE_DESC_0),
				Component.translatable(TRANSLATION_KEY_SOUL_CONVERGE_DESC_1),
				Component.translatable(TRANSLATION_KEY_SOUL_CONVERGE_DESC_2),
				Component.translatable(TRANSLATION_KEY_SOUL_CONVERGE_DESC_3),
				Component.translatable(TRANSLATION_KEY_SOUL_CONVERGE_DESC_4)
		);

		registration.addItemStackInfo(
				SoulsRegister.SoulCoreItem.get().getDefaultInstance(),
				Component.translatable(TRANSLATION_KEY_SOUL_CORE_DESC_1),
				Component.translatable(TRANSLATION_KEY_SOUL_CORE_DESC_2)
		);
	}

}
