package First.fargo_soul.Attachment;

import First.fargo_soul.Attachment.Attachment.Data;
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
	public static final Supplier<AttachmentType<Data>> Data;


	static {
		Attachments = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Fargo_soul.MODID);
		SoulData = Attachments.register("soul_data", () -> AttachmentType.builder(SoulData::new).build());
		Data = Attachments.register("data", () -> AttachmentType.builder(Data::new).build());
	}

	public static void register(IEventBus eventbus) {
		Attachments.register(eventbus);
	}
}
