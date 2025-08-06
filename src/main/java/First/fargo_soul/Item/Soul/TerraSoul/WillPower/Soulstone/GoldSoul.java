package First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone;

import First.fargo_soul.Client.KeyBinding;
import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Network.Packet.GoldSoulPacket;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static First.fargo_soul.Utils.Utils.random;

public class GoldSoul extends SoulItem {

    public GoldSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.PINK));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.gold_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.gold_soul.attribute.2").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.gold_soul.attribute.3").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.gold_soul.attribute.4").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.gold_soul.attribute.5").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.gold_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
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

    @OnlyIn(Dist.CLIENT)
    public static void GoldSoulInputHandler(InputEvent.Key event) {
        if (KeyBinding.GoldSoulKey.consumeClick() && Minecraft.getInstance().player instanceof LocalPlayer player && CurioUtils.isEquipped(player, SoulsRegister.GoldSoul.get())) {
            long GoldSoul = player.getPersistentData().getLong("GoldSoul");
            long gameTime = player.level().getGameTime();
            if (GoldSoul < gameTime) {
                PacketDistributor.sendToServer(new GoldSoulPacket());
                player.getPersistentData().putLong("GoldSoul", gameTime + 1100);
                if (!player.getPersistentData().getBoolean("GoldSoulDamage")) {
                    player.getPersistentData().putBoolean("GoldSoulDamage", true);
                    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                    executorService.schedule(() -> player.getPersistentData().remove("GoldSoulDamage"), 5, TimeUnit.SECONDS);
                }
            }
        }
    }

    public static void GoldSoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.GoldSoul.get()) && event.getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(EffectRegister.Midas, 200));
            if (player.getPersistentData().getBoolean("GoldSoulDamage")) {
                event.setCanceled(true);
            }
        }
    }

    public static void GoldSoulDeathHandler(LivingDeathEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.getEffect(EffectRegister.Midas) != null) {
            livingEntity.spawnAtLocation(new ItemStack(Items.GOLD_NUGGET, random.nextInt(8)));
        }
    }


}
