package first.fargo_soul.common.item.base;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import first.fargo_soul.common.dataComponents.SoulRarity;
import first.fargo_soul.register.FargoSoulDataComponentsRegister;
import first.lyra.common.entity.IEntityCollision;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.Input;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.*;

public abstract class SoulItem extends Item implements ICurioItem {

    public SoulItem(Properties properties) {
        super(properties);
    }

    public SoulRarity getSoulRarity(ItemStack itemStack) {
        return itemStack.getOrDefault(FargoSoulDataComponentsRegister.SOUL_RARITY.get(), SoulRarity.Empty);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack itemStack) {
        Component name = super.getName(itemStack);
        SoulRarity soulRarity = getSoulRarity(itemStack);
        if (!soulRarity.isEmpty()) {
            return name.copy().withColor(soulRarity.getColor());
        }
        return name;
    }

    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(Player player) {
        return HashMultimap.create();
    }

    public void criticalHit(Player player, LivingEntity target, List<ValueModifier> modifiers) {
    }

    public void pickup(Player player, ItemStack itemStack) {
    }

    public void shieldBlock(Player player, DamageSource source, float blockedDamage) {
    }

    public void getMaxFlyTime(Player player, List<ValueModifier> modifiers) {
    }

    public boolean renderLavaFog(Player player){
        return true;
    }

    public boolean renderFireOverlay(Player player){
        return true;
    }

    public void updateSpecialPrices(Player player, Villager villager, List<ValueModifier> modifiers) {
    }

    public void eat(Player player, ItemStack food) {
    }

    public List<Component> getTooltip(ItemStack itemStack, TooltipFlag flags) {
        List<Component> list = new ArrayList<>();
        list.add(Component.translatable("fargo_soul.key.tooltip", Component.literal("Shift").withStyle(flags.hasShiftDown() ? ChatFormatting.WHITE : ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(this);
        String key = "item." + location.getNamespace() + "." + location.getPath() + ".tooltip.";
        if (flags.hasShiftDown()) {
            list.add(Component.empty());
            int color = getSoulRarity(itemStack).getColor();
            for (int i = 1; I18n.exists(key + i); i++) {
                list.add(Component.translatable(key + i).withColor(color));
            }
            Set<SoulItem> soulItemList = getSoulItemList();
            for (SoulItem soulItem : soulItemList) {
                list.add(Component.empty());
                ResourceLocation location1 = BuiltInRegistries.ITEM.getKey(soulItem);
                String key1 = "item." + location1.getNamespace() + "." + location1.getPath() + ".tooltip.";
                int color1 = getSoulRarity(soulItem.getDefaultInstance()).getColor();
                for (int i = 1; I18n.exists(key1 + i); i++) {
                    list.add(Component.translatable(key1 + i).withColor(color1));
                }
            }
        } else {
            for (int i = -1; I18n.exists(key + i); i--) {
                list.add(Component.translatable(key + i).withStyle(ChatFormatting.GRAY));
            }
        }
        return list;
    }

    public Set<SoulItem> getSoulItemList() {
        return new HashSet<>();
    }

    public void tick(Player living){
    }

    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
    }

    public void hurt(@Nullable Entity attacker, @NotNull Player target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
    }

    public boolean death(Player player, DamageSource source, boolean canceled) {
        return true;
    }

    public void kill(Player attacker, LivingEntity target, DamageSource source) {
    }

    public void movement(Player player, Input input) {
    }

    public boolean canSprint(Player player) {
        return false;
    }

    public void sprintCollision(Player player, List<IEntityCollision.HitContext> list, Set<LivingEntity> hits) {
    }

    public void sprintServer(Player player) {
    }


    public void sprintClient(Player player, List<ValueModifier> modifiers) {
    }

    public @Nullable ResourceLocation keyPressed(Player player, int key) {
        return null;
    }

    public void keyHandle(Player player, ResourceLocation location) {
    }

    public int fly(Player player, int flyTime) {
        return flyTime;
    }

    public List<ItemStack> dropFromLootTableAfter(Player attacker, LivingEntity target) {
        return new ArrayList<>();
    }

    public float dropFromLootTableScale(Player attacker, LivingEntity target, float scale) {
        return scale;
    }

    public boolean effectApplicable(Player player, MobEffectInstance effectInstance) {
        return true;
    }
}