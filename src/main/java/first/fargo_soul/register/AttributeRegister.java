package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AttributeRegister {

    private static final DeferredRegister<Attribute> Register = DeferredRegister.create(Registries.ATTRIBUTE, FargoSoul.MODID);

    public static final Holder<Attribute> CriticalChance = register("critical_chance", 0.1D);
    public static final Holder<Attribute> CriticalDamage = register("critical_damage", 2.0D);
    public static final Holder<Attribute> ArmorPierce = register("armor_pierce", 0.0D);
    public static final Holder<Attribute> Damage = register("damage", 0.0f);

    private static Holder<Attribute> register(String name, double defaultValue) {
        return Register.register(name, () -> new RangedAttribute(FargoSoul.rl(name).toString(), defaultValue, 0.0, Double.MAX_VALUE).setSyncable(true));
    }

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }

}
