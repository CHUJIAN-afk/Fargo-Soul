package First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone;

import First.fargo_soul.Entity.EntityRegister;
import First.fargo_soul.Entity.Projectile.Bone;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.DeathPower;
import First.fargo_soul.Utils.SoulUtils;
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


    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !attacker.level().isClientSide()) {
                if (!attacker.equals(target) && SoulUtils.isEquipped(target, NecromancerSoul.class)) {
                    Level level = target.level();
                    if (target.getRandom().nextDouble() < 0.02) {
                        int chance = SoulUtils.isEquipped(attacker, DeathPower.class) ? 24 : 12;
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
                if (SoulUtils.isEquipped(attacker, NecromancerSoul.class)) {
                    if (event.getEntity() instanceof LivingEntity target && target.getType().is(EntityTypeTags.UNDEAD)) {
                        float maxHealth = target.getMaxHealth();
                        float healAmount = SoulUtils.isEquipped(attacker, DeathPower.class) ? maxHealth * 0.35f : maxHealth * 0.2f;
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