package First.fargo_soul.register;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.recipe.SoulRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RecipeTypeRegister {

    private static final DeferredRegister<RecipeType<?>> Register =
            DeferredRegister.create(Registries.RECIPE_TYPE, FargoSoul.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<SoulRecipe>> Integration =
            Register.register("integration", () -> new RecipeType<>() {
                public String toString() {
                    return FargoSoul.MODID + ":" + "integration";
                }
            });

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }

}
