package First.fargo_soul.Item.Soul;

import First.fargo_soul.Utils.CurioUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.joml.Quaternionf;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SoulItem extends Item implements ICurioItem {
    public SoulItem(Properties properties) {
        super(new Properties().stacksTo(1).durability(0));
    }

    public static final List<SoulItem> SOUL_ITEM_LIST = new ArrayList<>();

    public List<SoulItem> getCurioItemList() {
        return SOUL_ITEM_LIST;
    }

    public List<Component> AttributeList = new ArrayList<>();

    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    public List<Component> TooltipList = new ArrayList<>();

    public List<Component> getTooltipList() {
        return this.TooltipList;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack itemStack) {
        LivingEntity livingEntity = slotContext.entity();
        return !CurioUtils.isEquipped(livingEntity, itemStack.getItem());
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static void invulnerableTimeHandler(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player) && event.getEntity() instanceof LivingEntity livingEntity && event.getSource().getWeaponItem() == null) {
            livingEntity.invulnerableTime = 0;
        }
    }

    public static <T extends LivingEntity, M extends EntityModel<T>> void SoulItemRendererHnadler(ItemStack stack, SlotContext slotContext,
                                                                                                  PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
                                                                                                  int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                                                                                                  float netHeadYaw, float headPitch) {
        Minecraft minecraft = Minecraft.getInstance();
        if (stack.getItem() instanceof SoulItem soul) {
            List<SoulItem> soulItemList = CurioUtils.getAllCurioItems(soul.getCurioItemList());
            soulItemList.add(soul);
            for (SoulItem soulItem : soulItemList) {
                ModelManager modelManager = minecraft.getModelManager();
                ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(soulItem);
                BakedModel model = modelManager.getModel(ModelResourceLocation.inventory(resourceLocation));
                matrixStack.pushPose();
                //去除玩家视角影响
                Quaternionf playerRotation = new Quaternionf();
                matrixStack.last().pose().getNormalizedRotation(playerRotation);
                playerRotation.conjugate();
                matrixStack.mulPose(playerRotation);
                //坐标偏移
                int hashCode = soulItem.hashCode();
                double random = new Random(new Random(hashCode).nextInt()).nextDouble();
                double angle = ageInTicks * (random * 4 + 1);
                double semiMajorAxis = 0.6 + random * 2.4f;
                double semiMinorAxis = 0.4 + random * 1.6f;
                if (new Random(hashCode + 1).nextBoolean()) angle *= -new Random(hashCode + 2).nextFloat();
                float x = (float) (Math.cos(Math.toRadians(angle)) * semiMajorAxis);
                float y = (float) Math.sin(Math.toRadians(angle / 2));
                float z = (float) (Math.sin(Math.toRadians(angle)) * semiMinorAxis);
                //模型坐标
                matrixStack.translate(x, y, z);
                //物品旋转角度
                matrixStack.mulPose(new Quaternionf().rotateX(x));
                matrixStack.mulPose(new Quaternionf().rotateY(y));
                matrixStack.mulPose(new Quaternionf().rotateZ(z));
                //物品模型缩放
                matrixStack.scale(0.5f, 0.5f, 0.5f);
                minecraft.getItemRenderer().render(
                        soulItem.getDefaultInstance(),
                        ItemDisplayContext.HEAD,
                        false,
                        matrixStack,
                        renderTypeBuffer,
                        light,
                        OverlayTexture.NO_OVERLAY,
                        model
                );
                matrixStack.popPose();
            }
        }
    }
}






