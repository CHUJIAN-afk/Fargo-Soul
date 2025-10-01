package First.fargo_soul.mixin.fargo;


import First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone.BeeSoul;
import First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SoulStone.AncientHolySoul;
import First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SpiritPower;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(
            method = "eat",
            at = @At("HEAD")
    )
    public void eat(Level level, ItemStack food, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir) {
        Player player = (Player) (Object) this;
        if (food.is(Items.HONEY_BOTTLE) && SoulUtils.isEquipped(player, BeeSoul.class)) {
            player.heal(player.getMaxHealth() * 0.05f);
            Bee bee = new Bee(EntityType.BEE, level);
            bee.setPos(new Vec3(player.getRandomX(2), player.getRandomY() + 2, player.getRandomZ(2)));
            level.addFreshEntity(bee);
        }
    }

    @Inject(
            method = "getAttackStrengthScale",
            at = @At("RETURN"),
            cancellable = true
    )
    public void getAttackStrengthScale(float adjustTicks, CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        if (SoulUtils.isEquipped(player, AncientHolySoul.class)) {
            float lowStrength = SoulUtils.isEquipped(player, SpiritPower.class) ? 0.5f : 0.35f;
            cir.setReturnValue(Math.max(cir.getReturnValue(), lowStrength));
        }
    }

}
