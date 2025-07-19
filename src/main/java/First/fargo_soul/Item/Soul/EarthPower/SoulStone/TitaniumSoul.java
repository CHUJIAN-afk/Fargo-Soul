package First.fargo_soul.Item.Soul.EarthPower.SoulStone;

import First.fargo_soul.Item.Soul.SoulItem;
import First.fargo_soul.Item.Soul.Souls;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

import static First.fargo_soul.Utils.MathUtils.random;

public class TitaniumSoul extends SoulItem {

    public TitaniumSoul(Properties properties) {
        super(properties);
    }

    public List<Component> AttributeList = List.of(
            Component.literal("一段时间不受伤害后生成一个钛金护盾，免疫下次受到伤害").withStyle(ChatFormatting.BLUE),
            Component.literal("钛金护盾未生成时，剩余生命值比例越低，闪避率越高，最大15%").withStyle(ChatFormatting.BLUE)
    );

    public List<Component> TooltipList = List.of(
            Component.literal("“有了绝对防御后，谁还需要躲避呢？”").withStyle(ChatFormatting.DARK_GRAY)
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

    public static void TitaniumSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.TitaniumSoul.get())) {
            long TitaniumSoul = player.getPersistentData().getLong("TitaniumSoul");
            long GameTime = player.serverLevel().getGameTime();
            float chance = (1 - (player.getHealth() / player.getMaxHealth())) * 0.15f;
            if (TitaniumSoul < (GameTime - 600)) {
                event.setCanceled(true);
                player.serverLevel().playSound(
                        null,
                        player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ANVIL_PLACE,
                        SoundSource.PLAYERS,
                        1.0f,
                        random.nextFloat() * 0.4f + 0.4f
                );
            } else if (random.nextFloat() < chance) {
                event.setCanceled(true);
            }
            player.getPersistentData().putLong("TitaniumSoul", GameTime);
        }
    }

    public static void TitaniumSoulTickHandler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, Souls.TitaniumSoul.get())) {
            long TitaniumSoul = player.getPersistentData().getLong("TitaniumSoul");
            long GameTime = player.serverLevel().getGameTime();
            float chance = (1 - (player.getHealth() / player.getMaxHealth()));
            if (TitaniumSoul > (GameTime - 600) && random.nextFloat() < chance) {
                ParticleUtils.spawnParticleLine(
                        player.serverLevel(),
                        player.getEyePosition().add((1 - random.nextDouble(2)), (1 - random.nextDouble(2)), (1 - random.nextDouble(2))),
                        player.position().add((1 - random.nextDouble(2)), (1 - random.nextDouble(2)), (1 - random.nextDouble(2))),
                        ParticleTypes.CLOUD,
                        1,
                        0.2f
                );
            }
        }
    }

}

