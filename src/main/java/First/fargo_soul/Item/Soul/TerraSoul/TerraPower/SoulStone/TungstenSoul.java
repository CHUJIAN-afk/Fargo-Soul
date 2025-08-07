package First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.TungstenSoul;

public class TungstenSoul extends SoulItem {

    public TungstenSoul(Item.Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.BLUE));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.tungsten_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.tungsten_soul.attribute.2").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.tungsten_soul.attribute.3").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.tungsten_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void TungstenSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, TungstenSoul.get()) && event.getEntity() instanceof LivingEntity livingEntity) {
            long TungstenSoul = player.getPersistentData().getLong("TungstenSoul");
            long tickCount = player.level().getGameTime();
            if (TungstenSoul < tickCount) {
                player.getPersistentData().putLong("TungstenSoul", tickCount + 50);
                List<LivingEntity> livingEntityList = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(3));
                livingEntityList.removeIf(livingEntity1 -> livingEntity1.equals(player));
                for (LivingEntity entity : livingEntityList) {
                    entity.hurt(player.damageSources().playerAttack(player), event.getAmount() * 0.5f);
                    entity.invulnerableTime = 0;
                }
                ServerLevel level = player.serverLevel();
                ParticleUtils.spawnParticleSphere(
                        level,
                        livingEntity.getX(),
                        livingEntity.getBoundingBox().getCenter().y(),
                        livingEntity.getZ(),
                        ParticleTypes.EXPLOSION,
                        (float) 3,
                        (int) (event.getAmount() * 0.5f),
                        0.2f
                );
                level.playSound(
                        null,
                        livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                        SoundEvents.GENERIC_EXPLODE,
                        SoundSource.PLAYERS,
                        1.0f,
                        Utils.random.nextFloat() * 0.4f + 0.4f
                );
            }
        }
    }

    public static void TungstenSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            Holder<Attribute> entityInteractionRange = Attributes.ENTITY_INTERACTION_RANGE;
            ResourceLocation resourceLocation = TungstenSoul.getId();
            AttributeModifier.Operation addMultipliedBase = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
            boolean equipped = CurioUtils.isEquipped(player, TungstenSoul.get());
            AttributeUtils.ConditionAttributeModifier(player, entityInteractionRange, resourceLocation, 0.5, addMultipliedBase, equipped);
        }
    }










}
