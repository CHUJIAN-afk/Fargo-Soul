package First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.AttributeUtils;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.concurrent.TimeUnit;

public class MithrilSoul extends SoulItem {

    public MithrilSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }


    public static void MithrilSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.MithrilSoul.get())) {
            long MithrilSoulLastDamage = player.getPersistentData().getLong("MithrilSoulLastDamage");
            player.getPersistentData().putLong("MithrilSoulLastDamage", player.serverLevel().getGameTime() + 100);
            if (MithrilSoulLastDamage < player.serverLevel().getGameTime()) {
                ResourceLocation resourceLocation = SoulsRegister.MithrilSoul.getId();
                AttributeUtils.addAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
                AttributeUtils.executorService.schedule(() -> AttributeUtils.removeAttributeModifier(player, Attributes.ATTACK_SPEED, resourceLocation), 3, TimeUnit.SECONDS);
            }
        }
    }

}
