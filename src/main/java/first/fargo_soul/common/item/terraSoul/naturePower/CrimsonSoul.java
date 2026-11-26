package first.fargo_soul.common.item.terraSoul.naturePower;

import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.common.entity.TrackingBlood;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import first.lyra.api.LyraHelper;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.core.HolderLookup;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.nbt.CompoundTag;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.util.RandomSource;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.damagesource.DamageSource;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.LivingEntity;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.player.Player;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CrimsonSoul extends SoulItem {

    public CrimsonSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(Player player) {
        if (player.tickCount % 20 == 0) {
            List<LivingEntity> enemies = SoulTargetCache.get(player).getEntitiesInRadius(player.getBoundingBox().getCenter(), 4, null);
            float amount = player.getMaxHealth() * (enemies.isEmpty() ? 0.03f : 0.01f);
            player.heal(amount);
        }
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        Info info = SoulInfoData.getSoulInfo(target, FargoSoulSoulInfoRegister.CRIMSON_SOUL_INFO);
        if (info == null) {
            info = new Info();
            SoulInfoData.putSoulInfo(target, info);
        }
        if (info.cooldown <= 0) {
            info.cooldown = 5;
            Vec3 from = target.getBoundingBox().getCenter();
            RandomSource random = attacker.getRandom();
            int count = 2 + random.nextInt(3);
            for (int i = 0; i < count; i++) {
                TrackingBlood blood = new TrackingBlood();
                blood.init(new PathNode(from, 0, 0, 0));
                blood.setVelocity(from.offsetRandom(random, 1).subtract(from).normalize().scale(1.5f));
                blood.setOwner(attacker);
                LyraHelper.get(attacker).add(blood);
            }
        }
    }

    @Override
    public void kill(Player player, LivingEntity target, DamageSource source) {
        player.heal(target.getMaxHealth() * 0.2f);
    }

    public static final class Info extends SoulInfo {

        public int cooldown = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.CRIMSON_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (--cooldown <= 0) {
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
