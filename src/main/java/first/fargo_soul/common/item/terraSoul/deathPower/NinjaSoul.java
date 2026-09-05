package first.fargo_soul.common.item.terraSoul.deathPower;

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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NinjaSoul extends SoulItem {

    public NinjaSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(Player player) {
        Info soulInfo = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.NINJA_SOUL_INFO);
        if (soulInfo == null) {
            soulInfo = new Info();
            SoulInfoData.putSoulInfo(player, soulInfo);
        }
        soulInfo.ambushPower = Math.min(soulInfo.ambushPower + 1, soulInfo.maxAmbushPower);
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        Info soulInfo = SoulInfoData.getSoulInfo(attacker, FargoSoulSoulInfoRegister.NINJA_SOUL_INFO);
        if (soulInfo != null) {
            modifiers.add(new ValueModifier(soulInfo.ambushPower * 0.01f, ValueOperation.ADD_MULTIPLIED_BASE));
            soulInfo.ambushPower = 0;
        }
    }

    public static final class Info extends SoulInfo {

        public float ambushPower = 0;
        public float maxAmbushPower = 600;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.NINJA_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (!SoulItemData.isEquipped(living, FargoSoulItemRegister.NinjaSoulItem.get())) {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putFloat("ambushPower", ambushPower);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            ambushPower = tag.getFloat("ambushPower");
        }
    }
}
