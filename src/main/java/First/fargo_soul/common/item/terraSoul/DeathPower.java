package First.fargo_soul.common.item.terraSoul;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.entity.projectile.Bone;
import First.fargo_soul.common.event.modEvent.SprintEvent;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.register.EffectRegister;
import First.fargo_soul.register.EntityRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class DeathPower extends SoulItem {

    public DeathPower(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                AncientShadowSoulItem.get(),
                CrystalAssassinSoulItem.get(),
                DarkArtistSoulItem.get(),
                GloomySoulItem.get(),
                NecromancerSoulItem.get(),
                NinjaSoulItem.get(),
                PenetratingNinjaSoulItem.get()
        );
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide() && CurioUtils.isEquipped(ticker, DeathPower.class)) {
            SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(ticker, DeathPower.class);
            soulInfo.setMaxCooldown(80);
            if (soulInfo.getDuration() > 0) {
                List<LivingEntity> list = ticker.level().getEntitiesOfClass(LivingEntity.class, ticker.getBoundingBox().inflate(0.5), living -> living != ticker);
                for (LivingEntity living : list) {
                    SoulAbilityData.getSoulInfo(living, "deathMark" + ticker.getStringUUID()).setDuration(100);
                }
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, DeathPower.class)) {
                if (SoulAbilityData.getSoulInfo(target, DeathPower.class).getDuration() > 0) {
                    event.setCanceled(true);
                }
            }
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, DeathPower.class)) {
                if (SoulAbilityData.getSoulInfo(target, "deathMark" + attacker.getStringUUID()).getDuration() > 0) {
                    event.setAmount(event.getAmount() * 1.5f);
                }
                target.addEffect(new MobEffectInstance(EffectRegister.ShadowFire, 100));
            }
        }
    }

    @Override
    public void sprintServer(SprintEvent.Server event) {
        Player player = event.getEntity();
        if (CurioUtils.isEquipped(player, DeathPower.class)) {
            SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(player, DeathPower.class);
            if (soulInfo.isReady()) {
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                soulInfo.setDuration(10);
                Level level = player.level();
                for (int i = 0; i < 20; i++) {
                    Bone bone = new Bone(EntityRegister.BoneEntity.get(), level);
                    SoulUtils.randomShoot(player, bone, player);
                    SoulUtils.setAbilityInvulnerable(bone);
                    bone.addDeltaMovement(player.getLookAngle());
                }
                SoulUtils.playSound(level, player.position(), SoundEvents.SKELETON_DEATH, player.getSoundSource());
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, DeathPower.class, SoulRenderType.Cooldown);
        soulRenderManager.add(this, DeathPower.class, SoulRenderType.Duration);
    }

}
