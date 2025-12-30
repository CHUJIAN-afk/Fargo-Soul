package First.fargo_soul.item.terraSoul.willPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.WillPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.EffectRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.register.KeyRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.RenderUtils;
import First.fargo_soul.utils.SoulUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import java.util.List;

public class GoldSoul extends SoulItem {

    public GoldSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void render(RenderLivingEvent.Post<?, ?> event) {
            LivingEntity attacker = event.getEntity();
            if (CurioUtils.isEquipped(attacker, GoldSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GoldSoul.class);
                if (soulInfo.getDuration() > 0) {
                    MultiBufferSource multiBufferSource = event.getMultiBufferSource();
                    PoseStack poseStack = event.getPoseStack();
                    float partialTick = event.getPartialTick();
                    Minecraft minecraft = Minecraft.getInstance();
                    ItemRenderer itemRenderer = minecraft.getItemRenderer();
                    float ageInTicks = RenderUtils.getAgeInTicks(attacker, partialTick, 1f);

                    float radius = (float) attacker.getBoundingBox().getYsize()*0.5f;
					int blockCount = 250;

                    // 计算倾斜角度周期循环 (30°到60°之间)
                    float tiltAngle = (float) (45f + 7.5f * Math.sin(ageInTicks * 0.05f));

                    poseStack.pushPose();
                    poseStack.mulPose(Axis.YP.rotationDegrees(-Mth.lerp(partialTick, attacker.yHeadRotO, attacker.yHeadRot) + 90));

                    // 渲染第一道环 (倾斜45度)
                    for (int i = 0; i < blockCount; i++) {
                        poseStack.pushPose();
                        float angle = ageInTicks * 0.05f + (float) (Math.PI * 2 / blockCount * i);

                        // 计算环上的位置 (倾斜45度)
                        float x = (float) (Math.cos(angle) * radius);
                        float y = (float) (Math.sin(angle) * radius * Math.sin(Math.toRadians(tiltAngle)));
                        float z = (float) (Math.sin(angle) * radius * Math.cos(Math.toRadians(tiltAngle)));

                        poseStack.translate(x, y + attacker.getBoundingBox().getYsize() * 0.5, z);
                        poseStack.mulPose(Axis.YP.rotationDegrees(angle * 180 / (float) Math.PI));
                        poseStack.scale(0.1f, 0.1f, 0.1f);

                        itemRenderer.renderStatic(
                                Blocks.GOLD_BLOCK.asItem().getDefaultInstance(),
                                ItemDisplayContext.FIXED,
                                LightTexture.FULL_BRIGHT,
                                OverlayTexture.NO_OVERLAY,
                                poseStack,
                                multiBufferSource,
                                attacker.level(),
                                0
                        );
                        poseStack.popPose();
                    }

                    // 渲染第二道环 (倾斜-45度)
                    for (int i = 0; i < blockCount; i++) {
                        poseStack.pushPose();
                        float angle = ageInTicks * 0.05f + (float) (Math.PI * 2 / blockCount * i);

                        // 计算环上的位置 (倾斜-45度)
                        float x = (float) (Math.cos(angle) * radius);
                        float y = (float) (Math.sin(angle) * radius * Math.sin(Math.toRadians(-tiltAngle)));
                        float z = (float) (Math.sin(angle) * radius * Math.cos(Math.toRadians(-tiltAngle)));

                        poseStack.translate(x, y + attacker.getBoundingBox().getYsize() * 0.5, z);
                        poseStack.mulPose(Axis.YP.rotationDegrees(angle * 180 / (float) Math.PI));
                        poseStack.scale(0.1f, 0.1f, 0.1f);

                        itemRenderer.renderStatic(
                                Blocks.GOLD_BLOCK.asItem().getDefaultInstance(),
                                ItemDisplayContext.FIXED,
                                LightTexture.FULL_BRIGHT,
                                OverlayTexture.NO_OVERLAY,
                                poseStack,
                                multiBufferSource,
                                attacker.level(),
                                0
                        );
                        poseStack.popPose();
                    }
                    poseStack.popPose();
                }
            }
        }

        @SubscribeEvent
        public static void CurioChangeEvent(CurioChangeEvent event) {
            event.getEntity().getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GoldSoul.class).setMaxCooldown(2400);
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && CurioUtils.isEquipped(target, GoldSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GoldSoul.class);
                    if (soulInfo.getDuration() > 0) {
                        float amount = event.getAmount();
                        float scale = 0.4f;
                        if (target instanceof Player player) {
                            NonNullList<ItemStack> items = player.getInventory().items;
                            for (ItemStack itemStack : items) {
                                if (itemStack.is(Items.GOLD_INGOT)) {
                                    scale += 0.001f * itemStack.getCount();
                                    if (scale > 0.95f) {
                                        scale = 0.95f;
                                        break;
                                    }
                                }
                            }
                        }
                        event.setAmount(amount * (1 - scale));
                        Level level = target.level();
                        List<LivingEntity> livingEntityList = level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(8), livingEntity -> {
                            if (attacker instanceof Player) {
                                return livingEntity instanceof Enemy;
                            } else {
                                return livingEntity instanceof Player || (livingEntity instanceof Mob mob && attacker.equals(mob.getTarget()));
                            }
                        });
                        if (!livingEntityList.isEmpty()) {
                            LivingEntity livingEntity = livingEntityList.get(SoulUtils.random.nextInt(livingEntityList.size()));
                            SoulUtils.attack(GoldSoul.class, target, target, livingEntity, DamageTypes.MAGIC, amount * scale);
                        }
                    }
                    if (CurioUtils.isEquipped(target, WillPower.class) && attacker.getEffect(EffectRegister.Midas) != null) {
                        event.setAmount(event.getAmount() * 0.85f);
                    }
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, GoldSoul.class)) {
                    target.addEffect(new MobEffectInstance(EffectRegister.Midas, 200));
                }
            }
        }

        @SubscribeEvent
        public static void Applicable(MobEffectEvent.Applicable event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (CurioUtils.isEquipped(target, GoldSoul.class) && event.getEffectInstance().is(EffectRegister.Midas)) {
                    event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
                }
            }
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void GoldSoulInputHandler(InputEvent.Key event) {
            if (KeyRegister.GoldSoulKey.consumeClick() && Minecraft.getInstance().player instanceof LocalPlayer player) {
                PacketDistributor.sendToServer(new Packet(CurioUtils.isEquipped(player, GoldSoul.class)));
            }
        }

    }

    public record Packet(boolean isEquipped) implements CustomPacketPayload {

        public static final Type<Packet> TYPE = new Type<>(ItemRegister.GoldSoulItem.getId());
        public static final StreamCodec<ByteBuf, Packet> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.BOOL,
                Packet::isEquipped,
                Packet::new
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public void handle(IPayloadContext context) {
            context.enqueueWork(() -> {
                if (isEquipped) {
                    Player player = context.player();
                    SoulAbilityData.SoulInfo soulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GoldSoul.class);
                    soulInfo.setMaxCooldown(2400);
                    if (soulInfo.getCooldown() == 0) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        soulInfo.setDuration(CurioUtils.isEquipped(player, WillPower.class) ? 140 : 100);
                        SoulUtils.playSound(
                                player.level(),
                                player.position(),
                                SoundEvents.APPLY_EFFECT_RAID_OMEN,
                                SoundSource.PLAYERS
                        );
                    }
                }
            });
        }

    }

}
