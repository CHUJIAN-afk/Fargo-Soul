package first.fargo_soul.common.item.terraSoul.forestPower;

import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.common.entity.Star;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PearlWoodSoul extends SoulItem {

    public PearlWoodSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void criticalHit(Player player, LivingEntity target, List<ValueModifier> modifiers) {
        float bonus = player.hasEffect(FargoSoulMobEffectRegister.Starlight) ? 0.6f : 0.2f;
        modifiers.add(new ValueModifier(bonus, ValueOperation.ADD_MULTIPLIED_BASE));
    }

    @Override
    public void pickup(Player player, ItemStack itemStack) {
        Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.PEARL_WOOD_SOUL_INFO);
        if (info == null) {
            info = new Info();
            SoulInfoData.putSoulInfo(player, info);
        }
        if (info.cooldown <= 0) {
            List<LivingEntity> targets = SoulTargetCache.get(player).getEntitiesInRadius(player.getBoundingBox().getCenter(), 12, null);
            if (!targets.isEmpty()) {
                RandomSource random = player.getRandom();
                LivingEntity target = targets.get(random.nextInt(targets.size()));
                Vec3 targetPos = target.getBoundingBox().getCenter();
                Vec3 pos = targetPos.add(0, 10, 0).offsetRandom(random, 10);
                Star star = new Star(player.damageSources().playerAttack(player), pos, pos.subtract(targetPos).normalize().scale(1.5f));
                star.setDamage(8);
                star.setChaseTarget(target);
                star.join(player);
                info.cooldown = 20;
            }
        }
    }

    public static final class Info extends SoulInfo {

        public int cooldown = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.PEARL_WOOD_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (SoulItemData.isEquipped(living, FargoSoulItemRegister.PearlWoodSoulItem.get())) {
                if (cooldown > 0) {
                    cooldown--;
                }
            } else {
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