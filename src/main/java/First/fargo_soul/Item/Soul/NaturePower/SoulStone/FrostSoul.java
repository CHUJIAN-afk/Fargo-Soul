package First.fargo_soul.Item.Soul.NaturePower.SoulStone;

import First.fargo_soul.Entity.Projectile.IceSpike.IceSpike;
import First.fargo_soul.Item.ProjectileItem.ProjectileItems;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.joml.Quaternionf;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class FrostSoul extends SoulItem {

    public FrostSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("每秒在玩家周围生成冰锥，最多可同时存在10个").withStyle(ChatFormatting.BLUE),
            Component.literal("冰锥每0.5秒自动锁定周围敌人发射").withStyle(ChatFormatting.BLUE),
            Component.literal("冰锥会对其击中的敌人造成0.25秒的冰冻和6秒的冻伤，冰锥的基础伤害对烈焰人提高至600%").withStyle(ChatFormatting.BLUE),
            Component.literal("敌人被冻结时无法移动，已经被冻结的敌人不会被再次冰冻或延长冰冻时间").withStyle(ChatFormatting.BLUE),
            Component.literal("被冻伤影响的敌人会持续受到寒冷伤害，降低最大生命值10%，降低20%移动速度").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“让我们给这个世界披上一层厚厚的冰衣”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void FrostSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof Player player && CurioUtils.isEquipped(player, Souls.FrostSoul.get())) {
            if (player.tickCount % 20 == 0) {
                int FrostSoul = player.getPersistentData().getInt("FrostSoul");
                player.getPersistentData().putInt("FrostSoul", Math.min(FrostSoul + 1, 10));
            }
            if (player.tickCount % 5 == 0) {
                int FrostSoul = player.getPersistentData().getInt("FrostSoul");
                TargetingConditions conditions = TargetingConditions.forCombat().range(30.0);
                LivingEntity target = player.level().getNearestEntity(LivingEntity.class, conditions, player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().inflate(10));
                if (FrostSoul > 0 && target != null) {
                    player.getPersistentData().putInt("FrostSoul", Math.max(FrostSoul - 1, 0));
                    if (player instanceof ServerPlayer) {
                        double x = player.getRandomX(2);
                        double y = player.getY() + 1;
                        double z = player.getRandomZ(2);
                        IceSpike iceSpike = new IceSpike(player.level(), x, y, z);
                        Vec3 toMonster = target.getBoundingBox().getCenter().subtract(x, y, z).normalize();
                        iceSpike.shoot(toMonster.x, toMonster.y, toMonster.z, 3.0f, 1.0f);
                        iceSpike.setOwner(player);
                        player.level().addFreshEntity(iceSpike);
                    }
                }
            }
        }
    }

    public static void FrostSoulRendererHnadler(SlotContext slotContext, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, float ageInTicks) {
        if (slotContext.entity() instanceof LocalPlayer player && CurioUtils.isEquipped(player, Souls.FrostSoul.get())) {
            int frostSoulCount = player.getPersistentData().getInt("FrostSoul");
            if (frostSoulCount > 0) {
                Minecraft minecraft = Minecraft.getInstance();
                ModelManager modelManager = minecraft.getModelManager();
                ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(ProjectileItems.IceSpike.get());
                BakedModel model = modelManager.getModel(ModelResourceLocation.inventory(resourceLocation));
                int displayCount = Math.min(frostSoulCount, 10);
                float angleIncrement = 360.0F / displayCount;
                float radius = 1.2f + (displayCount * 0.05f);
                float floatOffset = Mth.sin(ageInTicks * 0.1f) * 0.1f;
                for (int i = 0; i < displayCount; i++) {
                    matrixStack.pushPose();
                    Quaternionf playerRotation = new Quaternionf();
                    matrixStack.last().pose().getNormalizedRotation(playerRotation);
                    playerRotation.conjugate();
                    matrixStack.mulPose(playerRotation);
                    float angle = (ageInTicks * 1.5f + i * angleIncrement) % 360;
                    float rad = (float) Math.toRadians(angle);
                    float xPos = Mth.cos(rad) * radius;
                    float zPos = Mth.sin(rad) * radius;
                    float yOffset = i % 2 == 0 ? 0.1f : -0.1f;
                    matrixStack.translate(xPos, floatOffset + yOffset, zPos);
                    matrixStack.mulPose(new Quaternionf().rotateY((float) Math.toRadians(180 - angle)));
                    matrixStack.mulPose(new Quaternionf().rotateX(ageInTicks * 0.05f));
                    float scale = 0.4f + Mth.sin(ageInTicks * 0.2f + i) * 0.05f;
                    matrixStack.scale(scale, scale, scale);
                    matrixStack.scale(4.0f, 4.0f, 4.0f);
                    minecraft.getItemRenderer().render(
                            Souls.FrostSoul.get().getDefaultInstance(),
                            ItemDisplayContext.GROUND,
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

}
