package first.fargo_soul.common.item.terraSoul.cosmicPower;

import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.gui.SoulRenderType;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.CosmicPower;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.ParticleUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NebulaSoul extends SoulItem {

    public NebulaSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        Level level = ticker.level();
        if (!level.isClientSide() && CurioUtils.isEquipped(ticker, NebulaSoul.class)) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, NebulaSoul.class);
            soulInfo.setMaxCooldown(CurioUtils.isEquipped(ticker, CosmicPower.class) ? 40 : 60);
            if (soulInfo.isReady() && SoulUtils.getSoulTarget(ticker, 20) instanceof LivingEntity target) {
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                float amount = (8 + target.getMaxHealth() * 0.02f) * (CurioUtils.isEquipped(ticker, CosmicPower.class) ? 1.5f : 1f);
                SoulUtils.attack(this.getClass(), ticker, ticker, target, DamageTypes.MAGIC, amount);
                ticker.heal(amount);
                ParticleUtils.spawnMovingParticleLine((ServerLevel) level, new Vec3(target.getRandomX(256), level.getMaxBuildHeight(), target.getRandomZ(256)), target.getBoundingBox().getCenter(), ParticleTypes.DRAGON_BREATH, 100, 0.25f, 0, 5, 50);
                SoulUtils.playSound(level, target.position(), SoundEvents.EVOKER_CAST_SPELL, ticker.getSoundSource());
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, NebulaSoul.class, SoulRenderType.Cooldown);
    }

}
