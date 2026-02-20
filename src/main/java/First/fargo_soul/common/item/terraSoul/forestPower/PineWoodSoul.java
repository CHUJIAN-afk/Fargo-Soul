package First.fargo_soul.common.item.terraSoul.forestPower;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.ForestPower;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;

public class PineWoodSoul extends SoulItem {

    public PineWoodSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, PineWoodSoul.class)) {
                Level level = ticker.level();
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, PineWoodSoul.class);
                soulInfo.setMaxCooldown(CurioUtils.isEquipped(ticker, ForestPower.class) ? 20 : 40);
                if (soulInfo.isReady() && SoulUtils.getSoulTarget(ticker, 10) instanceof LivingEntity target) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    Snowball snowball = new Snowball(EntityType.SNOWBALL, level);
                    SoulUtils.shootTargetFromAttaker(snowball, ticker, target, 1, 2);
                    SoulUtils.setAbilityInvulnerable(snowball);
                    ParticleUtils.spawnParticleSphere(
                            (ServerLevel) level,
                            snowball.position(),
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

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, PineWoodSoul.class, SoulRenderType.Cooldown);
    }

}
