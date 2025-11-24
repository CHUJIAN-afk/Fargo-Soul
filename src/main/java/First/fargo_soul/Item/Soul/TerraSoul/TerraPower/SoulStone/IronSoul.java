package First.fargo_soul.Item.Soul.TerraSoul.TerraPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.TerraSoul.TerraPower.TerraPower;
import First.fargo_soul.Utils.SoulUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;

import java.util.List;

public class IronSoul extends SoulItem {

    public IronSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.GREEN));
    }

    @EventBusSubscriber
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, IronSoul.class)) {
                    Level level = attacker.level();
                    List<ItemEntity> itemEntityList = level.getEntitiesOfClass(ItemEntity.class, attacker.getBoundingBox().inflate(SoulUtils.isEquipped(attacker, TerraPower.class) ? 6 : 4), itemEntity -> !itemEntity.hasPickUpDelay());
                    for (ItemEntity itemEntity : itemEntityList) {
                        Vec3 delta = attacker.getBoundingBox().getCenter().subtract(itemEntity.getBoundingBox().getCenter()).normalize();
                        delta.scale(SoulUtils.isEquipped(attacker, TerraPower.class) ? 1.5 : 1);
                        itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(delta));
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Pickup(ItemEntityPickupEvent.Post event) {
            if (event.getPlayer() instanceof Player player && !player.level().isClientSide()) {
                if (SoulUtils.isEquipped(player, IronSoul.class)) {
                    SoulAbilityData.SoulInfo SoulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(IronSoul.class);
                    SoulInfo.setDuration(100);
                }
            }
        }

        @SubscribeEvent
        public static void Incoming(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
                SoulAbilityData.SoulInfo SoulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(IronSoul.class);
                if (SoulUtils.isEquipped(player, IronSoul.class) && SoulInfo.getDuration() > 0) {
                    event.setAmount(event.getAmount() * 0.8f);
                }
            }
        }

    }

}