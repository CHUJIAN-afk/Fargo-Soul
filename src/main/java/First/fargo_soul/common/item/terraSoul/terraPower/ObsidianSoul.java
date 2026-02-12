package First.fargo_soul.common.item.terraSoul.terraPower;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.TerraPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.AttributeUtils;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;


public class ObsidianSoul extends SoulItem {

    public ObsidianSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            ResourceLocation location = ItemRegister.ObsidianSoulItem.getId();
            int exAmount = 0;
            if (ticker.getAttribute(Attributes.ARMOR) instanceof AttributeInstance instance) {
                if (instance.getModifier(location) instanceof AttributeModifier modifier) {
                    exAmount = (int) modifier.amount();
                }
            }
            int amount = Math.min(100, 8 - (ticker.getArmorValue() - exAmount));
            AttributeUtils.condition(
                    ticker,
                    Attributes.ARMOR,
                    location,
                    amount,
                    AttributeModifier.Operation.ADD_VALUE,
                    CurioUtils.isEquipped(ticker, ObsidianSoul.class) && amount > 0
            );
            if (CurioUtils.isEquipped(ticker, ObsidianSoul.class)) {
                SoulAbilityData.SoulInfo SoulInfo = ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(ObsidianSoul.class);
                SoulInfo.setMaxCooldown(CurioUtils.isEquipped(ticker, TerraPower.class) ? 30 : 60);
                if (SoulInfo.getCooldown() == 0 && SoulUtils.getSoulTarget(ticker, 10) instanceof LivingEntity target) {
                    SoulInfo.setCooldown(SoulInfo.getMaxCooldown());
                    Level level = ticker.level();
                    SmallFireball fireball = new SmallFireball(EntityType.SMALL_FIREBALL, level);
                    SoulUtils.shootTargetFromAttaker(fireball, ticker, target, 1, CurioUtils.isEquipped(ticker, TerraPower.class) ? 1.3 : 1.0);
                    SoulUtils.setAbilityInvulnerable(fireball);
                    ParticleUtils.spawnParticleSphere(
                            (ServerLevel) level,
                            fireball.position(),
                            ParticleTypes.LAVA,
                            0.2f,
                            5,
                            0.2f
                    );
                    SoulUtils.playSound(
                            level,
                            fireball.position(),
                            SoundEvents.DRAGON_FIREBALL_EXPLODE,
                            SoundSource.PLAYERS
                    );
                }
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if ((event.getSource().is(DamageTypes.LAVA) || event.getSource().is(DamageTypeTags.IS_FIRE)) && CurioUtils.isEquipped(target, ObsidianSoul.class)) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, ObsidianSoul.class, SoulRenderType.Cooldown);
    }

}