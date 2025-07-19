package First.fargo_soul.Item.Soul.TerraPower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

import static First.fargo_soul.Item.Soul.Souls.SilverSoul;
import static First.fargo_soul.Utils.MathUtils.random;

public class SilverSoul extends SoulItem {

    public SilverSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("举盾状态会增加10点防御力").withStyle(ChatFormatting.BLUE),
            Component.literal("盾牌格挡如果时机正确，反弹200%当次伤害并获得惊人一刻增益").withStyle(ChatFormatting.BLUE),
            Component.literal("完美格挡后盾牌进入0.5秒冷却").withStyle(ChatFormatting.BLUE),
            Component.literal("惊人一刻使下次近战攻击伤害提高至500%").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“反射”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void SilverSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SilverSoul.get()) && player.getAttribute(Attributes.ARMOR) instanceof AttributeInstance attributeInstance) {
            ResourceLocation resourceLocation = SilverSoul.getId();
            if (player.isBlocking()) {
                player.getPersistentData().putInt("SilverSoul", player.getPersistentData().getInt("SilverSoul") + 1);
                if (attributeInstance.getModifier(resourceLocation) == null) {
                    AttributeModifier modifier = new AttributeModifier(
                            resourceLocation,
                            10,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attributeInstance.addPermanentModifier(modifier);
                }
            } else {
                player.getPersistentData().remove("SilverSoul");
                attributeInstance.removeModifier(resourceLocation);
            }
        }
    }

    public static void SilverSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SilverSoul.get()) && event.getEntity() instanceof LivingEntity) {
            if (player.getEffect(EffectRegister.AmazingMoment) != null && event.getSource().isDirect()) {
                player.removeEffect(EffectRegister.AmazingMoment);
                event.setAmount(event.getAmount() * 5.0f);
            }
        }
    }

    public static void SilverSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SilverSoul.get()) && event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            int SilverSoul = player.getPersistentData().getInt("SilverSoul");
            if (player.isBlocking() && SilverSoul > 0 && SilverSoul < 20) {
                player.getCooldowns().addCooldown(player.getUseItem().getItem(), 10);
                player.getPersistentData().remove("SilverSoul");
                livingEntity.hurt(player.damageSources().playerAttack(player), event.getAmount() * 2.0f);
                player.addEffect(new MobEffectInstance(EffectRegister.AmazingMoment, 100, 0));
                player.serverLevel().playSound(
                        null,
                        player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ANVIL_PLACE,
                        SoundSource.PLAYERS,
                        1.0f,
                        random.nextFloat() * 0.4f + 0.4f
                );
                ParticleUtils.spawnParticleLine(
                        player.serverLevel(),
                        player.getBoundingBox().getCenter(),
                        livingEntity.getBoundingBox().getCenter(),
                        ParticleTypes.CRIT,
                        20,
                        0.1f
                );
                ParticleUtils.spawnParticleSphere(
                        player.serverLevel(),
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        ParticleTypes.CRIT,
                        1,
                        40,
                        0.1f
                );
            }
        }
    }




}
