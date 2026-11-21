package first.fargo_soul.common.item.terraSoul.cosmicPower;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.common.soulInfo.SoulInfo;
import first.fargo_soul.common.soulInfo.SoulInfoType;
import first.fargo_soul.register.FargoSoulKeyRegister;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTickRateManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StardustSoul extends SoulItem {

    private static int frozenRemaining = -1;

    public StardustSoul(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation keyPressed(Player player, int key) {
        if (key == FargoSoulKeyRegister.StardustSoulKey.getKey().getValue()) {
            return FargoSoul.rl("stardust_soul");
        }
        return super.keyPressed(player, key);
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (attacker.getServer() instanceof MinecraftServer server && server.tickRateManager().isFrozen()) {
            modifiers.add(new ValueModifier(1, ValueOperation.ADD_VALUE));
        }
    }

    @Override
    public void keyHandle(Player player, ResourceLocation key) {
        if (key.equals(FargoSoul.rl("stardust_soul")) && player.getServer() instanceof MinecraftServer server) {
            ServerTickRateManager serverTickRateManager = server.tickRateManager();
            if (!serverTickRateManager.isFrozen()) {
                Info soulInfo = new Info();
                soulInfo.cooldown = 120 * 20;
                SoulInfoData.putSoulInfo(player, soulInfo);
                serverTickRateManager.setFrozen(true);
                frozenRemaining = 6 * 20;
            }
        }
    }

    public static void serverTick(MinecraftServer server) {
        if (--frozenRemaining == 0) {
            server.tickRateManager().setFrozen(false);
        }
    }

    public static final class Info extends SoulInfo {

        public int cooldown = 0;

        @Override
        public SoulInfoType<? extends SoulInfo> getType() {
            return FargoSoulSoulInfoRegister.STARDUST_SOUL_INFO.get();
        }

        @Override
        public void tick(LivingEntity living) {
            if (--cooldown <= 0) {
                setRemove(true);
            }
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = super.serializeNBT(provider);
            tag.putInt("cooldown", cooldown);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
            cooldown = tag.getInt("cooldown");
        }
    }
}