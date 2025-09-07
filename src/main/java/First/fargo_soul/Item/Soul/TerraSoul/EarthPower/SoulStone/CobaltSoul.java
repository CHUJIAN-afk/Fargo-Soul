package First.fargo_soul.Item.Soul.TerraSoul.EarthPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;


public class CobaltSoul extends SoulItem {

    public CobaltSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIGHT_RED));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                SoulsRegister.AncientCobaltSoul.get()
        );
    }

    public static void CobaltSoulJumpHandler(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.CobaltSoul.get()) && player.onGround()) {
            if (player.invulnerableTime < 14) {
                player.invulnerableTime += 4;
            }
        }
    }


}
