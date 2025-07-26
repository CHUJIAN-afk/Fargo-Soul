package First.fargo_soul.Dadageneeator;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.Souls;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Fargo_soul.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        basicItem(Souls.WoodSoul.get());
        basicItem(Souls.PineWoodSoul.get());
        basicItem(Souls.RosewoodSoul.get());
        basicItem(Souls.EbonyWoodSoul.get());
        basicItem(Souls.ShadowWoodSoul.get());
        basicItem(Souls.PalmWoodSoul.get());
        basicItem(Souls.PearlWoodSoul.get());
        basicItem(Souls.ForestPower.get());

        basicItem(Souls.AshWoodSoul.get());
        basicItem(Souls.ObsidianSoul.get());
        basicItem(Souls.CopperSoul.get());
        basicItem(Souls.TinSoul.get());
        basicItem(Souls.IronSoul.get());
        basicItem(Souls.LeadSoul.get());
        basicItem(Souls.SilverSoul.get());
        basicItem(Souls.TungstenSoul.get());
        basicItem(Souls.TerraPower.get());

        basicItem(Souls.AncientCobaltSoul.get());
        basicItem(Souls.CobaltSoul.get());
        basicItem(Souls.PalladiumSoul.get());
        basicItem(Souls.MithrilSoul.get());
        basicItem(Souls.OrichalcumSoul.get());
        basicItem(Souls.AdamantiteSoul.get());
        basicItem(Souls.TitaniumSoul.get());
        basicItem(Souls.EarthPower.get());

        basicItem(Souls.CrimsonSoul.get());
        basicItem(Souls.LavaSoul.get());
        basicItem(Souls.RainCloudSoul.get());
        basicItem(Souls.FrostSoul.get());
        basicItem(Souls.GreenSoul.get());
        basicItem(Souls.MushroomSoul.get());
        basicItem(Souls.NaturePower.get());

        basicItem(Souls.BeeSoul.get());
        basicItem(Souls.BeetleSoul.get());
        basicItem(Souls.PumpkinSoul.get());
        basicItem(Souls.SpiderSoul.get());
        basicItem(Souls.CactusSoul.get());
        basicItem(Souls.TurtleSoul.get());
        basicItem(Souls.LifePower.get());

        basicItem(Souls.ForbiddenSoul.get());
        basicItem(Souls.HolySoul.get());
        basicItem(Souls.AncientHolySoul.get());
        basicItem(Souls.TekeSoul.get());
        basicItem(Souls.GhostSoul.get());
        basicItem(Souls.SpiritPower.get());

        basicItem(Souls.NinjaSoul.get());
        basicItem(Souls.AncientShadowSoul.get());
        basicItem(Souls.CrystalAssassinSoul.get());
        basicItem(Souls.DarkArtistSoul.get());
        basicItem(Souls.GloomySoul.get());
        basicItem(Souls.NecromancerSoul.get());
        basicItem(Souls.MonkSoul.get());
        basicItem(Souls.PenetratingNinjaSoul.get());
        basicItem(Souls.DeathPower.get());

        basicItem(Souls.GoldSoul.get());
        basicItem(Souls.PlatinumSoul.get());
        basicItem(Souls.GladiatorSoul.get());
        basicItem(Souls.RedRidingSoul.get());
        basicItem(Souls.ValhallaKnightSoul.get());
        basicItem(Souls.WillPower.get());

        basicItem(Souls.MeteorSoul.get());
        basicItem(Souls.WizardSoul.get());
        basicItem(Souls.BlazeSoul.get());
        basicItem(Souls.StardustSoul.get());
        basicItem(Souls.NebulaSoul.get());
        basicItem(Souls.VortexSoul.get());
        basicItem(Souls.CosmicPower.get());
    }
}