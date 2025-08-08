package First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class RainCloudSoul extends SoulItem {

    public RainCloudSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIGHT_PURPLE));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.rain_cloud_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.rain_cloud_soul.attribute.2").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.rain_cloud_soul.attribute.3").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.rain_cloud_soul.attribute.4").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.rain_cloud_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );


    @Override
    public List<Component> getAttributeList() {
        return AttributeList;
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

    public static void RainCloudSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player && CurioUtils.isEquipped(player, SoulsRegister.RainCloudSoul.get())) {
            //反射
            float RainCloudSoul = player.getPersistentData().getFloat("RainCloudSoul");
            if (event.getSource().getDirectEntity() instanceof Projectile projectile && RainCloudSoul < 20) {
                if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                    projectile.setOwner(player);
                    Vec3 toMonster = livingEntity.getBoundingBox().getCenter().subtract(projectile.position()).normalize();
                    projectile.shoot(toMonster.x, toMonster.y, toMonster.z, 10f, 0.0f);
                } else {
                    Vec3 deltaMovement = projectile.getDeltaMovement();
                    projectile.setDeltaMovement(-deltaMovement.x() * 3, -deltaMovement.y() * 3, -deltaMovement.z() * 3);
                }
                event.setCanceled(true);
                float damage = event.getAmount();
                player.getPersistentData().putFloat("RainCloudSoul", RainCloudSoul + damage);
            }
            //免疫雷击
            if (player instanceof ServerPlayer && event.getSource().is(DamageTypeTags.IS_LIGHTNING)) {
                event.setCanceled(true);
            }
        }
    }

    public static void RainCloudSoulTickHnadler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof Player player && CurioUtils.isEquipped(player, SoulsRegister.RainCloudSoul.get())) {
            //恢复雨伞
            if (player.tickCount % 1200 == 0) {
                player.getPersistentData().remove("RainCloudSoul");
            }
            //增加缓降效果
            if (player.getEffect(MobEffects.SLOW_FALLING) == null) {
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 19, 0, true, false, false));
            }
        }
    }

}
