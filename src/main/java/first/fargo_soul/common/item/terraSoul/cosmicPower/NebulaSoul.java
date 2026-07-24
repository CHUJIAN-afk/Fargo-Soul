package first.fargo_soul.common.item.terraSoul.cosmicPower;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.api.SoulInfoHelper;
import first.fargo_soul.api.TargetHelper;
import first.fargo_soul.common.attachment.InvincibleData;
import first.fargo_soul.common.attachment.soulInfoData.soulInfo.CoolDownSoulInfo;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.register.SoulInfoRegister;
import first.fargo_soul.utils.ParticleUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

public class NebulaSoul extends SoulItem {

    public NebulaSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity ticker = slotContext.entity();
        Level level = ticker.level();
        if (!level.isClientSide()) {
            SoulInfoHelper helper = SoulInfoHelper.get(ticker);
            CoolDownSoulInfo coolDownSoulInfo = helper.getInfo(FargoSoul.rl("nebula_soul_shooter"), SoulInfoRegister.COOLDOWN);
            if (coolDownSoulInfo == null){
                coolDownSoulInfo = new CoolDownSoulInfo();
                coolDownSoulInfo.setCooldown(3 * 20);
                helper.putInfo(FargoSoul.rl("nebula_soul_shooter"), coolDownSoulInfo);
                LivingEntity target = TargetHelper.get(ticker)
                        .getTargetList(16)
                        .stream()
                        .findAny()
                        .orElse(null);
                if (target != null) {
                    AttributeInstance instance = target.getAttribute(Attributes.ATTACK_DAMAGE);
                    if (instance != null) {
                        float damage = (float) (instance.getValue() * 2);
                        DamageSources damageSources = ticker.damageSources();
                        InvincibleData.attack(target)
                                .attacker(target.getUUID())
                                .damageSource(damageSources.magic())
                                .damageAmount(damage)
                                .apply();
                        AABB box = target.getBoundingBox();
                        ParticleUtils.spawnMovingParticleLine((ServerLevel) level, new Vec3(target.getRandomX(256), target.getY() + level.getMaxBuildHeight(), target.getRandomZ(256)), box.getCenter(), ParticleTypes.DRAGON_BREATH, 100, 0.25f, 0, 5, 50);
                        SoulUtils.playSound(level, target.position(), SoundEvents.EVOKER_CAST_SPELL, ticker.getSoundSource());
                    }
                }
            }
        }
    }
}
