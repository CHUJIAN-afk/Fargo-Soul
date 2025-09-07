package First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.CustomUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;


public class AncientShadowSoul extends SoulItem {

    public AncientShadowSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }


    public static void AncientShadowSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.AncientShadowSoul.get())) {
            if (event.getEntity() instanceof LivingEntity livingEntity && !livingEntity.equals(player)) {
                if (CustomUtils.random.nextBoolean()) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200));
                }
                if (CustomUtils.random.nextBoolean()) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200));
                }
                if (CustomUtils.random.nextDouble() < 0.1 && livingEntity instanceof Monster monster && monster.getTarget() instanceof LivingEntity) {
                    monster.setTarget(null);
                }
            }
        }
    }

    public static void AdamantiteSoulChangeTargetHandler(LivingChangeTargetEvent event) {
        if (event.getNewAboutToBeSetTarget() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.AncientShadowSoul.get())) {
            player.serverLevel();
            if (getEnvironmentLight(player) < 1) {
                event.setCanceled(true);
            }
        }
    }

    private static int getEnvironmentLight(Player player) {
        BlockPos pos = player.blockPosition();
        Level level = player.level();
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        return Math.max(skyLight, blockLight);
    }

}
