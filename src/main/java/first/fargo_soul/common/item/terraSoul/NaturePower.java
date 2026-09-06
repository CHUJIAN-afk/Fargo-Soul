package first.fargo_soul.common.item.terraSoul;

import first.fargo_soul.common.entity.ChlorophyteOrb;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.common.item.base.ValueModifier;
import first.fargo_soul.common.item.base.ValueOperation;
import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.api.LyraHelper;
import first.lyra.common.attachment.AttachmentEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

import static first.fargo_soul.register.FargoSoulItemRegister.*;

public class NaturePower extends SoulItem {

    public NaturePower(Properties properties) {
        super(properties);
    }

    @Override
    public Set<SoulItem> getSoulItemList() {
        return Set.of(CrimsonSoulItem.get(), LavaSoulItem.get(), RainCloudSoulItem.get(), FrostSoulItem.get(), GreenSoulItem.get(), MushroomSoulItem.get());
    }

    @Override
    public void tick(Player player) {
        List<ChlorophyteOrb> orbs = LyraHelper.get(player).getEntityData().get(AttachmentEntityData.Type.ExtraMinion, SummonerAttachmentEntityRegister.CHLOROPHYTE_ORB.get()).stream().filter(orb -> orb.getKind() == ChlorophyteOrb.NATURE).toList();
        if (orbs.size() < 5) {
            ChlorophyteOrb orb = new ChlorophyteOrb(ChlorophyteOrb.NATURE, player.getBbWidth() * 1.2f, player.getBbHeight() * 0.25f);
            LyraHelper.get(player).add(AttachmentEntityData.Type.ExtraMinion, orb);
        }
    }

    @Override
    public void hurt(@Nullable Entity attacker, @NotNull Player target, @NotNull DamageContainer container, List<ValueModifier> modifiers) {
        modifiers.add(new ValueModifier(-15, ValueOperation.ADD_VALUE));
    }
}