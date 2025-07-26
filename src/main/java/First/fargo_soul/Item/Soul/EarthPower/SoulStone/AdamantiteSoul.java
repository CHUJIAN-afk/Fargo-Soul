package First.fargo_soul.Item.Soul.EarthPower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.AttributeUtils;
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

import static First.fargo_soul.Utils.MathUtils.random;


public class AdamantiteSoul extends SoulItem {

    public AdamantiteSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("持续攻击会提高攻击速度，最高增加30%攻击速度，五秒不攻击清空").withStyle(ChatFormatting.BLUE),
            Component.literal("攻击速度增益达到最大值时，附近以你为目标的敌人有概率被混乱粒子干扰，使他们短暂丢失目标").withStyle(ChatFormatting.BLUE)
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
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.AdamantiteSoul.get())) {
            if (event.getSource().getWeaponItem() != null) {
                player.getPersistentData().putLong("AdamantiteSoulLastDamage", player.serverLevel().getGameTime() + 100);
                int AdamantiteSoul = player.getPersistentData().getInt("AdamantiteSoul");
                player.getPersistentData().putInt("AdamantiteSoul", Math.min(AdamantiteSoul + 5, 30));
                ResourceLocation resourceLocation = Souls.AdamantiteSoul.getId();
                AttributeUtils.addAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation, AdamantiteSoul / 100d, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            }
        }
    }

    public static void AdamantiteSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ResourceLocation resourceLocation = Souls.AdamantiteSoul.getId();
            if (CurioUtils.isEquipped(player, Souls.AdamantiteSoul.get())) {
                int AdamantiteSoul = player.getPersistentData().getInt("AdamantiteSoul");
                long lastDamage = player.getPersistentData().getLong("AdamantiteSoulLastDamage");
                if (lastDamage < player.serverLevel().getGameTime()) {
                    player.getPersistentData().remove("AdamantiteSoul");
                    AttributeUtils.removeAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation);
                }
                if (AdamantiteSoul == 30 && player.tickCount % 40 == 0) {
                    List<Monster> monsterList = player.level().getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(5));
                    monsterList.removeIf(monster -> !player.equals(monster.getTarget()));
                    for (Monster monster : monsterList) {
                        if (random.nextDouble() < 0.2) {
                            ParticleUtils.spawnParticleLine(
                                    player.serverLevel(),
                                    player.getBoundingBox().getCenter(),
                                    monster.position(),
                                    ParticleTypes.PORTAL,
                                    20,
                                    0.1f
                            );
                            monster.setTarget(null);
                        }
                    }
                }
            } else {
                AttributeUtils.removeAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation);
            }
        }
    }


}
