package First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone;

import First.fargo_soul.Entity.Arrow.Bone;
import First.fargo_soul.Entity.EntityRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class NecromancerSoul extends SoulItem {

    public NecromancerSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.ORANGE));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.necromancer_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.necromancer_soul.attribute.2").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.necromancer_soul.attribute.3").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.necromancer_soul.attribute.4").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.necromancer_soul.attribute.5").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.necromancer_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );


    @Override
    public List<Component> getAttributeList() {
        return AttributeList;
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

    public static void NecromancerSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.NecromancerSoul.get())) {
            if (event.getEntity() instanceof LivingEntity livingEntity && Utils.random.nextDouble() < 0.05) {
                ServerLevel level = player.serverLevel();
                for (int i = 0; i < Math.min((int) event.getAmount(), 50); i++) {
                    Bone bone = new Bone(EntityRegister.Bone.get(), level);
                    bone.setOwner(player);
                    bone.setBaseDamage(Math.min(event.getAmount() * 0.25f, 10));
                    double theta = level.random.nextDouble() * Math.PI * 2;
                    double phi = Math.acos(2 * level.random.nextDouble() - 1);
                    double r = 0.5 + level.random.nextDouble() * 0.3;
                    Vec3 offset = new Vec3(r * Math.sin(phi) * Math.cos(theta), r * Math.sin(phi) * Math.sin(theta), r * Math.cos(phi));
                    Vec3 spawnPos = livingEntity.position().add(offset);
                    Vec3 velocity = offset.normalize().scale(0.8);
                    bone.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, livingEntity.getYRot(), livingEntity.getXRot());
                    bone.shoot(velocity.x, velocity.y, velocity.z, 0.6f, 6.0f);
                    level.addFreshEntity(bone);
                }
            }
        }
    }

    public static void NecromancerSoulPickupHandler(ItemEntityPickupEvent.Post event) {
        if (event.getPlayer() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.NecromancerSoul.get())) {
            if (event.getItemEntity().getItem().is(Items.BONE)) {
                long DarkArtistSoul = player.getPersistentData().getLong("NecromancerSoul");
                if (DarkArtistSoul < player.serverLevel().getGameTime()) {
                    player.getPersistentData().putLong("NecromancerSoul", player.serverLevel().getGameTime() + 200);
                    float heal = (player.getMaxHealth() - player.getHealth()) * 0.35f;
                    player.heal(heal);
                }
            }
        }
    }

    public static void NecromancerSoulDeathHandler(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.NecromancerSoul.get())) {
            if (event.getEntity() instanceof Skeleton skeleton) {
                if (skeleton.getType() == EntityType.SKELETON) {
                    skeleton.spawnAtLocation(Items.SKELETON_SKULL);
                } else if (skeleton.getType() == EntityType.WITHER_SKELETON) {
                    skeleton.spawnAtLocation(Items.WITHER_SKELETON_SKULL);
                }
            }
        }
    }



}