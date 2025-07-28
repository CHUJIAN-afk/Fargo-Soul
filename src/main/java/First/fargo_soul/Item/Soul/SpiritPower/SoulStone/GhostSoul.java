package First.fargo_soul.Item.Soul.SpiritPower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Entity.AbstractArrow.AbstractArrowRegister;
import First.fargo_soul.Entity.AbstractArrow.SoulProjectile.SoulProjectile;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.MathUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Collection;
import java.util.List;

public class GhostSoul extends SoulItem {

    public GhostSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.ghost_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.ghost_soul.attribute.2").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.ghost_soul.attribute.3").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.ghost_soul.attribute.4").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.ghost_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void GhostSoulDamageHnadler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.GhostSoul.get())) {
            if (event.getEntity() instanceof LivingEntity livingEntity && event.getSource().getWeaponItem() != null) {
                ServerLevel level = player.serverLevel();
                SoulProjectile soulProjectile = new SoulProjectile(AbstractArrowRegister.Soul.get(), level);
                soulProjectile.setOwner(player);
                soulProjectile.setPos(livingEntity.getRandomX(2), livingEntity.getRandomY() + 1, livingEntity.getRandomZ(2));
                level.addFreshEntity(soulProjectile);
            }
        }
        if (event.getSource().getDirectEntity() instanceof SoulProjectile soulProjectile && event.getEntity().equals(soulProjectile.getOwner())) {
            event.setCanceled(true);
        }
    }

    public static void GhostSoulTickHnadler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.GhostSoul.get())) {
            List<LivingEntity> livingEntityList = player.serverLevel().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(20), livingEntity1 -> !player.equals(livingEntity1));
            if (!livingEntityList.isEmpty()) {
                LivingEntity livingEntity = livingEntityList.get(MathUtils.random.nextInt(livingEntityList.size()));
                List<SoulProjectile> projectileList = player.serverLevel().getEntitiesOfClass(SoulProjectile.class, player.getBoundingBox().inflate(0.5), soulProjectile -> player.equals(soulProjectile.getOwner()) && soulProjectile.getBaseDamage() != 3.0);
                for (SoulProjectile soulProjectile : projectileList) {
                    Vec3 toMonster = livingEntity.getBoundingBox().getCenter().subtract(soulProjectile.position()).normalize();
                    soulProjectile.shoot(toMonster.x, toMonster.y, toMonster.z, 0.75f, 1.0f);
                    soulProjectile.setBaseDamage(3.0);
                    player.heal(2);
                }
            } else {
                player.heal(2);
            }
        }
    }
    public static void GhostSoulDeathHnadler(LivingDeathEvent event) {
        if (!event.isCanceled() && event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.GhostSoul.get())) {
            long GameTime = player.serverLevel().getGameTime();
            if (player.getPersistentData().getLong("GhostSoul") < GameTime) {
                player.getPersistentData().putLong("GhostSoul", GameTime + (600 * 20));
                event.setCanceled(true);
                player.heal(player.getMaxHealth() * 0.2f);
                Collection<MobEffectInstance> mobEffectInstances = player.getActiveEffects();
                for (MobEffectInstance mobEffectInstance : mobEffectInstances) {
                    if (mobEffectInstance.is(MobEffects.HARM)) {
                        player.removeEffect(mobEffectInstance.getEffect());
                    }
                }
                ServerLevel level = player.serverLevel();
                for (int i = 0; i < 12; i++) {
                    SoulProjectile soulProjectile = new SoulProjectile(AbstractArrowRegister.Soul.get(), level);
                    soulProjectile.setOwner(player);
                    soulProjectile.setBaseDamage(2.0);
                    soulProjectile.setPos(player.getRandomX(4), player.getRandomY() + 1, player.getRandomZ(4));
                    level.addFreshEntity(soulProjectile);
                }
                player.addEffect(new MobEffectInstance(EffectRegister.Ghost, 100));
            }
        }
    }





}
