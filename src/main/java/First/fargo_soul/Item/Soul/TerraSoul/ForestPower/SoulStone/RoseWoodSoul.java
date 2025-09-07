package First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import static First.fargo_soul.Item.Soul.SoulsRegister.RosewoodSoul;

public class RoseWoodSoul extends SoulItem {

    public RoseWoodSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }


    public static void RosewoodSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && !player.onGround() && CurioUtils.isEquipped(player, RosewoodSoul.get())) {
            event.setAmount(event.getAmount() * 0.9f);
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                livingEntity.hurt(player.damageSources().magic(), event.getAmount() * 0.5f);
                livingEntity.invulnerableTime = 0;
                Vec3 delta = player.position().subtract(livingEntity.position()).normalize();
                livingEntity.addDeltaMovement(delta);
            }
        }
    }
}
