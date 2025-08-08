package First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Utils.Utils.random;


public class AdamantiteSoul extends SoulItem {

    public AdamantiteSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIME));
    }


    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.adamantite_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.adamantite_soul.attribute.2").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.adamantite_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );


    @Override
    public List<Component> getAttributeList() {
        return AttributeList;
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

    public static void AdamantiteSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.AdamantiteSoul.get())) {
            if (event.getSource().getWeaponItem() != null) {
                player.getPersistentData().putLong("AdamantiteSoulLastDamage", player.serverLevel().getGameTime() + 100);
                int AdamantiteSoul = player.getPersistentData().getInt("AdamantiteSoul");
                player.getPersistentData().putInt("AdamantiteSoul", Math.min(AdamantiteSoul + 5, 30));
                ResourceLocation resourceLocation = SoulsRegister.AdamantiteSoul.getId();
                AttributeUtils.addAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation, AdamantiteSoul / 100d, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            }
        }
    }

    public static void AdamantiteSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ResourceLocation resourceLocation = SoulsRegister.AdamantiteSoul.getId();
            if (CurioUtils.isEquipped(player, SoulsRegister.AdamantiteSoul.get())) {
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
