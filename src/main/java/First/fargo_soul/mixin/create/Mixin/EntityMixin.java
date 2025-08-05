package First.fargo_soul.mixin.create.Mixin;


import First.fargo_soul.Compact.Create.CreateCompact;
import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(Entity.class)
public class EntityMixin {

    @ModifyReturnValue(
            method = "fireImmune()Z",
            at = @At("RETURN")
    )
    public boolean fireImmune(boolean original) {
        Entity entity = (Entity) (Object) this;
        if (CreateCompact.isLoadCreate()) {
            return (original || (entity instanceof Player player && CurioUtils.isEquipped(player, CreateSoulsRegister.DeepDiving_Soul.get())));
        }
        return original;
    }

}
