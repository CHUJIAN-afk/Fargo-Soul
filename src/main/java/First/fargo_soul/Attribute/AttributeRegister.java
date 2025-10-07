package First.fargo_soul.Attribute;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = Fargo_soul.MODID)
public class AttributeRegister {

    public static final DeferredRegister<Attribute> ATTRIBUTE = DeferredRegister.create(Registries.ATTRIBUTE, Fargo_soul.MODID);

    public static final Holder<Attribute> CriticalChance;
    public static final Holder<Attribute> CriticalDamage;
    public static final Holder<Attribute> Damage;
    public static final Holder<Attribute> RangedDamage;
    public static final Holder<Attribute> RangedSpeed;
    public static final Holder<Attribute> ArmorPierce;

    static {
        CriticalChance = RegisterAttribute("critical_chance", 0.1D);
        CriticalDamage = RegisterAttribute("critical_damage", 2.0D);
        Damage = RegisterAttribute("damage", 1.0D);
        RangedDamage = RegisterAttribute("ranged_damage", 1.0D);
        RangedSpeed = RegisterAttribute("ranged_speed", 1.0D);
        ArmorPierce = RegisterAttribute("armor_pierce", 0.0D);
    }

    private static Holder<Attribute> RegisterAttribute(String name, double defaultValue) {
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, name);
        return ATTRIBUTE.register(name, () -> new RangedAttribute(resourceLocation.toString(), defaultValue, 0.0, Double.MAX_VALUE).setSyncable(true));
    }

    @SubscribeEvent
    public static void EntityAttributeModificationEvent(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, CriticalChance);
        event.add(EntityType.PLAYER, CriticalDamage);
        event.add(EntityType.PLAYER, Damage);
        event.add(EntityType.PLAYER, RangedDamage);
        event.add(EntityType.PLAYER, RangedSpeed);
        event.add(EntityType.PLAYER, ArmorPierce);
    }

    //弹射物速度处理
    public static void RangedSpeedHandler(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Projectile projectile && projectile.getOwner() instanceof Player player) {
            if (!projectile.getPersistentData().getBoolean("RangedSpeed") && player.getAttribute(RangedSpeed) instanceof AttributeInstance attributeInstance) {
                projectile.getPersistentData().putBoolean("RangedSpeed", true);
                projectile.setDeltaMovement(projectile.getDeltaMovement().scale(attributeInstance.getValue()));
            }
        }
    }

    //暴击率与暴击伤害处理
    public static void CriticalHandler(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity && event.getSource().getEntity() instanceof LivingEntity attacker) {
            if (attacker.getAttribute(CriticalChance) instanceof AttributeInstance criticalChance && attacker.getAttribute(CriticalDamage) instanceof AttributeInstance criticalDamage) {
                if (SoulUtils.random.nextDouble() < criticalChance.getValue()) {
                    event.setAmount((float) (event.getAmount() * criticalDamage.getValue()));
                }
            }
        }
    }

    //伤害与远程伤害处理
    public static void DamageAndRangedDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            if (attacker.getAttribute(Damage) instanceof AttributeInstance damage) {
                event.setAmount((float) (event.getAmount() * damage.getValue()));
            }
            if (!event.getSource().isDirect() && attacker.getAttribute(RangedDamage) instanceof AttributeInstance damage) {
                event.setAmount((float) (event.getAmount() * damage.getValue()));
            }
        }
    }

    public static void register(IEventBus eventBus) {
        ATTRIBUTE.register(eventBus);
    }

}
