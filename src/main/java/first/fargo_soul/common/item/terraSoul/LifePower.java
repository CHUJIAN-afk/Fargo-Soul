package first.fargo_soul.common.item.terraSoul;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulMobEffectRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import first.lyra.register.LyraAttributeRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

import static first.fargo_soul.register.FargoSoulItemRegister.*;

public class LifePower extends SoulItem {

    public LifePower(Properties properties) {
        super(properties);
    }

    @Override
    public Set<SoulItem> getSoulItemList() {
        return Set.of(BeeSoulItem.get(), BeetleSoulItem.get(), PumpkinSoulItem.get(), SpiderSoulItem.get(), TurtleSoulItem.get());
    }

    @Override
    public boolean canFly(Player player) {
        return true;
    }

    @Override
    public void getMaxFlyTime(Player player, List<ValueModifier> modifiers) {
        modifiers.add(new ValueModifier(1.5f, ValueOperation.ADD_MULTIPLIED_BASE));
    }

    @Override
    public void eat(Player player, ItemStack food) {
        if (food.is(Items.HONEY_BOTTLE)) {
            player.addEffect(new MobEffectInstance(FargoSoulMobEffectRegister.Ambrosia, 200));
        }
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> map = HashMultimap.create();
        if (player.hasEffect(FargoSoulMobEffectRegister.Ambrosia)) {
            map.put(LyraAttributeRegister.SummonDamage, new AttributeModifier(FargoSoul.rl("life_ambrosia"), 2.0, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            map.put(LyraAttributeRegister.HealthRegen, new AttributeModifier(FargoSoul.rl("life_regen"), 5, AttributeModifier.Operation.ADD_VALUE));
        }
        return map;
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (attacker.hasEffect(FargoSoulMobEffectRegister.Ambrosia)) {
            modifiers.add(new ValueModifier(0.3f, ValueOperation.ADD_MULTIPLIED_BASE));
        }
    }

    @Override
    public void hurt(@Nullable Entity attacker, @NotNull Player target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (attacker instanceof LivingEntity living) {
            Info info = SoulInfoData.getSoulInfo(target, FargoSoulSoulInfoRegister.LIFE_POWER_INFO);
            if (info == null) {
                info = new Info();
                SoulInfoData.putSoulInfo(target, info);
                living.hurt(target.damageSources().playerAttack(target), container.getOriginalDamage() * 5.0f);
            }
        }
    }

    public static final class Info extends SoulInfo {

        public int cooldown = 2;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.LIFE_POWER_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (--cooldown < 0) {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putInt("cooldown", cooldown);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            cooldown = tag.getInt("cooldown");
        }
    }
}