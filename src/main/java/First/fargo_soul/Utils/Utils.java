package First.fargo_soul.Utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class Utils {
    public static final Random random = new Random();


    public static BlockHitResult getTargetedBlock(Player player, double maxDistance) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getLookAngle();
        Vec3 endPos = eyePos.add(lookVec.scale(maxDistance));
        ClipContext clipContext = new ClipContext(eyePos, endPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
        return player.level().clip(clipContext);
    }

}
