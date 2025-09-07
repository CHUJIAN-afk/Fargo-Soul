package First.fargo_soul.Compact.Create.Event;


import First.fargo_soul.Client.Renderer.PlayerRenderer;
import First.fargo_soul.Compact.Create.CreateCompact;
import First.fargo_soul.Compact.Create.CreatePower.SoulStone.BurnerSoul;
import First.fargo_soul.Compact.Create.CreatePower.SoulStone.DeepDivingSoul;
import First.fargo_soul.Compact.Create.CreatePower.SoulStone.GogglesSoul;
import First.fargo_soul.Compact.Create.CreatePower.CreateSoulItem;
import First.fargo_soul.Compact.Create.CreateSoulsRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Utils.CustomUtils;
import com.simibubi.create.Create;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.loot.LootPool;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class CreateEvent {

    @EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.GAME)
    public static class CreateGameEvent {

        @SubscribeEvent
        public static void RightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
            if (CreateCompact.isLoadCreate()) {
                BurnerSoul.BurnerSoulRightClickBlockHandler(event);
            }
        }

        @SubscribeEvent
        public static void PlayerTickEvent(PlayerTickEvent.Post event) {
            if (CreateCompact.isLoadCreate()) {
                GogglesSoul.GogglesSoulTickHandler(event);
            }
        }

        @SubscribeEvent
        public static void LivingBreatheEvent(LivingBreatheEvent event) {
            if (CreateCompact.isLoadCreate()) {
                DeepDivingSoul.DeepDivingSoulBreathHandler(event);
            }
        }

        @SubscribeEvent
        public static void LootTableLoadEvent(LootTableLoadEvent event) {
            if (CreateCompact.isLoadCreate()) {
                CustomUtils.AddLootTable(event, CreateSoulsRegister.CreateSouls.getRegistry().get(), LootPool.lootPool().name(Create.ID));
            }
        }

    }

    @EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static class CreateClientTEvent {

        @SubscribeEvent
        public static void RenderFog(ViewportEvent.RenderFog event) {
            if (CreateCompact.isLoadCreate()) {
                DeepDivingSoul.DeepDivingSoulRenderFogHandler(event);
            }
        }

        @SubscribeEvent
        public static void ItemTooltipHandler(ItemTooltipEvent event) {
            if (event.getItemStack().getItem() instanceof CreateSoulItem && !CreateCompact.isLoadCreate() && !event.getFlags().hasShiftDown()) {
                event.getToolTip().addLast(Component.translatable("item.fargo_soul.create_compact.tooltip").withStyle(ChatFormatting.RED));
            }
        }

    }

    @EventBusSubscriber(modid = Fargo_soul.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvent {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            //机械动力联动
            if (CreateCompact.isLoadCreate()) {
                CreateSoulsRegister.CreateSouls.getRegistry().get().forEach(item -> CuriosRendererRegistry.register(item, PlayerRenderer::new));
            }
        }

    }










}
