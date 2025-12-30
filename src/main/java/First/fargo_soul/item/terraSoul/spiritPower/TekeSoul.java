package First.fargo_soul.item.terraSoul.spiritPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.SpiritPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

public class TekeSoul extends SoulItem {

    public TekeSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIME));
    }

    @EventBusSubscriber
    public static class Event {

        @SubscribeEvent
        public static void CurioChangeEvent(CurioChangeEvent event) {
            SoulAbilityData.SoulInfo soulInfo = event.getEntity().getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TekeSoul.class);
            soulInfo.setMaxCooldown(CurioUtils.isEquipped(event.getEntity(), SpiritPower.class) ? 3600 : 6000);
        }

        @SubscribeEvent
        public static void Death(LivingDeathEvent event) {
            if (!event.isCanceled() && event.getEntity() instanceof TamableAnimal animal && animal.getOwner() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TekeSoul.class);
                soulInfo.setMaxCooldown(CurioUtils.isEquipped(event.getEntity(), SpiritPower.class) ? 3600 : 6000);
                if (soulInfo.getCooldown() == 0 && CurioUtils.isEquipped(attacker, TekeSoul.class)) {
                    animal.heal(attacker.getHealth() * 0.25f);
                    event.setCanceled(true);
                }
            }
        }

    }



}
