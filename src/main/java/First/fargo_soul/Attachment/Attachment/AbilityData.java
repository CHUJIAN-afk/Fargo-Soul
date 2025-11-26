package First.fargo_soul.Attachment.Attachment;

import First.fargo_soul.Attachment.AttachmentRegister;
import First.fargo_soul.Fargo_soul;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AbilityData implements INBTSerializable<CompoundTag> {

    private final Map<String, Boolean> ability = new HashMap<>();

    public Map<String, Boolean> getAbility() {
        return ability;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        return serializeNBT(ability);
    }

    private static @NotNull CompoundTag serializeNBT(Map<String, Boolean> ability) {
        CompoundTag compoundTag = new CompoundTag();
        for (Map.Entry<String, Boolean> entry : ability.entrySet()) {
            compoundTag.putBoolean(entry.getKey(), entry.getValue());
        }
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag compoundTag) {
        deserializeNBT(ability, compoundTag);
    }

    private static void deserializeNBT(Map<String, Boolean> ability, CompoundTag compoundTag) {
        ability.clear();
        for (String key : compoundTag.getAllKeys()) {
            if (compoundTag.contains(key, Tag.TAG_BYTE) || compoundTag.contains(key, Tag.TAG_INT)) {
                boolean value = compoundTag.getBoolean(key);
                ability.put(key, value);
            }
        }
    }

    @EventBusSubscriber(modid = Fargo_soul.MODID)
    public static class Event {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void Death(PlayerEvent.Clone event) {
            if (event.isWasDeath()) {
                Map<String, Boolean> ability1 = event.getOriginal().getData(AttachmentRegister.AbilityData).getAbility();
                Map<String, Boolean> ability2 = event.getEntity().getData(AttachmentRegister.AbilityData).getAbility();
                ability2.putAll(ability1);
            }
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void Tick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
                if (player.tickCount % 10 == 0) {
                    AbilityData abilityData = player.getData(AttachmentRegister.AbilityData);
                    PacketDistributor.sendToAllPlayers(new Packet(player.getId(), serializeNBT(abilityData.getAbility())));
                }
            }
        }

    }

    public record Packet(int id, CompoundTag tag) implements CustomPacketPayload {

        public static final Type<Packet> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, "ability_data"));
        public static final StreamCodec<ByteBuf, Packet> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT,
                Packet::id,
                ByteBufCodecs.COMPOUND_TAG,
                Packet::tag,
                Packet::new
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public void handle(IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
                if (player.isLocalPlayer() && player.level().getEntity(id) instanceof LivingEntity livingEntity) {
                    deserializeNBT(livingEntity.getData(AttachmentRegister.AbilityData).getAbility(), tag);
                }
            });
        }

    }

}



