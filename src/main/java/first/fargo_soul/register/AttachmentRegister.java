package first.fargo_soul.register;

import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.ISoulItemCache;
import first.fargo_soul.common.attachment.InvincibleData;
import first.fargo_soul.common.attachment.TargetCache;
import first.fargo_soul.common.attachment.soulInfoData.SoulInfoData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AttachmentRegister {

	private static final DeferredRegister<AttachmentType<?>> Register =
			DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, FargoSoul.MODID);

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<TargetCache>> TARGET_CACHE =
			Register.register("target_cache", () -> AttachmentType.builder(TargetCache::new).build());

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<InvincibleData>> INVINCIBLE_DATA =
			Register.register("invincible_data", () -> AttachmentType.builder(InvincibleData::new)
					.build());

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<SoulInfoData>> SOUL_INFO_DATA =
			Register.register("SoulInfoData", () -> AttachmentType.builder(SoulInfoData::new)
					.build());

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<ISoulItemCache>> I_SOUL_ITEM_CACHE =
			Register.register("i_soul_item_cache", () -> AttachmentType.builder(ISoulItemCache::new)
					.build());

	public static void register(IEventBus eventbus) {
		Register.register(eventbus);
	}

}
