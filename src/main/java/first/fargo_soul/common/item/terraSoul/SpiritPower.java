package first.fargo_soul.common.item.terraSoul;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import first.fargo_soul.FargoSoul;
import first.fargo_soul.common.entity.TerraBlade;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.api.LyraHelper;
import first.lyra.common.attachment.AttachmentEntityData;
import first.lyra.common.minion.MinionDamageSource;
import first.lyra.register.LyraAttributeRegister;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

import static first.fargo_soul.register.FargoSoulItemRegister.*;

public class SpiritPower extends SoulItem {

    public SpiritPower(Properties properties) {
        super(properties);
    }

    @Override
    public Set<SoulItem> getSoulItemList() {
        return Set.of(ForbiddenSoulItem.get(), HolySoulItem.get(), AncientHolySoulItem.get(), TekeSoulItem.get(), GhostSoulItem.get());
    }

    @Override
    public void healAmount(Player player, float amount, List<ValueModifier> modifiers) {
        modifiers.add(new ValueModifier(0.7f, ValueOperation.ADD_MULTIPLIED_BASE));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> map = HashMultimap.create();
        map.put(LyraAttributeRegister.SummonDamage, new AttributeModifier(FargoSoul.rl("spirit_blade_damage"), 2.0, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return map;
    }

    @Override
    public void attack(@NotNull Player attacker, @NotNull LivingEntity target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        if (container.getSource() instanceof MinionDamageSource damageSource && damageSource.getMinion().getType() == SummonerAttachmentEntityRegister.TERRA_BLADE.get()) {
            modifiers.add(new ValueModifier(2, ValueOperation.ADD_MULTIPLIED_BASE));
        }
    }

    @Override
    public void tick(Player player) {
        List<TerraBlade> blades = LyraHelper.get(player).getEntityData().get(AttachmentEntityData.Type.ExtraMinion, SummonerAttachmentEntityRegister.TERRA_BLADE.get());
        if (blades.stream().filter(terraBlade -> terraBlade.spiritPower).count() < 8) {
            TerraBlade blade = new TerraBlade();
            blade.setOwner(player);
            blade.init(blade.getInterpolatedIdleState(0));
            blade.spiritPower = true;
            LyraHelper.get(player).add(AttachmentEntityData.Type.ExtraMinion, blade);
        }
    }
}