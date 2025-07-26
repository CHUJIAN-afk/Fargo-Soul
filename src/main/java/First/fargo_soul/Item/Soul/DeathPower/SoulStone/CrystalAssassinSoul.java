package First.fargo_soul.Item.Soul.DeathPower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.KeyUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CrystalAssassinSoul extends SoulItem {
    public static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public CrystalAssassinSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("获得冲刺能力").withStyle(ChatFormatting.BLUE),
            Component.literal("攻击时如果敌人没有以你为目标，则获得持续10秒的先发制人增益").withStyle(ChatFormatting.BLUE),
            Component.literal("先发制人增益会使你下次攻击必定暴击且造成150%伤害，并降低目标10点防御力持续10秒").withStyle(ChatFormatting.BLUE),
            Component.literal("先发制人效果有20秒冷却时间，冲刺能力有1秒冷却时间").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“登顶”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void CrystalAssassinSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && event.getSource().getWeaponItem() != null) {
            if (event.getEntity() instanceof Monster monster && player.equals(monster.getTarget())) {
                long CrystalAssassinSoul = player.getPersistentData().getLong("CrystalAssassinSoul");
                long gameTime = player.level().getGameTime();
                if (CrystalAssassinSoul < gameTime) {
                    player.addEffect(new MobEffectInstance(EffectRegister.PreemptiveStrike, 200));
                    player.getPersistentData().putLong("CrystalAssassinSoul", gameTime + 400);
                }

            }
            if (player.getEffect(EffectRegister.PreemptiveStrike) != null) {
                player.removeEffect(EffectRegister.PreemptiveStrike);
            }
            if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.getAttribute(Attributes.ARMOR) instanceof AttributeInstance) {
                ResourceLocation resourceLocation = Souls.CrystalAssassinSoul.getId();
                AttributeUtils.addAttributeModifier(livingEntity, Attributes.ARMOR, resourceLocation, -10, AttributeModifier.Operation.ADD_VALUE);
                executorService.schedule(() -> AttributeUtils.removeAttributeModifier(livingEntity, Attributes.ARMOR, resourceLocation), 10, TimeUnit.SECONDS);
            }
        }
    }

    public static void CrystalAssassinSoulMovementInputHandler(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof LocalPlayer player && CurioUtils.isEquipped(player, Souls.CrystalAssassinSoul.get())) {
            long CrystalAssassinSoulMovement = player.getPersistentData().getLong("CrystalAssassinSoulMovement");
            long GameTime = player.level().getGameTime();
            if (CrystalAssassinSoulMovement < GameTime) {
                if (KeyUtils.isDoubleTappingForward(event.getInput())) {
                    player.getPersistentData().putLong("CrystalAssassinSoulMovement", GameTime + 20);
                    Vec3 viewVector = player.getLookAngle().normalize().scale(1.2);
                    player.addDeltaMovement(viewVector);
                }
            }
        }
    }






}
