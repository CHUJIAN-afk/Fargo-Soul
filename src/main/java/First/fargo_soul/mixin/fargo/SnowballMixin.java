package First.fargo_soul.mixin.fargo;

import First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone.FrostSoul;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Snowball.class)
public class SnowballMixin {

	@ModifyArg(
			method = "onHitEntity",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
			),
			index = 1
	)
	private float hurt(float amount) {
		Snowball snowball = (Snowball) (Object) this;
		if (snowball.getOwner() instanceof LivingEntity attacker && SoulUtils.isEquipped(attacker, FrostSoul.class)) {
			amount += 2;
			amount *= 3;
		}
		return amount;
	}

}
