package First.fargo_soul.common.item.terraSoul.naturePower;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.NaturePower;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.RenderUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

import java.util.ArrayList;
import java.util.List;

public class FrostSoul extends SoulItem {

    public FrostSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void render(RenderLivingEvent.Post<?, ?> event) {
        LivingEntity attacker = event.getEntity();
        if (CurioUtils.isEquipped(attacker, FrostSoul.class)) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(attacker, FrostSoul.class);
            int stacks = soulInfo.getStacks();
            if (stacks > 0) {
                List<ItemStack> renderList = new ArrayList<>();
                for (int i = 0; i < stacks; i++) {
                    renderList.add(Items.SNOWBALL.getDefaultInstance());
                }
                float size = (float) attacker.getBoundingBox().getSize();
                RenderUtils.renderItemRings(
                        attacker.level(),
                        event.getPoseStack(),
                        event.getMultiBufferSource(),
                        renderList,
                        1 + (stacks / 3),
                        size * 1.5f,
                        0.5f * size,
                        size * 0.75f,
                        event.getPackedLight(),
                        RenderUtils.getAgeInTicks(attacker, event.getPartialTick(), 1),
                        attacker.getRandom(),
                        attacker.getId()
                );
            }
        }
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, FrostSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, FrostSoul.class);
                Level level = ticker.level();
                BlockPos onPos = ticker.getOnPos();
                boolean cold = level.getBiome(onPos).value().coldEnoughToSnow(onPos);
                soulInfo.setMaxCooldown(cold ? 8 : 20);
                soulInfo.setMaxStacks(CurioUtils.isEquipped(ticker, NaturePower.class) ? 20 : 10);
                if (soulInfo.isReady() && soulInfo.getStacks() < soulInfo.getMaxStacks()) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    soulInfo.addStacks();
                }
                if (ticker.tickCount % (cold ? 2 : 5) == 0 && soulInfo.getStacks() > 0) {
                    if (SoulUtils.getSoulTarget(ticker, 10) instanceof LivingEntity target) {
                        soulInfo.shrinkStacks();
                        Snowball snowball = new Snowball(EntityType.SNOWBALL, level);
                        SoulUtils.shootTargetFromAttaker(snowball, ticker, target, 0.5, 2);
                        SoulUtils.setAbilityInvulnerable(snowball);
                        ParticleUtils.spawnParticleSphere(
                                (ServerLevel) level,
                                snowball.position(),
                                ParticleTypes.ITEM_SNOWBALL,
                                0.2f,
                                10,
                                0.5f
                        );
                        SoulUtils.playSound(
                                level,
                                snowball.position(),
                                SoundEvents.SNOWBALL_THROW,
                                SoundSource.PLAYERS
                        );
                    }
                }
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, FrostSoul.class, SoulRenderType.Stack);
        soulRenderManager.add(this, FrostSoul.class, SoulRenderType.Cooldown);
    }

}
