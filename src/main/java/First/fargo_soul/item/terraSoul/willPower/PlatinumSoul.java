package First.fargo_soul.item.terraSoul.willPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.WillPower;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.Collection;

public class PlatinumSoul extends SoulItem {

    public PlatinumSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void drop(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, PlatinumSoul.class) && attacker.getRandom().nextDouble() < 0.2) {
                Collection<ItemEntity> itemEntities = event.getDrops();
                for (ItemEntity itemEntity : itemEntities) {
                    ItemStack itemStack = itemEntity.getItem();
                    Item item = itemStack.getItem();
                    if (!(item instanceof SoulItem)) {
                        int scale = CurioUtils.isEquipped(attacker, WillPower.class) ? 8 : 5;
                        SoulAbilityData.SoulInfo soulInfo = SoulAbilityData.getSoulInfo(attacker, RedRidingSoul.class);
                        if (scale == 8 && soulInfo.getStacks() == soulInfo.getMaxStacks()) {
                            scale = 16;
                        }
                        itemStack.setCount(itemStack.getCount() * scale);
                    }
                }
            }
        }
    }

}
