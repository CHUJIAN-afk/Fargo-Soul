package First.fargo_soul.Item.Soul.LifePower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.MathUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.PumpkinBlock;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

public class PumpkinSoul extends SoulItem {
    public PumpkinSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("如果脚下可以种植南瓜，你在移动时将会种下南瓜种子").withStyle(ChatFormatting.BLUE),
            Component.literal("踩在成熟的南瓜上时会为你回复5点生命值，并使南瓜产生爆炸").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“你对南瓜的突发渴望永远不会得到满足”").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<Component> getAttributeList() {
        return this.AttributeList;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context, ItemStack stack) {
        tooltips.addAll(AttributeList);
        tooltips.addAll(TooltipList);
        return tooltips;
    }

    public static void PumpkinSoulTickHandler1(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.PumpkinSoul.get())) {
            ServerLevel level = player.serverLevel();
            BlockPos position = player.blockPosition();
            if (level.getBlockState(position.below()).getBlock() instanceof FarmBlock && level.getBlockState(position).isAir()) {
                level.setBlockAndUpdate(position, Blocks.PUMPKIN_STEM.defaultBlockState());
            }
        }
    }

    public static void PumpkinSoulTickHandler2(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.PumpkinSoul.get())) {
            ServerLevel level = player.serverLevel();
            BlockPos position = player.blockPosition();
            if (level.getBlockState(position.below()).getBlock() instanceof PumpkinBlock) {
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
                        MathUtils.random.nextFloat() * 0.4f + 0.4f
                );
            }
        }
    }




}
