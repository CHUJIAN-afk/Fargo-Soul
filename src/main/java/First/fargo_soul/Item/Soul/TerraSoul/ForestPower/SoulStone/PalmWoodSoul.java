package First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import static First.fargo_soul.Item.Soul.SoulsRegister.PalmWoodSoul;

public class PalmWoodSoul extends SoulItem {

    public PalmWoodSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }

    public static void PalmWoodDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, PalmWoodSoul.get())) {
                int remainingFireTicks = livingEntity.getRemainingFireTicks() + 40;
                remainingFireTicks = Math.min(remainingFireTicks, 600);
                livingEntity.setRemainingFireTicks(remainingFireTicks);
                livingEntity.getPersistentData().putLong("PalmWoodSoul", player.server.getTickCount() + 100);
            }
            if (event.getSource().is(DamageTypes.ON_FIRE)) {
                if (livingEntity.getServer() != null && livingEntity.getPersistentData().getLong("PalmWoodSoul") > livingEntity.getServer().getTickCount()) {
                    event.setAmount(event.getAmount() * 2.0f);
                }
            }
        }
    }
}
