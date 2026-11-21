package first.fargo_soul.common.item.terraSoul.forestPower;

import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.common.entity.SnowBall;
import first.fargo_soul.common.item.base.SoulItem;
import first.lyra.api.LyraHelper;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.LivingEntity;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.entity.player.Player;
import first.lyra.common.attachmentEntity.PathNode;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PineWoodSoul extends SoulItem {

    public PineWoodSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(Player player) {
        if (player.tickCount % 100 == 0) {
            List<LivingEntity> targets = SoulTargetCache.get(player).getEntitiesInRadius(player.getBoundingBox().getCenter(), 12, null);
            if (!targets.isEmpty()) {
                Vec3 pos = player.position().add(0, player.getBoundingBox().getYsize(), 0).offsetRandom(player.getRandom(), 4);
                LivingEntity target = targets.get(player.getRandom().nextInt(targets.size()));
                Vec3 dir = target.getBoundingBox().getCenter().subtract(pos).normalize();
                SnowBall ball = new SnowBall();
                ball.init(new PathNode(pos, dir));
                ball.setVelocity(dir.scale(2));
                ball.setDamage(6);
                ball.setOwner(player);
                LyraHelper.get(player).add(ball);
            }
        }
    }
}
