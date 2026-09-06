package first.fargo_soul.common.item.terraSoul.earthPower;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MithrilSoul extends SoulItem {

    public MithrilSoul(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> map = HashMultimap.create();
        Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.MITHRIL_SOUL_INFO);
        float bonus = info != null ? info.bonus : 0;
        map.put(Attributes.ATTACK_SPEED, new AttributeModifier(FargoSoul.rl("mithril_speed"), 0.3f + bonus, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return map;
    }

    @Override
    public void tick(Player player) {
        Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.MITHRIL_SOUL_INFO);
        if (info == null) {
            info = new Info();
            SoulInfoData.putSoulInfo(player, info);
        }
        if (info.lastAttackTick != 0 && player.tickCount - info.lastAttackTick > 100) {
            info.bonus = 0;
        }
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        Info info = SoulInfoData.getSoulInfo(attacker, FargoSoulSoulInfoRegister.MITHRIL_SOUL_INFO);
        if (info == null) {
            info = new Info();
            SoulInfoData.putSoulInfo(attacker, info);
        }
        if (info.lastAttackTick != 0 && attacker.tickCount - info.lastAttackTick > 100) {
            info.bonus = 0;
        }
        info.bonus = Math.min(0.7f, info.bonus + 0.05f);
        info.lastAttackTick = attacker.tickCount;
    }

    public static final class Info extends SoulInfo {

        public float bonus = 0;
        public int lastAttackTick = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.MITHRIL_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (!SoulItemData.isEquipped(living, FargoSoulItemRegister.MithrilSoulItem.get())) {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putFloat("bonus", bonus);
            tag.putInt("lastAttackTick", lastAttackTick);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            bonus = tag.getFloat("bonus");
            lastAttackTick = tag.getInt("lastAttackTick");
        }
    }
}