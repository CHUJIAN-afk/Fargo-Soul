package First.create.CreatePower.SoulStone;

import First.create.Create.CreateSoulsRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.SoulUtils;
import com.simibubi.create.foundation.fluid.FluidHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class DeepDivingSoul extends SoulItem {

    public DeepDivingSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }


    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @OnlyIn(Dist.CLIENT)
        public static void RenderFog(ViewportEvent.RenderFog event) {
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
                if (minecraft.player instanceof Player player && SoulUtils.getSoulItemFromSoulData(player, DeepDivingSoul.class) instanceof DeepDivingSoul) {
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

        public static void Breath(LivingBreatheEvent event) {
            if (event.getEntity() instanceof LivingEntity attacker && SoulUtils.getSoulItemFromSoulData(attacker, DeepDivingSoul.class) instanceof DeepDivingSoul) {
                event.setCanBreathe(true);
                event.setRefillAirAmount(attacker.getMaxAirSupply());
            }
        }

    }

}
