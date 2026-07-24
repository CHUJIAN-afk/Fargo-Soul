package first.fargo_soul.common.item.terraSoul.cosmicPower;

import first.fargo_soul.common.item.base.SoulItem;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;

public class MeteorSoul extends SoulItem {

    public MeteorSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void movementInput(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        Input input = event.getInput();
        Vec3 deltaMovement = player.getDeltaMovement();
        double y = deltaMovement.y();
        if (y < 0 && input.shiftKeyDown) {
            if (y > -1) {
                player.setDeltaMovement(new Vec3(deltaMovement.x(), -1, deltaMovement.z()));
            }
        }
    }
}