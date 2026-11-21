package first.fargo_soul.register;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import first.fargo_soul.FargoSoul;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class FargoSoulAttributeRegister {

    private static final Registrate REGISTER = FargoSoul.REGISTRATE;

    public static final RegistryEntry<Attribute, Attribute> ArmorPierce = register("armor_pierce", 0.0D);

    private static RegistryEntry<Attribute, Attribute> register(String name, double defaultValue) {
        return REGISTER.simple(name, Registries.ATTRIBUTE, () -> new RangedAttribute(FargoSoul.rl(name).toString(), defaultValue, 0.0, Double.MAX_VALUE).setSyncable(true));
    }

    public static void init() {
    }
}
