package First.fargo_soul.Utils;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AttributeUtils {
    public static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public static void ConditionAttributeModifier(LivingEntity livingEntity, Holder<Attribute> attribute, ResourceLocation resourceLocation, double amount, AttributeModifier.Operation operation, boolean condition) {
        if (condition) {
            addAttributeModifier(livingEntity, attribute, resourceLocation, amount, operation);
        } else {
            removeAttributeModifier(livingEntity, attribute, resourceLocation);
        }
    }

    public static void addAttributeModifier(LivingEntity livingEntity, Holder<Attribute> attribute, ResourceLocation resourceLocation, double amount, AttributeModifier.Operation operation) {
        AttributeModifier modifier = new AttributeModifier(resourceLocation, amount, operation);
        if (livingEntity.getAttribute(attribute) instanceof AttributeInstance attributeInstance) {
            if (attributeInstance.getModifier(resourceLocation) != null) {
                attributeInstance.removeModifier(resourceLocation);
            }
            attributeInstance.addPermanentModifier(modifier);
        }
    }

    public static void removeAttributeModifier(LivingEntity livingEntity, Holder<Attribute> attribute, ResourceLocation resourceLocation){
        if (livingEntity.getAttribute(attribute) instanceof AttributeInstance attributeInstance) {
            if (attributeInstance.getModifier(resourceLocation) != null) {
                attributeInstance.removeModifier(resourceLocation);
            }
        }
    }

}
