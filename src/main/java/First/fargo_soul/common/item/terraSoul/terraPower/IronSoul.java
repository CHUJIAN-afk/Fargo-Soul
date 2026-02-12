package First.fargo_soul.common.item.terraSoul.terraPower;

import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.common.attachment.SoulAbilityData;
import First.fargo_soul.common.item.base.SoulItem;
import First.fargo_soul.common.item.terraSoul.TerraPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

import java.util.List;

public class IronSoul extends SoulItem {

    public IronSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        Level level = ticker.level();
        if (!level.isClientSide() && ticker instanceof Player player && CurioUtils.isEquipped(ticker, IronSoul.class)) {
            Inventory inventory = player.getInventory();
            NonNullList<ItemStack> items = inventory.items;
            List<ItemEntity> itemEntityList = level.getEntitiesOfClass(ItemEntity.class, ticker.getBoundingBox().inflate(CurioUtils.isEquipped(ticker, TerraPower.class) ? 6 : 4));
            for (ItemEntity itemEntity : itemEntityList) {
                if (!itemEntity.hasPickUpDelay() && items.stream().anyMatch(itemStack -> itemStack.isEmpty() || ItemEntity.areMergable(itemStack.copy(), itemEntity.getItem().copy()))) {
                    Vec3 delta = ticker.getBoundingBox().getCenter().subtract(itemEntity.getBoundingBox().getCenter()).normalize();
                    delta.scale(CurioUtils.isEquipped(ticker, TerraPower.class) ? 1.5 : 1);
                    itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(delta));
                }
            }
        }
    }

    @Override
    public void pickup(ItemEntityPickupEvent.Post event) {
        if (event.getPlayer() instanceof Player player && !player.level().isClientSide()) {
            if (CurioUtils.isEquipped(player, IronSoul.class)) {
                SoulAbilityData.SoulInfo SoulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(IronSoul.class);
                SoulInfo.setDuration(100);
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            SoulAbilityData.SoulInfo SoulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(IronSoul.class);
            if (CurioUtils.isEquipped(player, IronSoul.class) && SoulInfo.getDuration() > 0) {
                event.setAmount(event.getAmount() * 0.8f);
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        soulRenderManager.add(this, IronSoul.class, SoulRenderType.Duration);
    }

}