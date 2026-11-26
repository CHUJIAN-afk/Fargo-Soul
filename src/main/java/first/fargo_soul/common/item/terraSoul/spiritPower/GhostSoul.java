package first.fargo_soul.common.item.terraSoul.spiritPower;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.common.entity.GhostOrb;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import first.lyra.api.LyraHelper;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.core.Holder;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.core.HolderLookup;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.nbt.CompoundTag;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.util.RandomSource;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.damagesource.DamageSource;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.effect.MobEffectCategory;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.effect.MobEffectInstance;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.LivingEntity;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.ai.attributes.Attribute;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.ai.attributes.Attributes;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.player.Player;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GhostSoul extends SoulItem {

    public GhostSoul(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(Player player) {
        Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.GHOST_SOUL_INFO);
        if (info != null && info.soul > 0) {
            return ImmutableMultimap.of(Attributes.MAX_HEALTH, new AttributeModifier(FargoSoul.rl("ghost_soul"), Math.min(info.soul, 100) * 0.02f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
        return ImmutableMultimap.of();
    }

    @Override
    public void tick(Player player) {
        Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.GHOST_SOUL_INFO);
        if (info != null && info.reviveCd > 0) {
            info.reviveCd--;
        }
        if (info != null && info.soul > 100 && player.tickCount % 20 == 0) {
            float overflow = info.soul - 100;
            info.soul = 100;
            Vec3 from = player.getBoundingBox().getCenter();
            List<LivingEntity> targets = SoulTargetCache.get(player).getEntitiesInRadius(from, 16, null);
            if (!targets.isEmpty()) {
                RandomSource random = player.getRandom();
                LivingEntity chase = targets.get(random.nextInt(targets.size()));
                GhostOrb orb = new GhostOrb();
                orb.init(new PathNode(from, 0, 0, 0));
                orb.setVelocity(from.offsetRandom(random, 1).subtract(from).normalize().scale(0.5f));
                orb.setDamage(overflow * player.getMaxHealth() * 0.04f);
                orb.setChaseTarget(chase);
                orb.setOwner(player);
                LyraHelper.get(player).add(orb);
            }
        }
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        Info info = SoulInfoData.getSoulInfo(attacker, FargoSoulSoulInfoRegister.GHOST_SOUL_INFO);
        if (info == null) {
            info = new Info();
            SoulInfoData.putSoulInfo(attacker, info);
        }
        info.soul++;
    }

    @Override
    public boolean death(Player player, DamageSource source, boolean canceled) {
        Info info = SoulInfoData.getSoulInfo(player, FargoSoulSoulInfoRegister.GHOST_SOUL_INFO);
        if (!canceled && info != null && info.soul >= 100 && info.reviveCd <= 0) {
            info.soul = 0;
            info.reviveCd = 12000;
            player.setHealth(player.getMaxHealth() * 0.25f);
            for (MobEffectInstance effect : List.copyOf(player.getActiveEffects())) {
                if (effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                    player.removeEffect(effect.getEffect());
                }
            }
            return false;
        }
        return super.death(player, source, canceled);
    }

    public static final class Info extends SoulInfo {

        public float soul = 0;
        public int reviveCd = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.GHOST_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (!SoulItemData.isEquipped(living, FargoSoulItemRegister.GhostSoulItem.get())) {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putFloat("soul", soul);
            tag.putInt("reviveCd", reviveCd);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            soul = tag.getFloat("soul");
            reviveCd = tag.getInt("reviveCd");
        }
    }
}
