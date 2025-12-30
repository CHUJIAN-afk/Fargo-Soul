package First.fargo_soul.attachment;

import First.fargo_soul.FargoSoul;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.utils.CurioUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = FargoSoul.MODID)
public class SoulListData implements INBTSerializable<CompoundTag>, AttachmentSyncHandler<SoulListData> {

	private List<SoulItem> soulItemList;

	public SoulListData() {
		this(new ArrayList<>());
	}

	public SoulListData(List<SoulItem> soulItemList) {
		this.soulItemList = soulItemList;
	}

	public List<SoulItem> getSoulItemList() {
		return soulItemList;
	}

	public void setSoulItemList(List<SoulItem> soulItemList) {
		this.soulItemList = soulItemList;
	}

	@SubscribeEvent
	public static void CurioChangeEvent(CurioChangeEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (!livingEntity.level().isClientSide()) {
			CurioUtils.updateSoulList(livingEntity);
		}
	}


        @Override
        public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = new CompoundTag();
            ListTag listTag = new ListTag();
            for (SoulItem soulItem : soulItemList) {
                CompoundTag itemTag = new CompoundTag();
                ResourceLocation key = BuiltInRegistries.ITEM.getKey(soulItem);
                itemTag.putString("item", key.toString());
                listTag.add(itemTag);
            }
            tag.put("soulItemList", listTag);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag compoundTag) {
            soulItemList.clear();
            if (compoundTag.contains("soulItemList", Tag.TAG_LIST)) {
                ListTag listTag = compoundTag.getList("soulItemList", Tag.TAG_COMPOUND);
                for (Tag tag : listTag) {
                    if (tag instanceof CompoundTag itemTag) {
                        String itemKey = itemTag.getString("item");
                        Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemKey));
                        if (item instanceof SoulItem soulItem) {
                            soulItemList.add(soulItem);
                        }
                    }
                }
            }
        }


	@Override
	public void write(@NotNull RegistryFriendlyByteBuf buf, SoulListData data, boolean clientPacket) {
		List<SoulItem> itemList = data.soulItemList;
		buf.writeVarInt(itemList.size());
		for (SoulItem soulItem : itemList) {
			ResourceLocation key = BuiltInRegistries.ITEM.getKey(soulItem);
			buf.writeResourceLocation(key);
		}
	}

	@Override
	public @Nullable SoulListData read(@NotNull IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable SoulListData data) {
		List<SoulItem> soulItemList = new ArrayList<>();
		int size = buf.readVarInt();
		for (int i = 0; i < size; i++) {
			ResourceLocation key = buf.readResourceLocation();
			Item item = BuiltInRegistries.ITEM.get(key);
			if (item instanceof SoulItem soulItem) {
				soulItemList.add(soulItem);
			}
		}
		return new SoulListData(soulItemList);
	}

}
