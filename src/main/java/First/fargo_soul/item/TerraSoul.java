package First.fargo_soul.item;

import First.fargo_soul.attachment.SoulAbilityData;
import First.fargo_soul.client.gui.SoulGuiRenderManager;
import First.fargo_soul.item.base.SoulItem;
import First.fargo_soul.utils.CurioUtils;
import First.fargo_soul.utils.ParticleUtils;
import First.fargo_soul.utils.SoulUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

import static First.fargo_soul.register.ItemRegister.*;

public class TerraSoul extends SoulItem {

    public TerraSoul(Properties properties) {
        super(properties);
    }

    @Override
    public List<SoulItem> getSoulItemList() {
        return List.of(
                CosmicPowerItem.get(),
                DeathPowerItem.get(),
                EarthPowerItem.get(),
                ForestPowerItem.get(),
                LifePowerItem.get(),
                NaturePowerItem.get(),
                SpiritPowerItem.get(),
                TerraPowerItem.get(),
                WillPowerItem.get()
        );
    }

    private static final List<EntityType<?>> typeList = new ArrayList<>();

    @Override
    public void tick(LivingEntity ticker) {
        Level level = ticker.level();
        if (!level.isClientSide() && CurioUtils.isEquipped(ticker, TerraSoul.class)) {
            SoulAbilityData.SoulInfo soulInfo = getInfo(ticker, TerraSoul.class);
            soulInfo.setMaxCooldown(1);
            if (soulInfo.isReady() && SoulUtils.getSoulTarget(ticker, 20) instanceof LivingEntity target) {
                RandomSource random = ticker.getRandom();
                List<EntityType<? extends Entity>> types = List.of(
                        EntityType.ARROW,
                        EntityType.SPECTRAL_ARROW,
                        EntityType.SNOWBALL,
                        EntityType.FIREBALL,
                        EntityType.SMALL_FIREBALL,
                        //EntityType.WITHER_SKULL,
                        EntityType.DRAGON_FIREBALL,
                        EntityType.SHULKER_BULLET
                );
                if (types.get(random.nextInt(types.size())).create(level) instanceof Projectile projectile) {
                    soulInfo.setCooldown(soulInfo.getMaxCooldown());
                    SoulUtils.shootTargetFromAttaker(projectile, ticker, target, ticker.getBoundingBox().getSize() * 8, 4);
                    SoulUtils.setAbilityInvulnerable(projectile);
                    ParticleUtils.spawnParticleSphere(
                            (ServerLevel) level,
                            projectile.position(),
                            ParticleTypes.SCULK_SOUL,
                            0.2f,
                            10,
                            0.5f
                    );
                }
            }
        }
    }

    @Override
    public void getSoulRenderInfo(SoulGuiRenderManager.SoulRenderManager soulRenderManager) {
        //soulRenderManager.add(this, TerraSoul.class, SoulRenderType.Cooldown);
    }

}
