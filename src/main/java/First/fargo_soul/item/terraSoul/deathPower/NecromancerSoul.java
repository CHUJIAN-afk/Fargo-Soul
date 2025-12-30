package First.fargo_soul.item.terraSoul.deathPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.entity.projectile.Bone;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.DeathPower;
import First.fargo_soul.register.EntityRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class NecromancerSoul extends SoulItem {

    public NecromancerSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.ORANGE));
    }


    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && CurioUtils.isEquipped(target, NecromancerSoul.class)) {
                    Level level = target.level();
                    if (target.getRandom().nextDouble() < 0.02) {
                        int chance = CurioUtils.isEquipped(attacker, DeathPower.class) ? 24 : 12;
                        for (int i = 0; i < chance; i++) {
                            Bone bone = new Bone(EntityRegister.Bone.get(), level);
                            SoulUtils.randomShoot(target, bone, target);
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Death(LivingDeathEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (CurioUtils.isEquipped(attacker, NecromancerSoul.class)) {
                    if (event.getEntity() instanceof LivingEntity target && target.getType().is(EntityTypeTags.UNDEAD)) {
                        float maxHealth = target.getMaxHealth();
                        float healAmount = CurioUtils.isEquipped(attacker, DeathPower.class) ? maxHealth * 0.35f : maxHealth * 0.2f;
                        attacker.heal(healAmount);
                        if (target.getType().equals(EntityType.SKELETON)) {
                            target.spawnAtLocation(Items.SKELETON_SKULL);
                        }
                        if (target.getType().equals(EntityType.WITHER_SKELETON)) {
                            target.spawnAtLocation(Items.WITHER_SKELETON_SKULL);
                        }
                    }
                }
            }
        }

    }

}