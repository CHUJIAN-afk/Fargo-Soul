package First.fargo_soul.Attachment;

import First.fargo_soul.Attachment.Attachment.DamageData;
import First.fargo_soul.Attachment.Attachment.SoulAbilityData;
import First.fargo_soul.Attachment.Attachment.SoulData;
import First.fargo_soul.Fargo_soul;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AttachmentRegister {

	private static final DeferredRegister<AttachmentType<?>> Attachments;
	public static final Supplier<AttachmentType<SoulData>> SoulData;
	public static final Supplier<AttachmentType<DamageData>> DamageData;
	public static final Supplier<AttachmentType<SoulAbilityData>> SoulAbilityData;

	static {
		Attachments = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Fargo_soul.MODID);
		SoulData = Attachments.register("soul_data", () -> AttachmentType.builder(SoulData::new).build());
		DamageData = Attachments.register("damage_data", () -> AttachmentType.builder(DamageData::new).build());
		SoulAbilityData = Attachments.register("soul_ability_data", () -> AttachmentType.serializable(SoulAbilityData::new).build());
	}

	public static void register(IEventBus eventbus) {
		Attachments.register(eventbus);
	}
}
