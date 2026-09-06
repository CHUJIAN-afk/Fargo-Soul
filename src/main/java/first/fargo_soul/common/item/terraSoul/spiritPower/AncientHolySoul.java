package first.fargo_soul.common.item.terraSoul.spiritPower;

import first.fargo_soul.common.entity.TerraBlade;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.api.LyraHelper;
import first.lyra.common.attachment.AttachmentEntityData;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class AncientHolySoul extends SoulItem {

    public AncientHolySoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(Player player) {
        List<TerraBlade> blades = LyraHelper.get(player).getEntityData().get(AttachmentEntityData.Type.ExtraMinion, SummonerAttachmentEntityRegister.TERRA_BLADE.get());
        if (blades.stream().noneMatch(terraBlade -> terraBlade.ancientHolySoul)) {
            TerraBlade blade = new TerraBlade();
            blade.ancientHolySoul = true;
            LyraHelper.get(player).add(AttachmentEntityData.Type.ExtraMinion, blade);
        }
    }
}
