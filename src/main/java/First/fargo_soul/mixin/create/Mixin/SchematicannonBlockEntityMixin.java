package First.fargo_soul.mixin.create.Mixin;


import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.mixin.create.Accessor.SchematicannonBlockEntityAccessor;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Pseudo
@Mixin(SchematicannonBlockEntity.class)
public class SchematicannonBlockEntityMixin {


    @Inject(
            method = "tickPrinter",
            at = @At(
                    value = "FIELD",
                    target = "Lcom/simibubi/create/content/schematics/cannon/SchematicannonBlockEntity;printerCooldown:I",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void reduceCooldown(CallbackInfo ci) {
        SchematicannonBlockEntity blockEntity = (SchematicannonBlockEntity) (Object) this;
        Level level = blockEntity.getLevel();
        if (level != null) {
            BlockPos blockPos = blockEntity.getBlockPos();
            List<Player> playerList = level.getEntitiesOfClass(Player.class, new AABB(blockPos).inflate(4), player -> CurioUtils.isEquipped(player, CreateSoulsRegister.Goggles_Soul.get()));
            if (!playerList.isEmpty()) {
                SchematicannonBlockEntityAccessor accessor = (SchematicannonBlockEntityAccessor) blockEntity;
                int printerCooldown = (int) Math.floor(accessor.getPrinterCooldown() * 0.5);
                accessor.setPrinterCooldown(printerCooldown);
                ParticleUtils.spawnParticleSphere((ServerLevel) level, blockPos.getCenter(), ParticleTypes.SOUL_FIRE_FLAME, 1, 1, 0.5f);
            }
        }
    }


}
