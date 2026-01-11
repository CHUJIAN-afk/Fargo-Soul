package First.fargo_soul.item.terraSoul.forestPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.ForestPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.RenderUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;

import java.util.List;

public class PineWoodSoul extends SoulItem {

    public PineWoodSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, PineWoodSoul.class)) {
                Level level = ticker.level();
                SoulAbilityData.SoulInfo soulInfo = ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PineWoodSoul.class);
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
    public List<Component> getGuiTooltip(Player player) {
        List<Component> tooltip = super.getGuiTooltip(player);
        tooltip.add(RenderUtils.createCooldownTooltip(this, "雪球冷却", player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PineWoodSoul.class)));
        return tooltip;
    }

}
