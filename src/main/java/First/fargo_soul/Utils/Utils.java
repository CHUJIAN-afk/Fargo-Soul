package First.fargo_soul.Utils;

import First.fargo_soul.Fargo_soul;
import First.fargo_soul.Item.Soul.BaseSoul.SoulItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import java.util.Random;

public class Utils {
    public static final Random random = new Random();


    public static BlockHitResult getTargetedBlock(Player player, double maxDistance) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getLookAngle();
        Vec3 endPos = eyePos.add(lookVec.scale(maxDistance));
        ClipContext clipContext = new ClipContext(eyePos, endPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
        return player.level().clip(clipContext);
    }

    /**
     * 只需添加主魂石，即可将子魂石全部添加到战利品表中
     * @param event
     * @param itemRegistry
     * @param lootPool
     */
    public static void AddLootTable(LootTableLoadEvent event, Registry<Item> itemRegistry,LootPool.Builder lootPool) {
        LootTable lootTable = event.getTable();
        LootContextParamSet paramSet = lootTable.getParamSet();
        if (paramSet.equals(LootContextParamSets.CHEST) || paramSet.equals(LootContextParamSets.VAULT)) {
            lootPool.when(LootItemRandomChanceCondition.randomChance(0.05f));
            lootPool.setRolls(ConstantValue.exactly(1));
            lootPool.setBonusRolls(ConstantValue.exactly(itemRegistry.size()));
            for (Item item : itemRegistry) {
                if (item.asItem() instanceof SoulItem soulItem && soulItem.getCurioItemList().size() < 2) {
                    lootPool.add(LootItem.lootTableItem(soulItem).setWeight(1));
                }
            }
            lootTable.addPool(lootPool.build());
        }
    }

    public static ResourceLocation FargoResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(Fargo_soul.MODID, path);
    }

}
