package First.fargo_soul.Curios.Soul.EarthPower.SoulStone;

import First.fargo_soul.Curios.SoulItem;
import First.fargo_soul.Curios.Souls;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.MathUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;
import java.util.Random;


public class AdamantiteSoul extends SoulItem {

    public AdamantiteSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("持续攻击会提高攻击速度，最高增加30%攻击速度，随时间逐渐衰减").withStyle(ChatFormatting.BLUE),
            Component.literal("攻击速度增益达到最大值时，溢出混乱粒子").withStyle(ChatFormatting.BLUE),
            Component.literal("混乱粒子会干扰附近敌人的索敌目标").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“混乱”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void AdamantiteSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.AdamantiteSoul.get()) && player.getAttribute(Attributes.ATTACK_SPEED) instanceof AttributeInstance attributeInstance) {
            int AdamantiteSoul = player.getPersistentData().getInt("AdamantiteSoul");
            player.getPersistentData().putInt("AdamantiteSoul", Math.min(AdamantiteSoul + 1, 30));
            ResourceLocation resourceLocation = Souls.AdamantiteSoul.getId();
            AttributeModifier modifier = new AttributeModifier(
                    resourceLocation,
                    AdamantiteSoul / 100d,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
            if (attributeInstance.getModifier(resourceLocation) != null) {
                attributeInstance.removeModifier(resourceLocation);
            }
            attributeInstance.addPermanentModifier(modifier);
        }
    }

    public static void AdamantiteSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && player.getAttribute(Attributes.ATTACK_SPEED) instanceof AttributeInstance attributeInstance) {
            ResourceLocation resourceLocation = Souls.AdamantiteSoul.getId();
            if (CurioUtils.isEquipped(player, Souls.AdamantiteSoul.get())) {
                int AdamantiteSoul = player.getPersistentData().getInt("AdamantiteSoul");
                Random random = MathUtils.random;
                if (AdamantiteSoul == 30) {
                    List<Monster> monsterList = player.level().getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(5));
                    monsterList.removeIf(monster -> monster.getTarget() == null);
                    for (Monster monster : monsterList) {
                        if (random.nextDouble() < 0.2) {
                            ParticleUtils.spawnParticleLine(
                                    player.serverLevel(),
                                    player.position().add((1 - random.nextDouble(2)), (1 - random.nextDouble(2)), (1 - random.nextDouble(2))),
                                    monster.position(),
                                    ParticleTypes.PORTAL,
                                    20,
                                    0.1f
                            );
                            monster.setTarget(null);
                        }
                    }
                }
                if (random.nextDouble() < 0.05) {
                    player.getPersistentData().putInt("AdamantiteSoul", Math.max(AdamantiteSoul - 1, 0));
                }
                AttributeModifier modifier = new AttributeModifier(
                        resourceLocation,
                        Math.min(AdamantiteSoul / 100d, 0.3),
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                if (attributeInstance.getModifier(resourceLocation) != null) {
                    attributeInstance.removeModifier(resourceLocation);
                }
                attributeInstance.addPermanentModifier(modifier);
            } else {
                if (attributeInstance.getModifier(resourceLocation) != null) {
                    attributeInstance.removeModifier(resourceLocation);
                }
            }
        }
    }


}
