package first.fargo_soul.common.item.terraSoul.terraPower;

import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TinSoul extends SoulItem {

    public TinSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void criticalHit(Player player, LivingEntity target, List<ValueModifier> modifiers) {
        Info soulInfo = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.TIN_SOUL_INFO);
        if (soulInfo == null) {
            soulInfo = new Info();
            SoulInfoData.putSoulInfo(player, soulInfo);
        }
        modifiers.add(new ValueModifier(0.2f + soulInfo.extraCritDamage * 0.01f, ValueOperation.ADD_MULTIPLIED_BASE));
        soulInfo.extraCritDamage = Math.min(soulInfo.extraCritDamage + 10, soulInfo.maxExtraCritDamage);
    }

    @Override
    public void hurt(@Nullable Entity attacker, @NotNull Player target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        Info soulInfo = SoulInfoData.getSoulInfo(target, FargoSoulSoulInfoRegister.TIN_SOUL_INFO);
        if (soulInfo != null) {
            soulInfo.extraCritDamage = soulInfo.extraCritDamage / 2;
        }
    }

    public static final class Info extends SoulInfo {

        public float extraCritDamage = 0;
        public float maxExtraCritDamage = 80;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.TIN_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (!SoulItemData.isEquipped(living, FargoSoulItemRegister.TinSoulItem.get())) {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putFloat("extraCritDamage", extraCritDamage);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            extraCritDamage = tag.getFloat("extraCritDamage");
        }
    }
}
