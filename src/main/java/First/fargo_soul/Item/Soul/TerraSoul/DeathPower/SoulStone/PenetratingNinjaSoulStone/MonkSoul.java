package First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone.PenetratingNinjaSoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone.PenetratingNinjaSoul;
import First.fargo_soul.Network.Packet.MonkSoulPacket;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.KeyUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;


public class MonkSoul extends SoulItem {

    public MonkSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }


    public static void MonkSoulMovementTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.MonkSoul.get())) {
            if (player.getPersistentData().getBoolean("MonkSoulDamage")) {
                List<LivingEntity> livingEntityList = player.serverLevel().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(1));
                for (LivingEntity livingEntity : livingEntityList) {
                    livingEntity.hurt(player.damageSources().playerAttack(player), player.getMaxHealth() * 0.5f);
                    livingEntity.knockback(5, player.getX(), player.getZ());
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void MonkSoulMovementInputHandler(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player && CurioUtils.isEquipped(player, SoulsRegister.MonkSoul.get())) {
            long MonkSoul = player.getPersistentData().getLong("MonkSoul");
            long GamaTime = player.level().getGameTime();
            if (MonkSoul < GamaTime) {
                if (KeyUtils.isDoubleTappingForward(event.getInput())) {
                    player.getPersistentData().putLong("MonkSoul", GamaTime + 400);
                    PacketDistributor.sendToServer(new MonkSoulPacket());
                    PenetratingNinjaSoul.PenetratingNinjaHandler(player);
                    Vec3 viewVector = player.getLookAngle().scale(4.0);
                    player.addDeltaMovement(viewVector);
                }
            }
        }
    }

}
