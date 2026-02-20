package First.fargo_soul.common.item.terraSoul.cosmicPower;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.TerraSoul;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.CosmicPower;
import First.fargo_soul.register.KeyRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.concurrent.TimeUnit;

public class StardustSoul extends SoulItem {

    public StardustSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, StardustSoul.class)) {
                if (attacker.getServer() instanceof MinecraftServer server && server.tickRateManager().isFrozen()) {
                    event.setAmount(event.getAmount() * 3);
                }
            }
        }
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide() && CurioUtils.isEquipped(ticker, StardustSoul.class)) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(ticker, StardustSoul.class);
            soulInfo.setMaxCooldown(CurioUtils.isEquipped(ticker, TerraSoul.class) ? 1800 : 3600);
        }
    }

    @Override
    public String keyPressed(Player player, int key) {
        if (key == KeyRegister.StardustSoulKey.getKey().getValue() && CurioUtils.isEquipped(player, StardustSoul.class)) {
            return StardustSoul.class.getSimpleName();
        }
        return null;
    }

    @Override
    public void keyHandle(Player player, String key) {
        if (key.equals(StardustSoul.class.getSimpleName())) {
            SoulAbilityData.SoulInfo soulInfo = SoulUtils.getSoulInfo(player, StardustSoul.class);
            if (soulInfo.isReady() && player.getServer() instanceof MinecraftServer server && !server.tickRateManager().isFrozen()) {
                soulInfo.setCooldown(soulInfo.getMaxCooldown());
                server.tickRateManager().setFrozen(true);
                int delay = CurioUtils.isEquipped(player, CosmicPower.class) ? 10 : 6;
                soulInfo.setDuration(delay);
                SoulUtils.executorService.schedule(() -> server.tickRateManager().setFrozen(false), delay, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, StardustSoul.class, SoulRenderType.Cooldown);
        soulRenderManager.add(this, StardustSoul.class, SoulRenderType.Duration);
    }

}
