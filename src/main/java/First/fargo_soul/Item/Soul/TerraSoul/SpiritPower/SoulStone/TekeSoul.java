package First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.SpiritPower.SpiritPower;
import First.fargo_soul.Utils.SoulUtils;
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
            SoulAbilityData.updateMaxCooldown(event.getEntity(), TekeSoul.class, SoulUtils.isEquipped(event.getEntity(), SpiritPower.class) ? 3600 : 6000);
        }

        @SubscribeEvent
        public static void Death(LivingDeathEvent event) {
            if (!event.isCanceled() && event.getEntity() instanceof TamableAnimal animal && animal.getOwner() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(TekeSoul.class);
                soulInfo.maxCooldown = SoulUtils.isEquipped(event.getEntity(), SpiritPower.class) ? 3600 : 6000;
                if (soulInfo.cooldown == 0 && SoulUtils.isEquipped(attacker, TekeSoul.class)) {
                    animal.heal(attacker.getHealth() * 0.25f);
                    event.setCanceled(true);
                }
            }
        }

    }



}
