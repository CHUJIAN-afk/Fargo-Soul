package First.fargo_soul.Item.Soul.TerraSoul.LifePower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TurtleSoul extends SoulItem {
    public TurtleSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    public final List<SoulItem> soulItemList = List.of(
            SoulsRegister.CactusSoul.get()
    );

    public final List<Component> list1 =
            soulItemList.stream()
                    .flatMap(curioItem -> curioItem.getAttributeList().stream())
                    .collect(Collectors.toList());


    public final List<Component> list2 = List.of(
            Component.translatable("item.fargo_soul.turtle_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.turtle_soul.attribute.2").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.turtle_soul.attribute.3").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.turtle_soul.attribute.4").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.turtle_soul.attribute.5").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.turtle_soul.attribute.6").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> AttributeList = Stream.concat(
            list1.stream(),
            list2.stream()
    ).collect(Collectors.toList());


    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.turtle_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );

    @Override
    public List<SoulItem> getCurioItemList() {
        return this.soulItemList;
    }

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

    public static void TurtleSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.TurtleSoul.get())) {
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                livingEntity.hurt(player.damageSources().cactus(), event.getAmount());
            }
            if (player.getEffect(EffectRegister.ShellDefense) != null) {
                event.setAmount(event.getAmount() * 0.1f);
            }
        }
    }

    public static void TurtleSoulTickHnadler(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.TurtleSoul.get())) {
            player.getPersistentData().putInt("TurtleSoul", player.getPersistentData().getInt("TurtleSoul") - 1);
            long Shift = player.getPersistentData().getLong("Shift");
            if (player.isShiftKeyDown()) {
                player.getPersistentData().putLong("Shift", player.serverLevel().getGameTime());
            }
            if ((Shift + 20) > player.serverLevel().getGameTime() && player.isShiftKeyDown() && player.onGround() && player.getPersistentData().getInt("TurtleSoul") < 0) {
                player.getPersistentData().putInt("TurtleSoul", 1100);
                int duration = (int) (20 + (20 * (player.getHealth() / player.getMaxHealth())));
                player.addEffect(new MobEffectInstance(EffectRegister.ShellDefense, duration));
            }
            if (player.getEffect(EffectRegister.ShellDefense) != null) {
                List<AbstractArrow> arrowList = player.serverLevel().getEntitiesOfClass(AbstractArrow.class, player.getHitbox().inflate(2), abstractArrow -> abstractArrow.getOwner() != player);
                for (AbstractArrow abstractArrow : arrowList) {
                    if (abstractArrow.getOwner() instanceof LivingEntity livingEntity && !livingEntity.equals(player)) {
                        livingEntity.hurt(player.damageSources().cactus(), (float) abstractArrow.getBaseDamage() * 10);
                    }
                    ParticleUtils.spawnParticleSphere(
                            player.serverLevel(),
                            abstractArrow.position(),
                            ParticleTypes.CRIT,
                            0.1f,
                            1,
                            0.0f
                    );
                    abstractArrow.discard();
                }
            }
        }
    }

}
