package first.fargo_soul.register;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.common.attachment.SoulItemData;
import first.fargo_soul.common.attachment.SoulTargetCache;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;

public class FargoSoulAttachmentRegister {

    private static final Registrate REGISTER = FargoSoul.REGISTRATE;

    public static final RegistryEntry<AttachmentType<?>, AttachmentType<SoulItemData>> SOUL_ITEM_DATA =
            REGISTER.simple("soul_item_data", NeoForgeRegistries.Keys.ATTACHMENT_TYPES, () -> AttachmentType.serializable(SoulItemData::new)
                    .copyOnDeath()
                    .sync(new SoulItemData())
                    .build());

    public static final RegistryEntry<AttachmentType<?>, AttachmentType<SoulInfoData>> SOUL_INFO_DATA =
            REGISTER.simple("soul_info_data", NeoForgeRegistries.Keys.ATTACHMENT_TYPES, () -> AttachmentType.serializable(() -> new SoulInfoData(new HashMap<>()))
                    .sync(new SoulInfoData(new HashMap<>()))
                    .build());

    public static final RegistryEntry<AttachmentType<?>, AttachmentType<SoulTargetCache>> SOUL_TARGET_CACHE =
            REGISTER.simple("soul_target_cache", NeoForgeRegistries.Keys.ATTACHMENT_TYPES, () -> AttachmentType.builder(SoulTargetCache::new)
                    .build());

    public static void init() {
    }
}
