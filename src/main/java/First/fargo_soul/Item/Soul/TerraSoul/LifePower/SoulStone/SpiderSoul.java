package First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone;

import First.fargo_soul.Attribute.AttributeRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.CustomUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class SpiderSoul extends SoulItem {
    public SpiderSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIGHT_PURPLE));
    }


    public static void SpiderSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof TamableAnimal animal) {
            if (animal.getOwner() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.SpiderSoul.get())) {
                if (CustomUtils.random.nextDouble() < 0.25) {
                    float damage = event.getAmount();
                    event.setAmount(damage + Math.min(damage * 0.5f, 100));
                }
            }
        }
    }

    public static void SpiderSoulTickHnadler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ResourceLocation resourceLocation = SoulsRegister.AdamantiteSoul.getId();
            if (CurioUtils.isEquipped(player, SoulsRegister.SpiderSoul.get())) {
                AttributeUtils.addAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
            } else {
                AttributeUtils.removeAttributeModifier(player, AttributeRegister.CriticalChance, resourceLocation);
            }
        }
    }




}
