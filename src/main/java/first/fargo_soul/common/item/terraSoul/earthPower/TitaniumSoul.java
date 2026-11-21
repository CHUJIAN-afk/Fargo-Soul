package first.fargo_soul.common.item.terraSoul.earthPower;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TitaniumSoul extends SoulItem {

    public TitaniumSoul(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> map = HashMultimap.create();
        Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.TITANIUM_SOUL_INFO);
        if (info != null && info.guard > 300) {
            map.put(Attributes.MAX_HEALTH, new AttributeModifier(FargoSoul.rl("titanium_health"), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            map.put(Attributes.ARMOR, new AttributeModifier(FargoSoul.rl("titanium_armor"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
        return map;
    }

    @Override
    public void tick(Player player) {
        Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.TITANIUM_SOUL_INFO);
        if (info == null) {
            info = new Info();
            SoulInfoData.putSoulInfo(player, info);
        }
        if (info.guard > 0) {
            info.guard = Math.max(0, info.guard - 0.25f);
        }
    }

    @Override
    public void hurt(@Nullable Entity attacker, @NotNull Player target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        modifiers.add(new ValueModifier(-0.15f, ValueOperation.ADD_MULTIPLIED_TOTAL));
        Info info = SoulInfoData.getSoulInfo(target, FargoSoulSoulInfoRegister.TITANIUM_SOUL_INFO);
        if (info == null) {
            info = new Info();
            SoulInfoData.putSoulInfo(target, info);
        }
        info.guard = Math.min(info.maxGuard, info.guard + container.getOriginalDamage() * 0.2f);
        float ratio = info.guard / info.maxGuard;
        float reduce = 0.35f * ratio;
        if (target.getHealth() < target.getMaxHealth() * 0.5f) {
            reduce *= 2;
        }
        if (reduce > 0) {
            modifiers.add(new ValueModifier(-reduce, ValueOperation.ADD_MULTIPLIED_TOTAL));
        }
    }

    public static final class Info extends SoulInfo {

        public float guard = 0;
        public float maxGuard = 600;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.TITANIUM_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (!SoulItemData.isEquipped(living, FargoSoulItemRegister.TitaniumSoulItem.get())) {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putFloat("guard", guard);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            guard = tag.getFloat("guard");
        }
    }
}