package First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.EarthPower.EarthPower;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import java.util.List;

public class TitaniumSoul extends SoulItem {

    public TitaniumSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }


    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void CurioChangeEvent(CurioChangeEvent event) {
            SoulAbilityData.updateMaxCooldown(event.getEntity(), TitaniumSoul.class, 400);
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
                if (SoulUtils.isEquipped(target, TitaniumSoul.class)) {
                    Level level = target.level();
                    SoulAbilityData.SoulInfo soulInfo = target.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TitaniumSoul.class);
                    soulInfo.maxCooldown = 400;
                    if (event.isCanceled() && soulInfo.cooldown == 0) {
                        soulInfo.cooldown = soulInfo.maxCooldown;
                        event.setCanceled(true);
                        SoulUtils.playSound(
                                level,
                                target.position(),
                                SoundEvents.ANVIL_PLACE,
                                SoundSource.PLAYERS
                        );
                    }
                    if (SoulUtils.isEquipped(target, EarthPower.class)) {
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

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TitaniumSoul.class);
                AttributeUtils.ConditionAttributeModifier(
                        attacker,
                        Attributes.MOVEMENT_SPEED,
                        SoulsRegister.TitaniumSoul.getId(),
                        (1 - (attacker.getHealth() / attacker.getMaxHealth())) * 0.25,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        SoulUtils.isEquipped(attacker, TitaniumSoul.class) && soulInfo.cooldown != 0 && soulInfo.cooldown < soulInfo.maxCooldown
                );
            }
        }

    }

}

