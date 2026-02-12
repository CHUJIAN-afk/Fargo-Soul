package First.fargo_soul.common.item.terraSoul.earthPower;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.EarthPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
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
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.getSoulInfo(ticker, TitaniumSoul.class).setMaxCooldown(400);
        }
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TitaniumSoul.class);
            AttributeUtils.condition(
                    ticker,
                    Attributes.MOVEMENT_SPEED,
                    ItemRegister.TitaniumSoulItem.getId(),
                    (1 - (ticker.getHealth() / ticker.getMaxHealth())) * 0.25,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                    CurioUtils.isEquipped(ticker, TitaniumSoul.class) && !soulInfo.isReady() && soulInfo.getCooldown() < soulInfo.getMaxCooldown()
            );
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (CurioUtils.isEquipped(target, TitaniumSoul.class)) {
                Level level = target.level();
                SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TitaniumSoul.class);
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

