package first.fargo_soul.common.soulInfo;

import first.fargo_soul.common.attachment.SoulInfoData;
import first.fargo_soul.register.FargoSoulSoulInfoRegister;
import net.minecraft.world.entity.projectile.Projectile;

public class ProjectileInfo extends SoulInfo {

    public static void set(Projectile projectile) {
        SoulInfoData.putSoulInfo(projectile, new ProjectileInfo());
    }

    @Override
    public SoulInfoType<? extends SoulInfo> getType() {
        return FargoSoulSoulInfoRegister.PROJECTILE_INFO.get();
    }
}
