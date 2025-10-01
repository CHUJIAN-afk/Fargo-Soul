package First.fargo_soul.Item.Soul.TerraSoul.ForestPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.ForestPower.ForestPower;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class PineWoodSoul extends SoulItem {

    public PineWoodSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, PineWoodSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PineWoodSoul.class);
                    soulInfo.maxCooldown = SoulUtils.isEquipped(attacker, ForestPower.class) ? 20 : 40;
                    Level level = attacker.level();
                    if (soulInfo.cooldown == 0 && SoulUtils.getSoulTarget(attacker, 10) instanceof LivingEntity target) {
                        soulInfo.cooldown = soulInfo.maxCooldown;
                        double x = attacker.getRandomX(2);
                        double y = attacker.getRandomY() + 2;
                        double z = attacker.getRandomZ(2);
                        Snowball snowball = new Snowball(level, x, y, z);
                        Vec3 toMonster = target.getHitbox().getCenter().subtract(x, y, z).normalize();
                        snowball.shoot(toMonster.x, toMonster.y, toMonster.z, 2.0f, 1.0f);
                        snowball.setOwner(attacker);
                        level.addFreshEntity(snowball);
                        ParticleUtils.spawnParticleSphere(
                                (ServerLevel) level,
                                x,
                                y,
                                z,
                                ParticleTypes.ITEM_SNOWBALL,
                                0.2f,
                                10,
                                0.5f
                        );
                        SoulUtils.playSound(
                                level,
                                snowball.position(),
                                SoundEvents.SNOWBALL_THROW,
                                SoundSource.PLAYERS
                        );
                    }
                }
            }
        }

    }

}
