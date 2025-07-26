package First.fargo_soul.Item.Soul.DeathPower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.MathUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;


public class AncientShadowSoul extends SoulItem {

    public AncientShadowSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("攻击有几率造成黑暗或失明减益").withStyle(ChatFormatting.BLUE),
            Component.literal("攻击有几率使敌人丢失目标").withStyle(ChatFormatting.BLUE),
            Component.literal("身处极黑暗的环境下，除非主动攻击，否则敌人不会发现你").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“十分古老，却非常实用”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void AncientShadowSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.AncientShadowSoul.get())) {
            if (event.getEntity() instanceof LivingEntity livingEntity && !livingEntity.equals(player)) {
                if (MathUtils.random.nextBoolean()) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200));
                }
                if (MathUtils.random.nextBoolean()) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200));
                }
                if (MathUtils.random.nextDouble() < 0.1 && livingEntity instanceof Monster monster && monster.getTarget() instanceof LivingEntity) {
                    monster.setTarget(null);
                }
            }
        }
    }

    public static void AdamantiteSoulChangeTargetHandler(LivingChangeTargetEvent event) {
        if (event.getNewAboutToBeSetTarget() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.AncientShadowSoul.get())) {
            ServerLevel level = player.serverLevel();
            if (getEnvironmentLight(player) < 1) {
                event.setCanceled(true);
            }
        }
    }

    private static int getEnvironmentLight(Player player) {
        BlockPos pos = player.blockPosition();
        Level level = player.level();
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        return Math.max(skyLight, blockLight);
    }

}
