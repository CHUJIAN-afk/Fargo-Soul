package First.fargo_soul.Item.Soul.NaturePower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class MushroomSoul extends SoulItem {

    public MushroomSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("食用蘑菇煲能恢复5生命值，并获得蘑菇力量，持续20秒，蘑菇力量可增加20%伤害和暴击率").withStyle(ChatFormatting.BLUE),
            Component.literal("弹射物在击中敌人时生成一团蘑菇孢子，造成魔法伤害，冷却时间很短").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“是用真的蘑菇做的！”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void MushroomSoulUseItemFinishHandler(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.MushroomSoul.get())) {
            if (event.getItem().is(Items.MUSHROOM_STEW)) {
                player.addEffect(new MobEffectInstance(EffectRegister.FungalEmpowerment, 400));
            }
        }
    }

    public static void MushroomSoulDamageHandler(LivingIncomingDamageEvent event){
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.MushroomSoul.get())) {
            if (event.getEntity() instanceof LivingEntity livingEntity && !event.getSource().isDirect()) {
                List<LivingEntity> livingEntityList = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(1));
                livingEntityList.add(livingEntity);
                for (LivingEntity livingEntity1 : livingEntityList) {
                    livingEntity1.hurt(player.damageSources().magic(), 0.5f);
                }
                ParticleUtils.spawnParticleSphere(
                        player.serverLevel(),
                        livingEntity.getBoundingBox().getCenter(),
                        ParticleTypes.WARPED_SPORE,
                        1.0f,
                        200,
                        0.5f
                );
            }
        }
    }





}