package First.fargo_soul.mixin.create.Accessor;


import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Accessor;

@Pseudo
@Mixin(BlazeBurnerBlockEntity.class)
public interface BlazeBurnerBlockEntityAccessor {

    @Accessor("activeFuel")
    void setActiveFuel(BlazeBurnerBlockEntity.FuelType fuel);

    @Accessor("remainingBurnTime")
    void setRemainingBurnTime(int ticks);

}
