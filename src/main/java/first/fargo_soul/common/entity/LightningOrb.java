package first.fargo_soul.common.entity;

import first.fargo_soul.common.attachment.SoulTargetCache;
import first.fargo_soul.register.SummonerAttachmentEntityRegister;
import first.lyra.common.attachmentEntity.SyncFieldDispatcher;
import first.lyra.common.particle.genericParticle.GenericParticleBuilder;
import first.lyra.utils.ParticleHelper;
import first.lyra.common.projectile.Projectile;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LightningOrb extends Projectile {

    private static final StreamCodec<RegistryFriendlyByteBuf, Set<Integer>> STREAM_CODEC = StreamCodec.of(
            (buf, ids) -> {
                buf.writeVarInt(ids.size());
                for (Integer id : ids) {
                    buf.writeVarInt(id);
                }
            },
            buf -> {
                int size = buf.readVarInt();
                Set<Integer> ids = new HashSet<>(size);
                for (int i = 0; i < size; i++) {
                    ids.add(buf.readVarInt());
                }
                return ids;
            }
    );

    private final Set<Integer> idList = new HashSet<>();

    public LightningOrb() {
        super(SummonerAttachmentEntityRegister.LIGHTNING_ORB);
        setGravity(0);
        setDrag(1f);
        setMaxTickCount(40);
    }

    @Override
    protected void registerSyncFields(SyncFieldDispatcher fields) {
        super.registerSyncFields(fields);
        fields.field(STREAM_CODEC, this::getIdList, value -> {
            idList.clear();
            idList.addAll(value);
        });
    }

    @Override
    public void tick() {
        super.tick();
        Level level = owner.level();
        idList.clear();
        List<LivingEntity> entities = SoulTargetCache.get(owner).getEntitiesInRadius(getPos(), 3, null);
        for (LivingEntity living : entities) {
            idList.add(living.getId());
            attack(living, getDamage(), 3);
            ParticleHelper.create(level)
                    .generic(GenericParticleBuilder.create()
                            .centerColor(0x38ffec)
                            .edgeColor(0x2fc1ae)
                            .lifetime(5)
                            .lifetimeRandom(5)
                            .spin(0.3f)
                            .spinRandom(0.05F)
                            .friction(0.75F)
                            .scale(0.025f)
                            .scaleRandom(0.005f)
                    )
                    .pos(living.getBoundingBox().getCenter())
                    .offset(0.15)
                    .velocity(getVelocity())
                    .count(1)
                    .speed(0.25)
                    .spread(2)
                    .emit();
        }
    }

    @Override
    public void onRemove() {
        ParticleHelper.create(owner.level())
                .generic(GenericParticleBuilder.create()
                        .centerColor(0x38ffec)
                        .edgeColor(0x2fc1ae)
                        .lifetime(5)
                        .lifetimeRandom(5)
                        .spin(0.3f)
                        .spinRandom(0.05F)
                        .friction(0.75F)
                        .scale(0.025f)
                        .scaleRandom(0.005f)
                )
                .pos(getPos())
                .offset(0.15)
                .velocity(getVelocity())
                .count(4)
                .speed(0.25)
                .spread(2)
                .emit();
    }

    public Set<Integer> getIdList() {
        return idList;
    }

    @Override
    public DamageSource getDamageSource() {
        Player owner = getOwner();
        return owner.damageSources().lightningBolt();
    }
}
