package First.fargo_soul.common.item.base;

import First.fargo_soul.common.dataComponents.SoulRarity;
import First.fargo_soul.register.DataComponentsRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public abstract class SoulItem extends Item implements ISoulItem {

    public SoulItem(Properties properties) {
        super(properties.stacksTo(1).durability(0).rarity(Rarity.EPIC));
    }

    public @NotNull SoulRarity getSoulRarity() {
        return this.getDefaultInstance().getComponents().getOrDefault(DataComponentsRegister.SoulRarity.get(), SoulRarity.COMMON);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        return super.getName(stack).copy().withColor(getSoulRarity().getColor());
    }

}