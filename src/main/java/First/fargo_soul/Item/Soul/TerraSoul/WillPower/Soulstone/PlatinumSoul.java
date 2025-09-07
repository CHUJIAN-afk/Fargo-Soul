package First.fargo_soul.Item.Soul.TerraSoul.WillPower.Soulstone;

import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Utils.CurioUtils;
import First.fargo_soul.Utils.CustomUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.Collection;

public class PlatinumSoul extends SoulItem {

    public PlatinumSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.LIGHT_RED));
    }


    public static void PlatinumSoulDropsEvent(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && CurioUtils.isEquipped(player, SoulsRegister.PlatinumSoul.get())) {
            if (event.getEntity() instanceof Monster && CustomUtils.random.nextDouble() < 0.2 && event.getEntity() instanceof LivingEntity) {
                Collection<ItemEntity> itemEntities = event.getDrops();
                for (ItemEntity itemEntity : itemEntities) {
                    ItemStack itemStack = itemEntity.getItem();
                    itemStack.setCount(itemStack.getCount() * 5);
                }
            }
        }
    }





}
