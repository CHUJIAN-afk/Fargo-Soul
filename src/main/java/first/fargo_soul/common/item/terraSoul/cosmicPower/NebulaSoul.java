package first.fargo_soul.common.item.terraSoul.cosmicPower;

import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
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

public class NebulaSoul extends SoulItem {

    public NebulaSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(Player player) {
        Info soulInfo = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.NEBULA_SOUL_INFO);
        if (soulInfo != null) {
            if (player.tickCount % 20 == 0) {
                player.heal(soulInfo.nebulaPower * 0.1f);
                soulInfo.nebulaPower -= soulInfo.nebulaPower * 0.1f;
            }
            if (soulInfo.nebulaPower <= 0.01) {
                soulInfo.setRemove(true);
            }
        }
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        Info soulInfo = SoulInfoData.getSoulInfo(attacker, FargoSoulSoulInfoRegister.NEBULA_SOUL_INFO);
        if (soulInfo == null) {
            soulInfo = new Info();
            SoulInfoData.putSoulInfo(attacker, soulInfo);
        }
        modifiers.add(new ValueModifier(attacker.getMaxHealth() * 0.04f, ValueOperation.ADD_VALUE));
        soulInfo.nebulaPower += container.getOriginalDamage() * 0.15f;
    }

    public static final class Info extends SoulInfo {

        public float nebulaPower = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.NEBULA_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (!SoulItemData.isEquipped(living, FargoSoulItemRegister.NebulaSoulItem.get())) {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putFloat("nebulaPower", nebulaPower);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            nebulaPower = tag.getFloat("nebulaPower");
        }
    }
}
