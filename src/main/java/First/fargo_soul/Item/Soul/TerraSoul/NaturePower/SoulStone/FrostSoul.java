package First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone;

import First.fargo_soul.Entity.Arrow.IceSpike;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class FrostSoul extends SoulItem {

    public FrostSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.frost_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.frost_soul.attribute.2").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.frost_soul.attribute.3").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.frost_soul.attribute.4").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.frost_soul.attribute.5").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.frost_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );


    @Override
    public List<Component> getAttributeList() {
        return AttributeList;
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

    public static void FrostSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof Player player && CurioUtils.isEquipped(player, SoulsRegister.FrostSoul.get())) {
            if (player.tickCount % 20 == 0) {
                int FrostSoul = player.getPersistentData().getInt("FrostSoul");
                player.getPersistentData().putInt("FrostSoul", Math.min(FrostSoul + 1, 10));
            }
            if (player.tickCount % 5 == 0) {
                int FrostSoul = player.getPersistentData().getInt("FrostSoul");
                TargetingConditions conditions = TargetingConditions.forCombat().range(30.0);
                LivingEntity target = player.level().getNearestEntity(LivingEntity.class, conditions, player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().inflate(10));
                if (FrostSoul > 0 && target != null) {
                    player.getPersistentData().putInt("FrostSoul", Math.max(FrostSoul - 1, 0));
                    if (player instanceof ServerPlayer) {
                        double x = player.getRandomX(2);
                        double y = player.getY() + 1;
                        double z = player.getRandomZ(2);
                        IceSpike iceSpike = new IceSpike(player.level(), x, y, z);
                        Vec3 toMonster = target.getBoundingBox().getCenter().subtract(x, y, z).normalize();
                        iceSpike.shoot(toMonster.x, toMonster.y, toMonster.z, 3.0f, 1.0f);
                        iceSpike.setOwner(player);
                        player.level().addFreshEntity(iceSpike);
                    }
                }
            }
        }
    }


}
