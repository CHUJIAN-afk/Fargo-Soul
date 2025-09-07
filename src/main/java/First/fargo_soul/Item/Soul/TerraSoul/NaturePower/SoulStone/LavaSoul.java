package First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;


public class LavaSoul extends SoulItem {
    public LavaSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.ORANGE));
    }


    public static void LavaSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.LavaSoul.get()) && player.tickCount % 20 == 0) {
            List<LivingEntity> livingEntityList = player.serverLevel().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(5));
            livingEntityList.removeIf(livingEntity -> CurioUtils.isEquipped(livingEntity, SoulsRegister.LavaSoul.get()));
            for (LivingEntity livingEntity : livingEntityList) {
                livingEntity.setRemainingFireTicks(Math.min(livingEntity.getRemainingFireTicks() + 40, 80));
            }
        }
    }

    public static void LavaSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            List<Player> playerList = livingEntity.level().getEntitiesOfClass(Player.class, livingEntity.getBoundingBox().inflate(5), player -> CurioUtils.isEquipped(player, SoulsRegister.LavaSoul.get()));
            if (!playerList.isEmpty()) {
                event.setAmount(event.getAmount() * (1 + (0.2f * playerList.size())));
            }
        }
    }


}
