package First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class MushroomSoul extends SoulItem {

    public MushroomSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void MushroomSoulUseItemFinishHandler(LivingEntityUseItemEvent.Finish event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (event.getItem().is(Items.MUSHROOM_STEW) && SoulUtils.isEquipped(attacker, MushroomSoul.class)) {
                    float healAmount = 4 + (attacker.getMaxHealth() - attacker.getHealth()) * 0.1f;
                    attacker.heal(healAmount);
                    attacker.addEffect(new MobEffectInstance(EffectRegister.FungalEmpowerment, 219));
                }
            }
        }

        @SubscribeEvent
        public static void MushroomSoulDamageHandler(LivingDeathEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, MushroomSoul.class)) {
                    target.spawnAtLocation(target.getRandom().nextBoolean() ? Items.BROWN_MUSHROOM : Items.RED_MUSHROOM);
                    ParticleUtils.spawnParticleSphere(
                            (ServerLevel) target.level(),
                            target.getBoundingBox().getCenter(),
                            ParticleTypes.WARPED_SPORE,
                            1.0f,
                            200,
                            0.5f
                    );
                }
            }
        }

    }

}