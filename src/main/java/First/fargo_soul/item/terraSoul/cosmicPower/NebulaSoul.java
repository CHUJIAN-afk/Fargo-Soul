package First.fargo_soul.item.terraSoul.cosmicPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.CosmicPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.RenderUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class NebulaSoul extends SoulItem {

    public NebulaSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        Level level = ticker.level();
        if (!level.isClientSide() && CurioUtils.isEquipped(ticker, NebulaSoul.class)) {
            SoulAbilityData.SoulInfo soulInfo = ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(NebulaSoul.class);
            soulInfo.setMaxCooldown(CurioUtils.isEquipped(ticker, CosmicPower.class) ? 40 : 60);
            if (soulInfo.isReady() && SoulUtils.getSoulTarget(ticker, 20) instanceof LivingEntity target) {
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                float amount = (8 + target.getMaxHealth() * 0.02f) * (CurioUtils.isEquipped(ticker, CosmicPower.class) ? 1.5f : 1f);
                SoulUtils.attack(ticker, target, DamageTypes.MAGIC, amount);
                ticker.heal(amount);
                ParticleUtils.spawnMovingParticleLine((ServerLevel) level, new Vec3(target.getRandomX(256), level.getMaxBuildHeight(), target.getRandomZ(256)), target.getBoundingBox().getCenter(), ParticleTypes.DRAGON_BREATH, 100, 0.25f, 0, 5, 50);
                SoulUtils.playSound(level, target.position(), SoundEvents.EVOKER_CAST_SPELL, ticker.getSoundSource());
            }
        }
    }

    @Override
    public List<Component> getGuiTooltip(Player player) {
        List<Component> tooltip = new ArrayList<>();
        SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(player, NebulaSoul.class);
        tooltip.add(RenderUtils.createCooldownTooltip(this, "星云射击冷却", soulInfo));
        return tooltip;
    }

}
