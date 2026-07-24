package first.fargo_soul.mixin.minecraft;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExtraCodecs.class)
public class ExtraCodecsMixin {

    @Inject(
            method = "intRange",
            at = @At(value = "HEAD")
    )
    private static void fargo_modifyMaxStackCodec(
            int min,
            int max,
            CallbackInfoReturnable<Codec<Integer>> cir,
            @Local(ordinal = 1, argsOnly = true) LocalIntRef maxRef
    ) {
        if (min == 1 && max == 99) {
            maxRef.set(Integer.MAX_VALUE);
        }
    }


}




