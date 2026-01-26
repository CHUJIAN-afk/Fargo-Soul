package First.fargo_soul.register;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.common.attachment.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AttachmentRegister {

	private static final DeferredRegister<AttachmentType<?>> Register =
			DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, FargoSoul.MODID);

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<SoulAbilityData>> SoulAbilityData =
			Register.register("soul_ability_data", () -> AttachmentType.serializable(() -> new SoulAbilityData())
					.sync(new SoulAbilityData())
					.copyOnDeath()
					.build()
			);

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<SoulAbilityEnabledData>> AbilityEnabledData =
			Register.register("ability_enabled_data", () -> AttachmentType.serializable(() -> new SoulAbilityEnabledData())
					.sync(new SoulAbilityEnabledData())
					.copyOnDeath()
					.build()
			);

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<SoulListData>> SoulListData =
			Register.register("soul_list_data", () -> AttachmentType.serializable(() -> new SoulListData())
					.sync(new SoulListData())
					.build()
			);

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<SoulDamageData>> SoulDamageData =
			Register.register("soul_damage_data", () -> AttachmentType.builder(SoulDamageData::new).build());

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<SoulContainerData>> SoulContainerData =
			Register.register("soul_container_data", () -> AttachmentType.serializable(SoulContainerData::new)
					.sync(new SoulContainerData())
					.copyOnDeath()
					.build()
			);

	public static void register(IEventBus eventbus) {
		Register.register(eventbus);
	}

}
