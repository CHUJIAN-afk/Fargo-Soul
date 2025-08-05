package First.fargo_soul.mixin.create.Accessor;

import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

@Pseudo
@Mixin(SchematicannonBlockEntity.class)
public interface SchematicannonBlockEntityAccessor {

    @Accessor("printerCooldown")
    void setPrinterCooldown(int fuel);

    @Accessor("printerCooldown")
    int getPrinterCooldown();

}
