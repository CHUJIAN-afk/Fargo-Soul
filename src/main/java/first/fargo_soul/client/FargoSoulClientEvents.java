package first.fargo_soul.client;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.item.base.SoulItem;
import first.lyra.api.LyraAPI;
import first.lyra.client.tooltip.TooltipProvider;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.List;

@EventBusSubscriber(modid = FargoSoul.MODID, value = Dist.CLIENT)
public class FargoSoulClientEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        LyraAPI.tooltip(new TooltipProvider(-1, (stack, player) -> {
            if (stack.getItem() instanceof SoulItem soulItem) {
                return soulItem.getTooltip(stack, new TooltipFlag() {
                    @Override
                    public boolean isAdvanced() {
                        return false;
                    }

                    @Override
                    public boolean isCreative() {
                        return false;
                    }

                    @Override
                    public boolean hasShiftDown() {
                        return Screen.hasShiftDown();
                    }
                }, true);
            }
            return List.of();
        }));
    }
}
