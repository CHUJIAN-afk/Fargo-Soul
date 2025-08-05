package First.fargo_soul.Dadageneeator;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.SoulsRegister;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Fargo_soul.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //魔石
        basicItem(SoulsRegister.WoodSoul.get());
        basicItem(SoulsRegister.PineWoodSoul.get());
        basicItem(SoulsRegister.RosewoodSoul.get());
        basicItem(SoulsRegister.EbonyWoodSoul.get());
        basicItem(SoulsRegister.ShadowWoodSoul.get());
        basicItem(SoulsRegister.PalmWoodSoul.get());
        basicItem(SoulsRegister.PearlWoodSoul.get());
        basicItem(SoulsRegister.ForestPower.get());

        basicItem(SoulsRegister.AshWoodSoul.get());
        basicItem(SoulsRegister.ObsidianSoul.get());
        basicItem(SoulsRegister.CopperSoul.get());
        basicItem(SoulsRegister.TinSoul.get());
        basicItem(SoulsRegister.IronSoul.get());
        basicItem(SoulsRegister.LeadSoul.get());
        basicItem(SoulsRegister.SilverSoul.get());
        basicItem(SoulsRegister.TungstenSoul.get());
        basicItem(SoulsRegister.TerraPower.get());

        basicItem(SoulsRegister.AncientCobaltSoul.get());
        basicItem(SoulsRegister.CobaltSoul.get());
        basicItem(SoulsRegister.PalladiumSoul.get());
        basicItem(SoulsRegister.MithrilSoul.get());
        basicItem(SoulsRegister.OrichalcumSoul.get());
        basicItem(SoulsRegister.AdamantiteSoul.get());
        basicItem(SoulsRegister.TitaniumSoul.get());
        basicItem(SoulsRegister.EarthPower.get());

        basicItem(SoulsRegister.CrimsonSoul.get());
        basicItem(SoulsRegister.LavaSoul.get());
        basicItem(SoulsRegister.RainCloudSoul.get());
        basicItem(SoulsRegister.FrostSoul.get());
        basicItem(SoulsRegister.GreenSoul.get());
        basicItem(SoulsRegister.MushroomSoul.get());
        basicItem(SoulsRegister.NaturePower.get());

        basicItem(SoulsRegister.BeeSoul.get());
        basicItem(SoulsRegister.BeetleSoul.get());
        basicItem(SoulsRegister.PumpkinSoul.get());
        basicItem(SoulsRegister.SpiderSoul.get());
        basicItem(SoulsRegister.CactusSoul.get());
        basicItem(SoulsRegister.TurtleSoul.get());
        basicItem(SoulsRegister.LifePower.get());

        basicItem(SoulsRegister.ForbiddenSoul.get());
        basicItem(SoulsRegister.HolySoul.get());
        basicItem(SoulsRegister.AncientHolySoul.get());
        basicItem(SoulsRegister.TekeSoul.get());
        basicItem(SoulsRegister.GhostSoul.get());
        basicItem(SoulsRegister.SpiritPower.get());

        basicItem(SoulsRegister.NinjaSoul.get());
        basicItem(SoulsRegister.AncientShadowSoul.get());
        basicItem(SoulsRegister.CrystalAssassinSoul.get());
        basicItem(SoulsRegister.DarkArtistSoul.get());
        basicItem(SoulsRegister.GloomySoul.get());
        basicItem(SoulsRegister.NecromancerSoul.get());
        basicItem(SoulsRegister.MonkSoul.get());
        basicItem(SoulsRegister.PenetratingNinjaSoul.get());
        basicItem(SoulsRegister.DeathPower.get());

        basicItem(SoulsRegister.GoldSoul.get());
        basicItem(SoulsRegister.PlatinumSoul.get());
        basicItem(SoulsRegister.GladiatorSoul.get());
        basicItem(SoulsRegister.RedRidingSoul.get());
        basicItem(SoulsRegister.ValhallaKnightSoul.get());
        basicItem(SoulsRegister.WillPower.get());

        basicItem(SoulsRegister.MeteorSoul.get());
        basicItem(SoulsRegister.WizardSoul.get());
        basicItem(SoulsRegister.BlazeSoul.get());
        basicItem(SoulsRegister.StardustSoul.get());
        basicItem(SoulsRegister.NebulaSoul.get());
        basicItem(SoulsRegister.VortexSoul.get());
        basicItem(SoulsRegister.CosmicPower.get());













    }
}