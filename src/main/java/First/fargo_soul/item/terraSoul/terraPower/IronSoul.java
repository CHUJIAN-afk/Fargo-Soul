package First.fargo_soul.item.terraSoul.terraPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.client.gui.SoulRenderType;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.TerraPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
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
        if (!ticker.level().isClientSide()) {
            if (CurioUtils.isEquipped(ticker, IronSoul.class)) {
                Level level = ticker.level();
                List<ItemEntity> itemEntityList = level.getEntitiesOfClass(ItemEntity.class, ticker.getBoundingBox().inflate(CurioUtils.isEquipped(ticker, TerraPower.class) ? 6 : 4), itemEntity -> !itemEntity.hasPickUpDelay());
                for (ItemEntity itemEntity : itemEntityList) {
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