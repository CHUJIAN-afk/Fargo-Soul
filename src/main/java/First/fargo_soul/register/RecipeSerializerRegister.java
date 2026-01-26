package First.fargo_soul.register;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.recipe.SoulRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RecipeSerializerRegister {

    private static final DeferredRegister<RecipeSerializer<?>> Register =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, FargoSoul.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SoulRecipe>> Integration =
            Register.register("integration", SoulRecipe.Serializer::new);

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }

}
