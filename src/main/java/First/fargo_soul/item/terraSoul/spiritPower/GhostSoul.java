package First.fargo_soul.item.terraSoul.spiritPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.SpiritPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class GhostSoul extends SoulItem {

    public GhostSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            SoulAbilityData.getSoulInfo(ticker, GhostSoul.class).setMaxCooldown(12000);
            SoulAbilityData.SoulInfo soulInfo = ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GhostSoul.class);
            soulInfo.setMinStacks(-20);
            soulInfo.setMaxStacks(CurioUtils.isEquipped(ticker, SpiritPower.class) ? 200 : 100);
            AttributeUtils.condition(ticker, Attributes.MAX_HEALTH, ItemRegister.GhostSoulItem.getId(), soulInfo.getStacks() * 0.01f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE, (CurioUtils.isEquipped(ticker, GhostSoul.class) && soulInfo.getStacks() > 0) || soulInfo.getStacks() < 0);
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if (!attacker.equals(target) && CurioUtils.isEquipped(attacker, GhostSoul.class)) {
                attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GhostSoul.class).addStacks();
                target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GhostSoul.class).shrinkStacks();
            }
        }
    }

    @Override
    public void death(LivingDeathEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GhostSoul.class);
            if (CurioUtils.isEquipped(target, GhostSoul.class) && soulInfo.isReady()) {
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                target.heal(target.getMaxHealth() * 0.25f);
                event.setCanceled(true);
                target.getActiveEffects().removeIf(mobEffectInstance -> mobEffectInstance.getEffect().value().getCategory().equals(MobEffectCategory.HARMFUL));
                List<LivingEntity> livingEntityList = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(3), livingEntity -> target instanceof Player ? livingEntity instanceof Enemy : !(livingEntity instanceof Monster));
                for (LivingEntity livingEntity : livingEntityList) {
                    SoulUtils.attack(target, livingEntity, DamageTypes.MAGIC, target.getMaxHealth());
                }
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, GhostSoul.class, SoulRenderType.Cooldown);
        soulRenderManager.add(this, GhostSoul.class, SoulRenderType.Stack);
    }

}
