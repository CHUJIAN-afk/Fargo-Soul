package First.fargo_soul.Item.Soul.TerraSoul.DeathPower.SoulStone;

import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import First.fargo_soul.Item.Soul.SoulsRegister;
import First.fargo_soul.Item.Soul.TerraSoul.DeathPower.DeathPower;
import First.fargo_soul.Utils.SoulUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PenetratingNinjaSoul extends SoulItem {

    public PenetratingNinjaSoul(Properties properties) {
        super(properties.component(ConfluenceMagicLib.MOD_RARITY, ModRarity.YELLOW));
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if (SoulUtils.isEquipped(attacker, PenetratingNinjaSoul.class)) {
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PenetratingNinjaSoul.class);
                    soulInfo.maxCooldown = 400;
                    if (soulInfo.duration > 0) {
                        List<LivingEntity> livingEntityList = attacker.level().getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox());
                        for (LivingEntity target : livingEntityList) {
                            float amount = attacker.getMaxHealth() * 0.1f;
                            int strength = 3;
                            if (SoulUtils.isEquipped(attacker, DeathPower.class)) {
                                amount += target.getMaxHealth() * 0.05f;
                                strength++;
                            }
                            SoulUtils.attack(attacker, target, DamageTypes.WITHER, amount);
                            target.knockback(strength, attacker.getX(), attacker.getZ());
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void Damage(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
                if ( SoulUtils.isEquipped(attacker, PenetratingNinjaSoul.class)){
                    SoulAbilityData.SoulInfo soulInfo = attacker.getData(AttachmentRegister.SoulAbilityData).getSoulInfo(PenetratingNinjaSoul.class);
                    if (soulInfo.duration > 0) {
                        event.setCanceled(true);
                    }
                }
            }
        }

    }

    public record Packet(boolean isEquipped) implements CustomPacketPayload {

        public static final Type<Packet> TYPE = new Type<>(SoulsRegister.PenetratingNinjaSoul.getId());
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
                    if (soulInfo.cooldown == 0) {
                        soulInfo.cooldown = soulInfo.maxCooldown;
                        soulInfo.duration = 20;
                    }
                }
            });
        }

    }

}
