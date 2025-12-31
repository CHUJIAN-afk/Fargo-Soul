package First.fargo_soul.dadageneeator.provider;


import First.fargo_soul.register.ItemRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SoulAdvancementProvider implements AdvancementProvider.AdvancementGenerator {

    protected Consumer<AdvancementHolder> output = null;

    protected AdvancementHolder builder(AdvancementHolder parent, ItemLike itemLike, Component title, @Nullable ResourceLocation background, AdvancementType type) {
        Item item = itemLike.asItem();
        String id = item.toString();
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder = parent != null ? builder.parent(parent) : builder;
        builder = builder.display(item, title, Component.translatable("fargo_soul.advancement.get").withStyle(ChatFormatting.DARK_RED).append(item.getName(item.getDefaultInstance())), background, type, true, true, false);
        builder = builder.addCriterion("has_" + id, InventoryChangeTrigger.TriggerInstance.hasItems(item));
        return builder.save(output, id);
    }

    protected AdvancementHolder builder(ItemLike itemLike, Component title, @Nullable ResourceLocation background, AdvancementType type) {
        return builder(null, itemLike, title, background, type);
    }

    protected AdvancementHolder builder(AdvancementHolder parent, ItemLike itemLike, Component title, AdvancementType type) {
        return builder(parent, itemLike, title, null, type);
    }


    @Override
    public void generate(HolderLookup.@NotNull Provider provider, @NotNull Consumer<AdvancementHolder> output, @NotNull ExistingFileHelper existingFileHelper) {
        this.output = output;

        AdvancementHolder root = builder(ItemRegister.CosmicCrucibleBlockItem, Component.translatable("advancement.fargo_soul.root.title"), ResourceLocation.withDefaultNamespace("textures/block/deepslate_bricks.png"), AdvancementType.TASK);

        AdvancementHolder terraSoulRoot = builder(root, ItemRegister.TerraSoulItem, Component.translatable("advancement.fargo_soul.terra_soul.title"), AdvancementType.CHALLENGE);

        AdvancementHolder cosmicPowerRoot = builder(terraSoulRoot, ItemRegister.CosmicPowerItem, Component.translatable("advancement.fargo_soul.cosmic_power.title"), AdvancementType.GOAL);
        builder(cosmicPowerRoot, ItemRegister.BlazeSoulItem, Component.translatable("advancement.fargo_soul.blaze_soul.title"), AdvancementType.TASK);
        builder(cosmicPowerRoot, ItemRegister.NebulaSoulItem, Component.translatable("advancement.fargo_soul.nebula_soul.title"), AdvancementType.TASK);
        builder(cosmicPowerRoot, ItemRegister.StardustSoulItem, Component.translatable("advancement.fargo_soul.stardust_soul.title"), AdvancementType.TASK);
        builder(cosmicPowerRoot, ItemRegister.VortexSoulItem, Component.translatable("advancement.fargo_soul.vortex_soul.title"), AdvancementType.TASK);
        builder(cosmicPowerRoot, ItemRegister.MeteorSoulItem, Component.translatable("advancement.fargo_soul.meteor_soul.title"), AdvancementType.TASK);
        builder(cosmicPowerRoot, ItemRegister.WizardSoulItem, Component.translatable("advancement.fargo_soul.wizard_soul.title"), AdvancementType.TASK);

        AdvancementHolder deathPowerRoot = builder(terraSoulRoot, ItemRegister.DeathPowerItem, Component.translatable("advancement.fargo_soul.death_power.title"), AdvancementType.GOAL);
        builder(deathPowerRoot, ItemRegister.AncientShadowSoulItem, Component.translatable("advancement.fargo_soul.ancient_shadow_soul.title"), AdvancementType.TASK);
        builder(deathPowerRoot, ItemRegister.CrystalAssassinSoulItem, Component.translatable("advancement.fargo_soul.crystal_assassin_soul.title"), AdvancementType.TASK);
        builder(deathPowerRoot, ItemRegister.DarkArtistSoulItem, Component.translatable("advancement.fargo_soul.dark_artist_soul.title"), AdvancementType.TASK);
        builder(deathPowerRoot, ItemRegister.GloomySoulItem, Component.translatable("advancement.fargo_soul.gloomy_soul.title"), AdvancementType.TASK);
        builder(deathPowerRoot, ItemRegister.NecromancerSoulItem, Component.translatable("advancement.fargo_soul.necromancer_soul.title"), AdvancementType.TASK);
        builder(deathPowerRoot, ItemRegister.NinjaSoulItem, Component.translatable("advancement.fargo_soul.ninja_soul.title"), AdvancementType.TASK);
        builder(deathPowerRoot, ItemRegister.PenetratingNinjaSoulItem, Component.translatable("advancement.fargo_soul.penetrating_ninja_soul.title"), AdvancementType.TASK);

        AdvancementHolder earthPowerRoot = builder(terraSoulRoot, ItemRegister.EarthPowerItem, Component.translatable("advancement.fargo_soul.earth_power.title"), AdvancementType.GOAL);
        builder(earthPowerRoot, ItemRegister.AdamantiteSoulItem, Component.translatable("advancement.fargo_soul.adamantite_soul.title"), AdvancementType.TASK);
        builder(earthPowerRoot, ItemRegister.CobaltSoulItem, Component.translatable("advancement.fargo_soul.cobalt_soul.title"), AdvancementType.TASK);
        builder(earthPowerRoot, ItemRegister.MithrilSoulItem, Component.translatable("advancement.fargo_soul.mithril_soul.title"), AdvancementType.TASK);
        builder(earthPowerRoot, ItemRegister.OrichalcumSoulItem, Component.translatable("advancement.fargo_soul.orichalcum_soul.title"), AdvancementType.TASK);
        builder(earthPowerRoot, ItemRegister.PalladiumSoulItem, Component.translatable("advancement.fargo_soul.palladium_soul.title"), AdvancementType.TASK);
        builder(earthPowerRoot, ItemRegister.TitaniumSoulItem, Component.translatable("advancement.fargo_soul.titanium_soul.title"), AdvancementType.TASK);

        AdvancementHolder forestPowerRoot = builder(terraSoulRoot, ItemRegister.ForestPowerItem, Component.translatable("advancement.fargo_soul.forest_power.title"), AdvancementType.GOAL);
        builder(forestPowerRoot, ItemRegister.EbonyWoodSoulItem, Component.translatable("advancement.fargo_soul.ebony_wood_soul.title"), AdvancementType.TASK);
        builder(forestPowerRoot, ItemRegister.PalmWoodSoulItem, Component.translatable("advancement.fargo_soul.palm_wood_soul.title"), AdvancementType.TASK);
        builder(forestPowerRoot, ItemRegister.PearlWoodSoulItem, Component.translatable("advancement.fargo_soul.pearl_wood_soul.title"), AdvancementType.TASK);
        builder(forestPowerRoot, ItemRegister.PineWoodSoulItem, Component.translatable("advancement.fargo_soul.pine_wood_soul.title"), AdvancementType.TASK);
        builder(forestPowerRoot, ItemRegister.RoseWoodSoulItem, Component.translatable("advancement.fargo_soul.rose_wood_soul.title"), AdvancementType.TASK);
        builder(forestPowerRoot, ItemRegister.ShadowWoodSoulItem, Component.translatable("advancement.fargo_soul.shadow_wood_soul.title"), AdvancementType.TASK);
        builder(forestPowerRoot, ItemRegister.WoodSoulItem, Component.translatable("advancement.fargo_soul.wood_soul.title"), AdvancementType.TASK);

        AdvancementHolder lifePowerRoot = builder(terraSoulRoot, ItemRegister.LifePowerItem, Component.translatable("advancement.fargo_soul.life_power.title"), AdvancementType.GOAL);
        builder(lifePowerRoot, ItemRegister.BeeSoulItem, Component.translatable("advancement.fargo_soul.bee_soul.title"), AdvancementType.TASK);
        builder(lifePowerRoot, ItemRegister.BeetleSoulItem, Component.translatable("advancement.fargo_soul.beetle_soul.title"), AdvancementType.TASK);
        builder(lifePowerRoot, ItemRegister.PumpkinSoulItem, Component.translatable("advancement.fargo_soul.pumpkin_soul.title"), AdvancementType.TASK);
        builder(lifePowerRoot, ItemRegister.SpiderSoulItem, Component.translatable("advancement.fargo_soul.spider_soul.title"), AdvancementType.TASK);
        builder(lifePowerRoot, ItemRegister.TurtleSoulItem, Component.translatable("advancement.fargo_soul.turtle_soul.title"), AdvancementType.TASK);

        AdvancementHolder naturePowerRoot = builder(terraSoulRoot, ItemRegister.NaturePowerItem, Component.translatable("advancement.fargo_soul.nature_power.title"), AdvancementType.GOAL);
        builder(naturePowerRoot, ItemRegister.CrimsonSoulItem, Component.translatable("advancement.fargo_soul.crimson_soul.title"), AdvancementType.TASK);
        builder(naturePowerRoot, ItemRegister.LavaSoulItem, Component.translatable("advancement.fargo_soul.lava_soul.title"), AdvancementType.TASK);
        builder(naturePowerRoot, ItemRegister.RainCloudSoulItem, Component.translatable("advancement.fargo_soul.rain_cloud_soul.title"), AdvancementType.TASK);
        builder(naturePowerRoot, ItemRegister.FrostSoulItem, Component.translatable("advancement.fargo_soul.frost_soul.title"), AdvancementType.TASK);
        builder(naturePowerRoot, ItemRegister.GreenSoulItem, Component.translatable("advancement.fargo_soul.green_soul.title"), AdvancementType.TASK);
        builder(naturePowerRoot, ItemRegister.MushroomSoulItem, Component.translatable("advancement.fargo_soul.mushroom_soul.title"), AdvancementType.TASK);

        AdvancementHolder spiritPowerRoot = builder(terraSoulRoot, ItemRegister.SpiritPowerItem, Component.translatable("advancement.fargo_soul.spirit_power.title"), AdvancementType.GOAL);
        builder(spiritPowerRoot, ItemRegister.ForbiddenSoulItem, Component.translatable("advancement.fargo_soul.forbidden_soul.title"), AdvancementType.TASK);
        builder(spiritPowerRoot, ItemRegister.HolySoulItem, Component.translatable("advancement.fargo_soul.holy_soul.title"), AdvancementType.TASK);
        builder(spiritPowerRoot, ItemRegister.AncientHolySoulItem, Component.translatable("advancement.fargo_soul.ancient_holy_soul.title"), AdvancementType.TASK);
        builder(spiritPowerRoot, ItemRegister.TekeSoulItem, Component.translatable("advancement.fargo_soul.teke_soul.title"), AdvancementType.TASK);
        builder(spiritPowerRoot, ItemRegister.GhostSoulItem, Component.translatable("advancement.fargo_soul.ghost_soul.title"), AdvancementType.TASK);

        AdvancementHolder terraPowerRoot = builder(terraSoulRoot, ItemRegister.TerraPowerItem, Component.translatable("advancement.fargo_soul.terra_power.title"), AdvancementType.GOAL);
        builder(terraPowerRoot, ItemRegister.CopperSoulItem, Component.translatable("advancement.fargo_soul.copper_soul.title"), AdvancementType.TASK);
        builder(terraPowerRoot, ItemRegister.TinSoulItem, Component.translatable("advancement.fargo_soul.tin_soul.title"), AdvancementType.TASK);
        builder(terraPowerRoot, ItemRegister.IronSoulItem, Component.translatable("advancement.fargo_soul.iron_soul.title"), AdvancementType.TASK);
        builder(terraPowerRoot, ItemRegister.LeadSoulItem, Component.translatable("advancement.fargo_soul.lead_soul.title"), AdvancementType.TASK);
        builder(terraPowerRoot, ItemRegister.SilverSoulItem, Component.translatable("advancement.fargo_soul.silver_soul.title"), AdvancementType.TASK);
        builder(terraPowerRoot, ItemRegister.TungstenSoulItem, Component.translatable("advancement.fargo_soul.tungsten_soul.title"), AdvancementType.TASK);
        builder(terraPowerRoot, ItemRegister.ObsidianSoulItem, Component.translatable("advancement.fargo_soul.obsidian_soul.title"), AdvancementType.TASK);

        AdvancementHolder willPowerRoot = builder(terraSoulRoot, ItemRegister.WillPowerItem, Component.translatable("advancement.fargo_soul.will_power.title"), AdvancementType.GOAL);
        builder(willPowerRoot, ItemRegister.GoldSoulItem, Component.translatable("advancement.fargo_soul.gold_soul.title"), AdvancementType.TASK);
        builder(willPowerRoot, ItemRegister.PlatinumSoulItem, Component.translatable("advancement.fargo_soul.platinum_soul.title"), AdvancementType.TASK);
        builder(willPowerRoot, ItemRegister.GladiatorSoulItem, Component.translatable("advancement.fargo_soul.gladiator_soul.title"), AdvancementType.TASK);
        builder(willPowerRoot, ItemRegister.RedRidingSoulItem, Component.translatable("advancement.fargo_soul.red_riding_soul.title"), AdvancementType.TASK);
        builder(willPowerRoot, ItemRegister.ValhallaKnightSoulItem, Component.translatable("advancement.fargo_soul.valhalla_knight_soul.title"), AdvancementType.TASK);
    }

}
