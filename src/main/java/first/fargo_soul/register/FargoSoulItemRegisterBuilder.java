package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.dadageneeator.provider.FargoSoulItemModelProvider;
import first.fargo_soul.dadageneeator.provider.FargoSoulItemTagsProvider;
import first.fargo_soul.dadageneeator.provider.FargoSoulLangProvider;
import first.fargo_soul.dadageneeator.provider.FargoSoulRecipeProvider;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FargoSoulItemRegisterBuilder<T extends Item> {

    private static final DeferredRegister.Items Register = DeferredRegister.createItems(FargoSoul.MODID);
    private final DeferredItem<T> register;

    private FargoSoulItemRegisterBuilder(DeferredItem<T> register) {
        this.register = register;
    }

    public static <T extends Item> FargoSoulItemRegisterBuilder<T> build(String name, Supplier<T> supplier) {
        DeferredItem<T> register = Register.register(name, supplier);
        return new FargoSoulItemRegisterBuilder<>(register);
    }

    public static <T extends Item> FargoSoulItemRegisterBuilder<T> build(String name, Function<Item.Properties, T> supplier) {
        DeferredItem<T> register = Register.registerItem(name, supplier);
        return new FargoSoulItemRegisterBuilder<>(register);
    }

    public static FargoSoulItemRegisterBuilder<Item> build(String name) {
        return build(name, () -> new Item(new Item.Properties()));
    }

    public FargoSoulItemRegisterBuilder<T> recipe(Consumer<RecipeOutput> outputConsumer) {
        if (!FMLLoader.isProduction()) {
            FargoSoulRecipeProvider.RecipeGenerate.add(outputConsumer);
        }
        return this;
    }

    public FargoSoulItemRegisterBuilder<T> language(String key, String enDesc, String zhDesc) {
        if (!FMLLoader.isProduction()) {
            FargoSoulLangProvider.entry(key, enDesc, zhDesc);
        }
        return this;
    }

    public FargoSoulItemRegisterBuilder<T> itemLanguage(String en, String zh) {
        if (register != null) {
            ResourceLocation id = register.getId();
            return language("item." + id.getNamespace() + "." + id.getPath(), en, zh);
        }
        return this;
    }

    public FargoSoulItemRegisterBuilder<T> itemLanguageTooltip(int index, String en, String zh) {
        if (register != null) {
            ResourceLocation id = register.getId();
            return language("item." + id.getNamespace() + "." + id.getPath() + ".tooltip." + index, en, zh);
        }
        return this;
    }

    public FargoSoulItemRegisterBuilder<T> blockLanguage(String en, String zh) {
        if (register != null) {
            ResourceLocation id = register.getId();
            return language("block." + id.getNamespace() + "." + id.getPath(), en, zh);
        }
        return this;
    }

    public FargoSoulItemRegisterBuilder<T> itemTag(TagKey<Item> tagKey) {
        if (!FMLLoader.isProduction()) {
            FargoSoulItemTagsProvider.ItemTagsGenerate.computeIfAbsent(tagKey, key -> new ArrayList<>())
                    .add(register);
        }
        return this;
    }

    public FargoSoulItemRegisterBuilder<T> itemModel(BiConsumer<ResourceLocation, FargoSoulItemModelProvider> consumer) {
        if (!FMLLoader.isProduction()) {
            FargoSoulItemModelProvider.ItemModelGenerate.put(register.getId(), consumer);
        }
        return this;
    }

    public static void basicModel(ResourceLocation location, FargoSoulItemModelProvider provider) {
        provider.basicItem(location);
    }

    public static void handheldItem(ResourceLocation location, FargoSoulItemModelProvider provider) {
        provider.handheldItem(location);
    }

    public DeferredItem<T> build() {
        return register;
    }

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }
}
