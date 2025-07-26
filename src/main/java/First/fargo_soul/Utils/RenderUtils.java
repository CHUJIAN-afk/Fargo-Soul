package First.fargo_soul.Utils;

import First.fargo_soul.Item.Soul.DeathPower.SoulStone.DarkArtistSoul;
import First.fargo_soul.Item.Soul.NaturePower.SoulStone.FrostSoul;
import First.fargo_soul.Item.Soul.NaturePower.SoulStone.GreenSoul;
import First.fargo_soul.Item.Soul.NaturePower.SoulStone.LavaSoul;
import First.fargo_soul.Item.Soul.NaturePower.SoulStone.RainCloudSoul;
import First.fargo_soul.Item.Soul.SpiritPower.SoulStone.AncientHolySoul;
import First.fargo_soul.Item.Soul.WillPower.Soulstone.GoldSoul;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class RenderUtils {

    public static void RenderHandler(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, float ageInTicks, float partialTicks) {
        FrostSoul.FrostSoulRendererHnadler(slotContext, matrixStack, renderTypeBuffer, light, ageInTicks);
        GreenSoul.GreenSoulRenderHnadler(slotContext, matrixStack, renderTypeBuffer, light, ageInTicks);
        RainCloudSoul.RainCloudSoulRenderHnadler(slotContext, matrixStack, renderTypeBuffer, light);
        AncientHolySoul.AncientHolySoulRenderHnadler(slotContext, matrixStack, renderTypeBuffer, ageInTicks);
        LavaSoul.LavaSoulRenderHandler(slotContext, matrixStack, renderTypeBuffer, ageInTicks);
        DarkArtistSoul.DarkArtistSoulRenderHnadler(slotContext, matrixStack, renderTypeBuffer, ageInTicks);
        GoldSoul.GoldSoulRenderHnadler(slotContext, matrixStack, renderTypeBuffer);
    }

    public static BakedModel getItemBakedModel(Item item) {
        Minecraft minecraft = Minecraft.getInstance();
        ModelManager modelManager = minecraft.getModelManager();
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item);
        return modelManager.getModel(ModelResourceLocation.inventory(resourceLocation));
    }





}
