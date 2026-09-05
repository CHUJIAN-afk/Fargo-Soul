package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.attachment.SoulTargetCache;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;

public class FargoSoulAttachmentRegister {

	private static final DeferredRegister<AttachmentType<?>> Register =
			DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, FargoSoul.MODID);

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<SoulItemData>> SOUL_ITEM_DATA =
			Register.register("soul_item_data", () -> AttachmentType.builder(SoulItemData::new)
					.sync(new SoulItemData())
					.build());

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<SoulInfoData>> SOUL_INFO_DATA =
			Register.register("soul_info_data", () -> AttachmentType.serializable(() -> new SoulInfoData(new HashMap<>()))
					.sync(new SoulInfoData(new HashMap<>()))
					.build());

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<SoulTargetCache>> SOUL_TARGET_CACHE =
			Register.register("soul_target_cache", () -> AttachmentType.builder(SoulTargetCache::new)
					.build());

	public static void register(IEventBus eventbus) {
		Register.register(eventbus);
	}

}
