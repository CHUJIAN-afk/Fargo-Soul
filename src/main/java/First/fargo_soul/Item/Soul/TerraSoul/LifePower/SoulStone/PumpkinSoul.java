package First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import First.fargo_soul.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.PumpkinBlock;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class PumpkinSoul extends SoulItem {
    public PumpkinSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.BLUE));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.pumpkin_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.pumpkin_soul.attribute.2").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.pumpkin_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );


    @Override
    public List<Component> getAttributeList() {
        return AttributeList;
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }


    public static void PumpkinSoulTickHandler1(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.PumpkinSoul.get())) {
            ServerLevel level = player.serverLevel();
            BlockPos position = player.blockPosition();
            if (level.getBlockState(position.below()).getBlock() instanceof FarmBlock && level.getBlockState(position).isAir()) {
                level.setBlockAndUpdate(position, Blocks.PUMPKIN_STEM.defaultBlockState());
            }
        }
    }

    public static void PumpkinSoulTickHandler2(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.PumpkinSoul.get())) {
            ServerLevel level = player.serverLevel();
            BlockPos position = player.blockPosition();
            if (level.getBlockState(position.below()).getBlock() instanceof PumpkinBlock) {
                level.setBlockAndUpdate(position, Blocks.AIR.defaultBlockState());
                player.heal(5);
                ParticleUtils.spawnParticleSphere(
                        level,
                        player.position(),
                        ParticleTypes.EXPLOSION,
                        3,
                        10,
                        0.2f
                );
                level.playSound(
                        null,
                        player.getX(), player.getY(), player.getZ(),
                        SoundEvents.GENERIC_EXPLODE,
                        SoundSource.PLAYERS,
                        1.0f,
                        Utils.random.nextFloat() * 0.4f + 0.4f
                );
            }
        }
    }




}
