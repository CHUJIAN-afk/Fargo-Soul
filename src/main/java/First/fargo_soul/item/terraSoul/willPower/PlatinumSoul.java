package First.fargo_soul.item.terraSoul.willPower;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.WillPower;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.Collection;

public class PlatinumSoul extends SoulItem {

    public PlatinumSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIGHT_RED));
    }

    @EventBusSubscriber(modid = FargoSoul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void onLootTableLoad(LootTableLoadEvent event) {

        }

        @SubscribeEvent
        public static void Drop(LivingDropsEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (CurioUtils.isEquipped(attacker, PlatinumSoul.class) && attacker.getRandom().nextDouble() < 0.2) {
                    Collection<ItemEntity> itemEntities = event.getDrops();
                    for (ItemEntity itemEntity : itemEntities) {
                        ItemStack itemStack = itemEntity.getItem();
                        Item item = itemStack.getItem();
                        if (!(item instanceof SoulItem)) {
                            int scale = CurioUtils.isEquipped(attacker, WillPower.class) ? 8 : 5;
                            itemStack.setCount(itemStack.getCount() * scale);
                        }
                    }
                }
            }
        }

    }

}
