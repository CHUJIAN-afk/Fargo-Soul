package First.fargo_soul.item.terraSoul.deathPower;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.DeathPower;
import First.fargo_soul.register.AttachmentRegister;
import First.fargo_soul.register.ItemRegister;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.RenderUtils;
import First.fargo_soul.utils.SoulUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.ArrayList;
import java.util.List;

public class PenetratingNinjaSoul extends SoulItem {

    public PenetratingNinjaSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(LivingEntity ticker) {
        if (!ticker.level().isClientSide() && CurioUtils.isEquipped(ticker, PenetratingNinjaSoul.class)) {
            SoulAbilityData.SoulInfo soulInfo = ticker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PenetratingNinjaSoul.class);
            soulInfo.setMaxCooldown(400);
            if (soulInfo.getDuration() > 0) {
                List<LivingEntity> livingEntityList = ticker.level().getEntitiesOfClass(LivingEntity.class, ticker.getBoundingBox());
                for (LivingEntity target : livingEntityList) {
                    float amount = ticker.getMaxHealth() * 0.1f;
                    int strength = 3;
                    if (CurioUtils.isEquipped(ticker, DeathPower.class)) {
                        amount += target.getMaxHealth() * 0.05f;
                        strength++;
                    }
                    SoulUtils.attack(ticker, target, DamageTypes.WITHER, amount);
                    target.knockback(strength, ticker.getX(), ticker.getZ());
                }
            }
        }
    }

    @Override
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            if (CurioUtils.isEquipped(attacker, PenetratingNinjaSoul.class)) {
                SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PenetratingNinjaSoul.class);
                if (soulInfo.getDuration() > 0) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public List<Component> getGuiTooltip(@UnknownNullability Player player) {
        List<Component> list = new ArrayList<>();
        list.add(RenderUtils.createCooldownTooltip(this, "渗透冲刺冷却", player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PenetratingNinjaSoul.class)));
        return list;
    }

    public record Packet(boolean isEquipped) implements CustomPacketPayload {

        public static final Type<Packet> TYPE = new Type<>(ItemRegister.PenetratingNinjaSoulItem.getId());
        public static final StreamCodec<ByteBuf, Packet> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.BOOL,
                Packet::isEquipped,
                Packet::new
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public void handle(IPayloadContext context) {
            context.enqueueWork(() -> {
                if (isEquipped) {
                    Player player = context.player();
                    SoulAbilityData.SoulInfo soulInfo = player.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PenetratingNinjaSoul.class);
                    if (soulInfo.isReady()) {
                        soulInfo.setCooldown(soulInfo.getMaxCooldown());
                        soulInfo.setDuration(20);
                    }
                }
            });
        }

    }

}
