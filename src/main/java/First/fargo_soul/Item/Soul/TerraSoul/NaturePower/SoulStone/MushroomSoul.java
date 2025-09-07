package First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class MushroomSoul extends SoulItem {

    public MushroomSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }


    public static void MushroomSoulUseItemFinishHandler(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.MushroomSoul.get())) {
            if (event.getItem().is(Items.MUSHROOM_STEW)) {
                player.addEffect(new MobEffectInstance(EffectRegister.FungalEmpowerment, 400));
            }
        }
    }

    public static void MushroomSoulDamageHandler(LivingIncomingDamageEvent event){
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.MushroomSoul.get())) {
            if (event.getEntity() instanceof LivingEntity livingEntity && !event.getSource().isDirect()) {
                List<LivingEntity> livingEntityList = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(1));
                livingEntityList.add(livingEntity);
                for (LivingEntity livingEntity1 : livingEntityList) {
                    livingEntity1.hurt(player.damageSources().magic(), 0.5f);
                    livingEntity1.invulnerableTime = 0;
                }
                ParticleUtils.spawnParticleSphere(
                        player.serverLevel(),
                        livingEntity.getBoundingBox().getCenter(),
                        ParticleTypes.WARPED_SPORE,
                        1.0f,
                        200,
                        0.5f
                );
            }
        }
    }





}