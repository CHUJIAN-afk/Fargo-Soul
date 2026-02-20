package First.fargo_soul.common.item.terraSoul.naturePower;

import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.NaturePower;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.RenderUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class LavaSoul extends SoulItem {

    public LavaSoul(Properties properties) {
        super(properties);
    }

    private static final List<ItemStack> renderList = IntStream.range(0, 400).mapToObj(i -> Items.MAGMA_BLOCK.getDefaultInstance()).collect(Collectors.toList());

    @Override
    public void render(RenderLivingEvent.Post<?, ?> event) {
        LivingEntity attacker = event.getEntity();
        if (CurioUtils.isEquipped(attacker, LavaSoul.class)) {
            float size = (float) attacker.getBoundingBox().getSize();
            RenderUtils.renderItemRing(
                    attacker.level(),
                    event.getPoseStack(),
                    event.getMultiBufferSource(),
                    renderList,
                    (float) (attacker.getBoundingBox().getSize() * 10),
                    size * 0.25f,
                    size * 0.75f,
                    LightTexture.FULL_BRIGHT,
                    RenderUtils.getAgeInTicks(attacker, event.getPartialTick(), 1),
                    attacker.getRandom(),
                    attacker.getId()
            );
        }
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, LavaSoul.class) && ticker.tickCount % 5 == 0) {
                double range = ticker.getBoundingBox().getSize() * 10;
                List<LivingEntity> targetList = SoulUtils.getTargetList(ticker, range);
                boolean equipped = CurioUtils.isEquipped(ticker, NaturePower.class);
                for (LivingEntity target : targetList) {
                    if (target.getRemainingFireTicks() > 0 && equipped && target.getRandom().nextDouble() < 0.2) {
                        Level level = ticker.level();
                        Vec3 center = target.getBoundingBox().getCenter();
                        level.explode(ticker, center.x(), center.y(), center.z(), 1, false, Level.ExplosionInteraction.NONE);
                    }
                    target.setRemainingFireTicks(Math.min(target.getRemainingFireTicks() + 10, 80));
                }
            }
        }
    }

}
