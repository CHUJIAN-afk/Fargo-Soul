package first.fargo_soul.common.item.terraSoul.naturePower;

import first.fargo_soul.common.entity.ChlorophyteOrb;
import first.fargo_soul.common.item.base.SoulItem;
import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.api.LyraHelper;
import first.lyra.common.attachment.AttachmentEntityData;
import first.lyra.common.entity.IEntityCollision;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Set;

public class GreenSoul extends SoulItem {

    public GreenSoul(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canFly(Player player) {
        return true;
    }

    @Override
    public boolean canSprint(Player player) {
        return true;
    }

    @Override
    public void sprintCollision(Player player, List<IEntityCollision.HitContext> list, Set<LivingEntity> hits) {
        for (IEntityCollision.HitContext context : list) {
            if (hits.add(context.entity())) {
                context.entity().addEffect(new MobEffectInstance(MobEffects.POISON, 200));
            }
        }
    }

    @Override
    public void tick(Player player) {
        List<ChlorophyteOrb> orbs = LyraHelper.get(player).getEntityData().get(AttachmentEntityData.Type.ExtraMinion, SummonerAttachmentEntityRegister.CHLOROPHYTE_ORB.get()).stream().filter(orb -> orb.getKind() == ChlorophyteOrb.GREEN).toList();
        if (orbs.isEmpty()) {
            ChlorophyteOrb orb = new ChlorophyteOrb(ChlorophyteOrb.GREEN, 0, player.getBbHeight());
            LyraHelper.get(player).add(AttachmentEntityData.Type.ExtraMinion, orb);
        }
    }
}