package first.fargo_soul.common.item.terraSoul.forestPower;

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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WoodSoul extends SoulItem {

    public WoodSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void kill(Player player, LivingEntity target, DamageSource source) {
        if (target instanceof Raider) {
            Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.WOOD_SOUL_INFO);
            if (info == null) {
                info = new Info();
                SoulInfoData.putSoulInfo(player, info);
            }
            info.discount = Math.min(30, info.discount + 0.25f + player.getRandom().nextFloat() * 0.25f);
        }
    }

    @Override
    public void updateSpecialPrices(Player player, Villager villager, List<ValueModifier> modifiers) {
        Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.WOOD_SOUL_INFO);
        float extra = info != null ? info.discount * 0.01f : 0f;
        modifiers.add(new ValueModifier(-(0.2f + extra), ValueOperation.ADD_MULTIPLIED_TOTAL));
    }

    public static final class Info extends SoulInfo {

        public float discount = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.WOOD_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (!SoulItemData.isEquipped(living, FargoSoulItemRegister.WoodSoulItem.get())) {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putFloat("discount", discount);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            discount = tag.getFloat("discount");
        }
    }
}