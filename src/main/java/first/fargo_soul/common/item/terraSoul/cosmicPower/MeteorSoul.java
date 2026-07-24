package first.fargo_soul.common.item.terraSoul.cosmicPower;

import com.google.common.collect.Multimap;
import first.fargo_soul.common.item.base.SoulItem;
import net.minecraft.client.player.Input;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import top.theillusivec4.curios.api.SlotContext;

public class MeteorSoul extends SoulItem {

    public MeteorSoul(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> multimap = super.getAttributeModifiers(slotContext, id, stack);
        multimap.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(id, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return multimap;
    }

    @Override
    public void movementInput(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        Input input = event.getInput();
        Vec3 deltaMovement = player.getDeltaMovement();
        double y = deltaMovement.y();
        if (y < 0 && input.shiftKeyDown) {
            if (y > -1) {
                player.setDeltaMovement(new Vec3(deltaMovement.x(), -1, deltaMovement.z()));
            }
        }
    }
}