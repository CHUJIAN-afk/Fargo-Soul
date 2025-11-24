package First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulCoreItem;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.WillPower.WillPower;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.Collection;

public class PlatinumSoul extends SoulItem {

    public PlatinumSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIGHT_RED));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Drop(LivingDropsEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, PlatinumSoul.class) && SoulUtils.random.nextDouble() < 0.2) {
                    Collection<ItemEntity> itemEntities = event.getDrops();
                    for (ItemEntity itemEntity : itemEntities) {
                        ItemStack itemStack = itemEntity.getItem();
                        Item item = itemStack.getItem();
                        if (!(item instanceof SoulCoreItem) && !(item instanceof SoulItem)) {
                            int scale = SoulUtils.isEquipped(attacker, WillPower.class) ? 8 : 5;
                            itemStack.setCount(itemStack.getCount() * scale);
                        }
                    }
                }
            }
        }

    }

}
