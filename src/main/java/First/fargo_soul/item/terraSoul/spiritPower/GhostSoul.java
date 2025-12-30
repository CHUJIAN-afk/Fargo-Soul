package First.fargo_soul.item.terraSoul.spiritPower;

import First.fargo_soul.attachment.SoulAbilityData;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import java.util.List;

public class GhostSoul extends SoulItem {

    public GhostSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    @EventBusSubscriber
    public static class Event {

        @SubscribeEvent
        public static void CurioChangeEvent(CurioChangeEvent event) {
            event.getEntity().getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GhostSoul.class).setMaxCooldown(12000);
        }

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GhostSoul.class);
                soulInfo.setMinStacks(-20);
                soulInfo.setMaxStacks(CurioUtils.isEquipped(target, SpiritPower.class) ? 200 : 100);
                AttributeUtils.ConditionAttributeModifier(
                        target,
                        Attributes.MAX_HEALTH,
                        ItemRegister.GhostSoulItem.getId(),
                        soulInfo.getStacks() * 0.01f,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        (CurioUtils.isEquipped(target, GhostSoul.class) && soulInfo.getStacks() > 0) || soulInfo.getStacks() < 0
                );
            }
        }

        @SubscribeEvent
        public static void Damage(LivingDamageEvent.Post event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (!attacker.equals(target) && CurioUtils.isEquipped(target, GhostSoul.class)) {
                    attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GhostSoul.class).addStacks();
                    target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GhostSoul.class).shrinkStacks();
                }
            }
        }

        @SubscribeEvent
        public static void Death(LivingIncomingDamageEvent event) {
            if (!event.isCanceled() && event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(GhostSoul.class);
                if (CurioUtils.isEquipped(target, GhostSoul.class) && soulInfo.getCooldown() == 0 && event.getAmount() > target.getHealth()) {
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

    }

}
