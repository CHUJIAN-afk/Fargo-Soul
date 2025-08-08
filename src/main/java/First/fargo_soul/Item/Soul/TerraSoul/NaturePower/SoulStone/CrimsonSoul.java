package First.fargo_soul.Item.Soul.TerraSoul.NaturePower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class CrimsonSoul extends SoulItem {

    public CrimsonSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.BLUE));
    }

    public final List<Component> AttributeList = List.of(
            Component.translatable("item.fargo_soul.crimson_soul.attribute.1").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.crimson_soul.attribute.2").withStyle(ChatFormatting.BLUE),
            Component.translatable("item.fargo_soul.crimson_soul.attribute.3").withStyle(ChatFormatting.BLUE)
    );

    public final List<Component> TooltipList = List.of(
            Component.translatable("item.fargo_soul.crimson_soul.tooltip.1").withStyle(ChatFormatting.DARK_GRAY)
    );


    @Override
    public List<Component> getAttributeList() {
        return AttributeList;
    }

    @Override
    public List<Component> getTooltipList() {
        return TooltipList;
    }


    public static void CrimsonSoulMobEffectExpiredHandler(MobEffectEvent.Expired event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (event.getEffectInstance() instanceof MobEffectInstance mobEffectInstance && mobEffectInstance.is(EffectRegister.ScarletHeals)) {
                player.heal(player.getPersistentData().getFloat("CrimsonSoul"));
                player.getPersistentData().remove("CrimsonSoul");
            }
        }
    }

    public static void CrimsonSoulDamageHandler2(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.CrimsonSoul.get()) && event.getAmount() > 10) {
            if (player.getEffect(EffectRegister.ScarletHeals) == null) {
                player.getPersistentData().putFloat("CrimsonSoul", event.getAmount() * 0.5f);
                player.addEffect(new MobEffectInstance(EffectRegister.ScarletHeals, 140));
            } else {
                player.getPersistentData().remove("CrimsonSoul");
                player.removeEffect(EffectRegister.ScarletHeals);
            }
        }
    }




}
