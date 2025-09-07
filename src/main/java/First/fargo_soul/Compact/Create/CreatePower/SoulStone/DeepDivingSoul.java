package First.fargo_soul.Compact.Create.CreatePower.SoulStone;

import First.fargo_soul.Compact.Create.CreatePower.CreateSoulItem;
import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import com.simibubi.create.foundation.fluid.FluidHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class DeepDivingSoul extends CreateSoulItem {

    public DeepDivingSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    @OnlyIn(Dist.CLIENT)
    public static void DeepDivingSoulRenderFogHandler(ViewportEvent.RenderFog event) {
        Camera camera = event.getCamera();
        Minecraft minecraft = Minecraft.getInstance();
        Level level = minecraft.level;
        BlockPos blockPos = camera.getBlockPosition();
        if (level != null) {
            FluidState fluidState = level.getFluidState(blockPos);
            if (camera.getPosition().y >= blockPos.getY() + fluidState.getHeight(level, blockPos))
                return;

            Fluid fluid = fluidState.getType();
            Entity entity = camera.getEntity();

            if (entity.isSpectator())
                return;
            if (minecraft.player instanceof Player player && CurioUtils.isEquipped(player, CreateSoulsRegister.DeepDiving_Soul.get())) {
                if (FluidHelper.isWater(fluid)) {
                    event.scaleFarPlaneDistance(6.25f);
                    event.setCanceled(true);
                } else if (FluidHelper.isLava(fluid)) {
                    event.setNearPlaneDistance(-4.0f);
                    event.setFarPlaneDistance(20.0f);
                    event.setCanceled(true);
                }
            }
        }
    }

    public static void DeepDivingSoulBreathHandler(LivingBreatheEvent event) {
        if (event.getEntity() instanceof Player player && CurioUtils.isEquipped(player, CreateSoulsRegister.DeepDiving_Soul.get())) {
            event.setCanBreathe(true);
            event.setRefillAirAmount(player.getMaxAirSupply());
        }
    }




}
