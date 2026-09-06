package first.fargo_soul.common.item.terraSoul.willPower;

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

public class RedRidingSoul extends SoulItem {

    public RedRidingSoul(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> map = HashMultimap.create();
        Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.RED_RIDING_SOUL_INFO);
        int layers = info != null ? info.layers : 0;
        map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(FargoSoul.rl("red_riding_speed"), layers * 0.01f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return map;
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (target.getArmorValue() > 0) {
            modifiers.add(new ValueModifier(0.4f, ValueOperation.ADD_MULTIPLIED_BASE));
        }
        Info info = SoulInfoData.getSoulInfo(attacker, FargoSoulSoulInfoRegister.RED_RIDING_SOUL_INFO);
        if (info == null) {
            info = new Info();
            SoulInfoData.putSoulInfo(attacker, info);
        }
        info.layers = Math.min(10, info.layers + 1);
    }

    @Override
    public void hurt(@Nullable Entity attacker, @NotNull Player target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        Info info = SoulInfoData.getSoulInfo(target, FargoSoulSoulInfoRegister.RED_RIDING_SOUL_INFO);
        if (info != null) {
            info.layers = 0;
        }
    }

    @Override
    public void sprintClient(Player player, List<ValueModifier> modifiers) {
        Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.RED_RIDING_SOUL_INFO);
        if (info != null && info.layers >= 10) {
            modifiers.add(new ValueModifier(0.5f, ValueOperation.ADD_MULTIPLIED_BASE));
        }
    }

    public static final class Info extends SoulInfo {

        public int layers = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.RED_RIDING_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (!SoulItemData.isEquipped(living, FargoSoulItemRegister.RedRidingSoulItem.get())) {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putInt("layers", layers);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            layers = tag.getInt("layers");
        }
    }
}