package first.fargo_soul.common.item.terraSoul.cosmicPower;

import com.mojang.blaze3d.platform.InputConstants;
import first.fargo_soul.FargoSoul;
import first.fargo_soul.api.SoulInfoHelper;
import first.fargo_soul.common.attachment.soulInfoData.soulInfo.CoolDownSoulInfo;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.register.KeyRegister;
import first.fargo_soul.register.SoulInfoRegister;
import first.fargo_soul.utils.SoulUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTickRateManager;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.TimeUnit;

public class StardustSoul extends SoulItem {

    public StardustSoul(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation keyPressed(Player player, int key) {
        InputConstants.Key keyKey = KeyRegister.StardustSoulKey.getKey();
        if (key == keyKey.getValue()) {
            return FargoSoul.rl("stardust_soul");
        }
        return null;
    }

    @Override
    public void keyHandle(Player player, ResourceLocation key) {
        if (key.equals(FargoSoul.rl("stardust_soul"))) {
            SoulInfoHelper helper = SoulInfoHelper.get(player);
            CoolDownSoulInfo coolDownSoulInfo = helper.getInfo(FargoSoul.rl("stardust_soul"), SoulInfoRegister.COOLDOWN);
            if (coolDownSoulInfo == null) {
                coolDownSoulInfo = new CoolDownSoulInfo();
                coolDownSoulInfo.setCooldown(120 * 20);
                helper.putInfo(FargoSoul.rl("stardust_soul"), coolDownSoulInfo);
                if (player.getServer() instanceof MinecraftServer server) {
                    ServerTickRateManager serverTickRateManager = server.tickRateManager();
                    if (!serverTickRateManager.isFrozen()) {
                        serverTickRateManager.setFrozen(true);
                        SoulUtils.executorService.schedule(() -> serverTickRateManager.setFrozen(false), 6, TimeUnit.SECONDS);
                    }
                }
            }
        }
    }
}
