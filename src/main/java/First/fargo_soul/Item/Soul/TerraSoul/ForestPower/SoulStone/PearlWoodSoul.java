package First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.CustomUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.PearlWoodSoul;

public class PearlWoodSoul extends SoulItem {

    public PearlWoodSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.ORANGE));
    }


    public static void PearlWoodDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, PearlWoodSoul.get())) {
            if (event.getEntity() instanceof LivingEntity livingEntity && CustomUtils.random.nextDouble() < 0.1) {
                event.setAmount(event.getAmount() * 1.5f);
                List<LivingEntity> monsterList = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(10), monster -> !monster.equals(livingEntity));
                monsterList.removeIf(livingEntity1 -> livingEntity1.equals(player));
                if (!monsterList.isEmpty()) {
                    LivingEntity monster = monsterList.get(CustomUtils.random.nextInt(monsterList.size()));
                    monster.hurt(player.damageSources().magic(), event.getAmount());
                    monster.invulnerableTime = 0;
                    ParticleUtils.spawnParticleLine(
                            player.serverLevel(),
                            livingEntity.getEyePosition(),
                            monster.getEyePosition(),
                            ParticleTypes.ENCHANT,
                            5,
                            0.1f
                    );
                    player.heal(player.getMaxHealth() * 0.05f);
                }
            }
        }
    }
}
