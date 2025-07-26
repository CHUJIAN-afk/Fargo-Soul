package First.fargo_soul.Item.Soul.NaturePower.SoulStone;

import First.fargo_soul.Item.ProjectileItem.ProjectileItems;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.joml.Quaternionf;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class RainCloudSoul extends SoulItem {

    public RainCloudSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("召唤一把可反弹射弹的雨伞").withStyle(ChatFormatting.BLUE),
            Component.literal("雨伞反射射弹的伤害合计超过20点后会破裂，每2分钟尝试恢复或修复雨伞").withStyle(ChatFormatting.BLUE),
            Component.literal("获得缓降药水的效果").withStyle(ChatFormatting.BLUE),
            Component.literal("免疫雷击伤害").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“改日再来”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void RainCloudSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player && CurioUtils.isEquipped(player, Souls.RainCloudSoul.get())) {
            //反射
            float RainCloudSoul = player.getPersistentData().getFloat("RainCloudSoul");
            if (event.getSource().getDirectEntity() instanceof Projectile projectile && RainCloudSoul < 20) {
                if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                    projectile.setOwner(player);
                    Vec3 toMonster = livingEntity.getBoundingBox().getCenter().subtract(projectile.position()).normalize();
                    projectile.shoot(toMonster.x, toMonster.y, toMonster.z, 10f, 0.0f);
                } else {
                    Vec3 deltaMovement = projectile.getDeltaMovement();
                    projectile.setDeltaMovement(-deltaMovement.x() * 3, -deltaMovement.y() * 3, -deltaMovement.z() * 3);
                }
                event.setCanceled(true);
                float damage = event.getAmount();
                player.getPersistentData().putFloat("RainCloudSoul", RainCloudSoul + damage);
            }
            //免疫雷击
            if (player instanceof ServerPlayer && event.getSource().is(DamageTypes.LIGHTNING_BOLT)) {
                event.setCanceled(true);
            }
        }
    }

    public static void RainCloudSoulTickHnadler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof Player player && CurioUtils.isEquipped(player, Souls.RainCloudSoul.get())) {
            //恢复雨伞
            if (player.tickCount % 1200 == 0) {
                player.getPersistentData().remove("RainCloudSoul");
            }
            //增加缓降效果
            if (player.getEffect(MobEffects.SLOW_FALLING) == null) {
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 19, 0, true, false, false));
            }
        }
    }

    public static void RainCloudSoulRenderHnadler(SlotContext slotContext, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light) {
        if (slotContext.entity() instanceof LocalPlayer player && CurioUtils.isEquipped(player, Souls.RainCloudSoul.get()) && player.getPersistentData().getFloat("RainCloudSoul") < 20) {
            BakedModel model = RenderUtils.getItemBakedModel(ProjectileItems.RedUmbrella.get());
            ItemStack stack = ProjectileItems.RedUmbrella.get().getDefaultInstance();
            Minecraft minecraft = Minecraft.getInstance();
            poseStack.pushPose();
            Quaternionf playerRotation = new Quaternionf();
            poseStack.last().pose().getNormalizedRotation(playerRotation);
            playerRotation.conjugate();
            poseStack.mulPose(playerRotation);
            poseStack.translate(0, 0.65, 0);
            poseStack.scale(0.7f, 0.7f, 0.7f);
            minecraft.getItemRenderer().render(
                    stack,
                    ItemDisplayContext.HEAD,
                    false,
                    poseStack,
                    renderTypeBuffer,
                    light,
                    OverlayTexture.NO_OVERLAY,
                    model
            );
            poseStack.popPose();
        }
    }
    
}
