package First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class LavaSoul extends SoulItem {
    public static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    public LavaSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.ORANGE));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.lava_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.lava_soul.attribute.2").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.lava_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );


    @Override
    public List<Component> getAttributeList() {
        return AttributeList;
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }

    public static void LavaSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.LavaSoul.get()) && player.tickCount % 20 == 0) {
            List<LivingEntity> livingEntityList = player.serverLevel().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(5));
            livingEntityList.removeIf(livingEntity -> CurioUtils.isEquipped(livingEntity, SoulsRegister.LavaSoul.get()));
            for (LivingEntity livingEntity : livingEntityList) {
                livingEntity.setRemainingFireTicks(Math.min(livingEntity.getRemainingFireTicks() + 40, 80));
            }
        }
    }

    public static void LavaSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            List<Player> playerList = livingEntity.level().getEntitiesOfClass(Player.class, livingEntity.getBoundingBox().inflate(5), player -> CurioUtils.isEquipped(player, SoulsRegister.LavaSoul.get()));
            if (!playerList.isEmpty()) {
                event.setAmount(event.getAmount() * (1 + (0.2f * playerList.size())));
            }
        }
    }


}
