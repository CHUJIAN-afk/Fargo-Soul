package first.fargo_soul.common.item.terraSoul.earthPower;

import first.fargo_soul.client.gui.SoulGuiRenderManager;
import first.fargo_soul.client.gui.SoulRenderType;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.terraSoul.EarthPower;
import first.fargo_soul.register.FargoSoulItemRegister;
import first.fargo_soul.utils.AttributeUtils;
import first.fargo_soul.utils.CurioUtils;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class TitaniumSoul extends SoulItem {

    public TitaniumSoul(Properties properties) {
        super();
    }

    @Override
    public void tick(LivingEntity ticker, boolean isClient) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, TitaniumSoul.class);
            soulInfo.setMaxCooldown(400);
            AttributeUtils.condition(
                    ticker,
                    Attributes.MOVEMENT_SPEED,
                    FargoSoulItemRegister.TitaniumSoulItem.getId(),
                    (1 - (ticker.getHealth() / ticker.getMaxHealth())) * 0.25,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                    CurioUtils.isEquipped(ticker, TitaniumSoul.class) && !soulInfo.isReady() && soulInfo.getCooldown() < soulInfo.getMaxCooldown()
            );
        }
    }

    @Override
    public void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, TitaniumSoul.class)) {
                Level level = target.level();
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(target, TitaniumSoul.class);
                soulInfo.setMaxCooldown(400);
                if (!event.isCanceled() && soulInfo.isReady()) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    event.setCanceled(true);
                    SoulUtils.playSound(
                            level,
                            target.position(),
                            SoundEvents.ANVIL_PLACE,
                            SoundSource.PLAYERS
                    );
                }
                if (CurioUtils.isEquipped(target, EarthPower.class)) {
                    List<LivingEntity> livingEntityList = level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(2));
                    livingEntityList.remove(target);
                    for (LivingEntity entity : livingEntityList) {
                        Vec3 direction = entity.position().subtract(entity.position()).normalize();
                        entity.knockback(1.5, -direction.x, -direction.z);
                    }
                }
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, TitaniumSoul.class, SoulRenderType.Cooldown);
    }

}

