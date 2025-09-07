package First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone;

import First.fargo_soul.Effect.EffectRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

public class GloomySoul extends SoulItem {

    public GloomySoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }


    public static void GloomySoulDamageHandler(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof TamableAnimal animal && animal.getOwner() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.GloomySoul.get()) && event.getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(EffectRegister.ShadowFire, 200));
        }
    }

}
