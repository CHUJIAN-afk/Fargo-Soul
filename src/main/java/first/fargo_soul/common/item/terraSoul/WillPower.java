package first.fargo_soul.common.item.terraSoul;

import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.FargoSoulMobEffectRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

import static first.fargo_soul.register.FargoSoulItemRegister.*;

public class WillPower extends SoulItem {

    public WillPower(Properties properties) {
        super(properties);
    }

    @Override
    public Set<SoulItem> getSoulItemList() {
        return Set.of(GoldSoulItem.get(), PlatinumSoulItem.get(), GladiatorSoulItem.get(), RedRidingSoulItem.get(), ValhallaKnightSoulItem.get());
    }

    @Override
    public void healAmount(Player player, float amount, List<ValueModifier> modifiers) {
        modifiers.add(new ValueModifier(0.25f, ValueOperation.ADD_MULTIPLIED_BASE));
    }

    @Override
    public void kill(Player player, LivingEntity target, DamageSource source) {
        if (player.getRandom().nextFloat() < 0.4f) {
            Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.WILL_POWER_INFO);
            if (info == null) {
                info = new Info();
                SoulInfoData.putSoulInfo(player, info);
            }
            info.dropScale = 16;
        }
    }

    @Override
    public float dropFromLootTableScale(Player attacker, LivingEntity target, float scale) {
        Info info = SoulInfoData.getSoulInfo(attacker, FargoSoulSoulInfoRegister.WILL_POWER_INFO);
        if (info != null && info.dropScale > 1) {
            scale *= info.dropScale;
            info.dropScale = 1;
        }
        return scale;
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (target.hasEffect(FargoSoulMobEffectRegister.Midas)) {
            modifiers.add(new ValueModifier(0.8f, ValueOperation.ADD_MULTIPLIED_BASE));
        }
    }

    public static final class Info extends SoulInfo {

        public float dropScale = 1;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.WILL_POWER_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (!SoulItemData.isEquipped(living, FargoSoulItemRegister.WillPowerItem.get())) {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putFloat("dropScale", dropScale);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            dropScale = tag.getFloat("dropScale");
        }
    }
}