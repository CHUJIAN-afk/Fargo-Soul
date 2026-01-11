package First.fargo_soul.register;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.attachment.SoulAbilityEnabledData;
import First.fargo_soul.attachment.SoulDamageData;
import First.fargo_soul.attachment.SoulListData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AttachmentRegister {

	private static final DeferredRegister<AttachmentType<?>> Register =
			DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, FargoSoul.MODID);

	public static final Supplier<AttachmentType<SoulAbilityData>> SoulAbilityData =
			Register.register("soul_ability_data", () -> {
				Supplier<SoulAbilityData> data = SoulAbilityData::new;
				AttachmentType.Builder<SoulAbilityData> builder = AttachmentType.serializable(data);
				builder.sync(data.get());
				return builder.build();
			});

	public static final Supplier<AttachmentType<SoulAbilityEnabledData>> AbilityEnabledData =
			Register.register("ability_enabled_data", () -> {
				Supplier<SoulAbilityEnabledData> data = SoulAbilityEnabledData::new;
				AttachmentType.Builder<SoulAbilityEnabledData> builder = AttachmentType.serializable(data);
				builder.sync(data.get());
				builder.copyOnDeath();
				return builder.build();
			});

	public static final Supplier<AttachmentType<SoulListData>> SoulListData =
			Register.register("soul_list_data", () -> {
				Supplier<SoulListData> data = SoulListData::new;
				AttachmentType.Builder<SoulListData> builder = AttachmentType.serializable(data);
				builder.sync(data.get());
				return builder.build();
			});

	public static final Supplier<AttachmentType<SoulDamageData>> SoulDamageData =
			Register.register("soul_damage_data", () -> AttachmentType.builder(SoulDamageData::new).build());

	public static void register(IEventBus eventbus) {
		Register.register(eventbus);
	}

}
