package First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

import static First.fargo_soul.Item.Soul.SoulsRegister.ObsidianSoul;


public class ObsidianSoul extends SoulItem {

    public ObsidianSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.ORANGE));
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                SoulsRegister.AshWoodSoul.get()
        );
    }


    public static void ObsidianSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, ObsidianSoul.get())) {
            DamageSource source = event.getSource();
            if (source.is(DamageTypeTags.IS_FIRE)) {
                event.setCanceled(true);
            }
        }
    }


}