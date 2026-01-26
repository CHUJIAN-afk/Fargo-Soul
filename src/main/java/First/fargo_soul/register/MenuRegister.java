package First.fargo_soul.register;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.menu.SoulContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MenuRegister {

    public static final DeferredRegister<MenuType<?>> Register = DeferredRegister.create(Registries.MENU, FargoSoul.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<SoulContainer>> SoulContainer = Register.register("soul_menu", () -> IMenuTypeExtension.create(SoulContainer::new));

    public static void register(IEventBus eventBus){
        Register.register(eventBus);
    }

}
