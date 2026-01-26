package First.fargo_soul.common.item.base;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import org.jetbrains.annotations.NotNull;

public abstract class SoulItem extends Item implements ISoulItem {

    public SoulItem(Properties properties) {
        super(properties.stacksTo(1).durability(0).rarity(Rarity.EPIC));
    }

    public ModRarity getModRarity() {
        return this.components().getOrDefault(ConfluenceMagicLib.MOD_RARITY.get(), ModRarity.COMMON);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        ModRarity modRarity = stack.get(ConfluenceMagicLib.MOD_RARITY);
        modRarity = modRarity != null ? modRarity : ModRarity.COMMON;
        return component.copy().withColor(modRarity.color());
    }

}