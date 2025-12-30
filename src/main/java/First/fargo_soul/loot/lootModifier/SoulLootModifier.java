package First.fargo_soul.loot.lootModifier;

import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.item.terraSoul.WillPower;
import First.fargo_soul.item.terraSoul.willPower.PlatinumSoul;
import First.fargo_soul.utils.CurioUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class SoulLootModifier extends LootModifier {

    public static final MapCodec<SoulLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, SoulLootModifier::new));

    public SoulLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        Entity entity = context.getParamOrNull(LootContextParams.ATTACKING_ENTITY);
        if (entity instanceof LivingEntity attacker) {
            if (CurioUtils.isEquipped(attacker, PlatinumSoul.class)) {
                if (attacker.getRandom().nextDouble() < 0.2) {
                    for (ItemStack stack : generatedLoot) {
                        if (stack.getItem() instanceof SoulItem) {
                            int scale = CurioUtils.isEquipped(attacker, WillPower.class) ? 8 : 5;
                            stack.setCount(stack.getCount() * scale);
                        }
                    }
                }
            }
        }
        return generatedLoot;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

}
