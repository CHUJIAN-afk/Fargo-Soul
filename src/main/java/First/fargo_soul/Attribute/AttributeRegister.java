package First.fargo_soul.Attribute;

import First.fargo_soul.Fargo_soul;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import static First.fargo_soul.Utils.Utils.random;

@EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.MOD)
public class AttributeRegister {

    public static final DeferredRegister<Attribute> ATTRIBUTE = DeferredRegister.create(Registries.ATTRIBUTE, Fargo_soul.MODID);

    public static final Holder<Attribute> CriticalChance;
    public static final Holder<Attribute> CriticalDamage;
    public static final Holder<Attribute> Damage;

    static {
        CriticalChance = ATTRIBUTE.register(
                "critical_chance",
                () -> new RangedAttribute("fargo_soul:critical_chance", 0.1D, 0.0D, Double.MAX_VALUE).setSyncable(true)
        );

        CriticalDamage = ATTRIBUTE.register(
                "critical_damage",
                () -> new RangedAttribute("fargo_soul:critical_damage", 2.0D, 0.0D, Double.MAX_VALUE).setSyncable(true)
        );

        Damage = ATTRIBUTE.register(
                "damage",
                () -> new RangedAttribute("fargo_soul:damage", 1.0D, 0.0D, Double.MAX_VALUE).setSyncable(true)
        );
    }

    @SubscribeEvent
    public static void EntityAttributeModificationEvent(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, CriticalChance);
        event.add(EntityType.PLAYER, CriticalDamage);
        event.add(EntityType.PLAYER, Damage);
    }
    //暴击率与暴击伤害处理
    public static void CriticalHandler(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity && event.getSource().getEntity() instanceof LivingEntity attacker) {
            if (attacker.getAttribute(CriticalChance) instanceof AttributeInstance criticalChance && attacker.getAttribute(CriticalDamage) instanceof AttributeInstance criticalDamage) {
                if (random.nextDouble() < criticalChance.getValue()) {
                    event.setAmount((float) (event.getAmount() * criticalDamage.getValue()));
                }
            }
        }
    }
    //伤害属性处理
    public static void DamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            if (attacker.getAttribute(Damage) instanceof AttributeInstance damage) {
                if (random.nextDouble() < damage.getValue()) {
                    event.setAmount((float) (event.getAmount() * damage.getValue()));
                }
            }
        }
    }

    public static void register(IEventBus eventBus) {
        ATTRIBUTE.register(eventBus);
    }

}
