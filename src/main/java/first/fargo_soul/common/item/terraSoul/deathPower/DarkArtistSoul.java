package first.fargo_soul.common.item.terraSoul.deathPower;

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
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DarkArtistSoul extends SoulItem {

    public DarkArtistSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (target.hasEffect(FargoSoulMobEffectRegister.ShadowFire)) {
            modifiers.add(new ValueModifier(0.4f, ValueOperation.ADD_MULTIPLIED_BASE));
            Info soulInfo = SoulInfoData.getSoulInfo(attacker, FargoSoulSoulInfoRegister.DARK_ARTIST_SOUL_INFO);
            if (soulInfo == null) {
                soulInfo = new Info();
                SoulInfoData.putSoulInfo(attacker, soulInfo);
            }
            soulInfo.darkPower = Math.min(soulInfo.darkPower + container.getOriginalDamage() * 0.2f, soulInfo.maxDarkPower);
            if (soulInfo.darkPower >= soulInfo.maxDarkPower){
                soulInfo.darkPower = 0;
                attacker.addEffect(new MobEffectInstance(FargoSoulMobEffectRegister.ShadowGift, 400));
            }
        }
    }

    @Override
    public boolean effectApplicable(Player player, MobEffectInstance effectInstance) {
        if (player.hasEffect(FargoSoulMobEffectRegister.ShadowGift) && effectInstance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL && player.getRandom().nextFloat() < 0.2f) {
            player.heal(player.getMaxHealth() * 0.12f);
            return false;
        }
        return super.effectApplicable(player, effectInstance);
    }

    public static final class Info extends SoulInfo {

        public float darkPower = 0;
        public float maxDarkPower = 400;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.DARK_ARTIST_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (!SoulItemData.isEquipped(living, FargoSoulItemRegister.DarkArtistSoulItem.get())) {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putFloat("darkPower", darkPower);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            darkPower = tag.getFloat("darkPower");
        }
    }
}
