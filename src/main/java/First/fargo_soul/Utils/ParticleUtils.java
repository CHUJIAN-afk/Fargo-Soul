package First.fargo_soul.Utils;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class ParticleUtils {
    private static final RandomSource RANDOM = RandomSource.create();

    /**
     * 生成两点之间的直线粒子
     * @param level      世界
     * @param start      起点坐标
     * @param end        终点坐标
     * @param particle   粒子类型
     * @param density    粒子密度（每格距离的粒子数）
     * @param jitter     位置随机偏移量（0=完全直线）
     */
    public static void spawnParticleLine(ServerLevel level, Vec3 start, Vec3 end, ParticleOptions particle, double density, float jitter) {
        double distance = start.distanceTo(end);
        int particles = (int) (distance * density);
        for (int i = 0; i <= particles; i++) {
            double ratio = i / (double) particles;
            Vec3 pos = start.lerp(end, ratio);
            if (jitter > 0) {
                pos = pos.add(
                        (RANDOM.nextDouble() - 0.5) * jitter,
                        (RANDOM.nextDouble() - 0.5) * jitter,
                        (RANDOM.nextDouble() - 0.5) * jitter
                );
            }
            level.sendParticles(particle, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0.01);
        }
    }












    /**
     * 生成3D球形粒子群（球面环绕 + 球体内随机粒子）
     *
     * @param level          世界
     * @param centerX        球心X
     * @param centerY        球心Y
     * @param centerZ        球心Z
     * @param particle       粒子类型
     * @param maxRadius      最大半径
     * @param totalParticles 总粒子数（球面+内部）
     * @param innerRatio     内部粒子占比（0.2 = 20%粒子在内部）
     */
    public static void spawnParticleSphere(ServerLevel level,
                                           double centerX,
                                           double centerY,
                                           double centerZ,
                                           ParticleOptions particle,
                                           float maxRadius,
                                           int totalParticles,
                                           float innerRatio) {

        // 球面环绕粒子（80%）
        int surfaceParticles = (int) (totalParticles * (1 - innerRatio));
        for (int i = 0; i < surfaceParticles; i++) {
            // 随机球面坐标（均匀分布）
            double theta = RANDOM.nextDouble() * 2 * Math.PI; // 水平角
            double phi = Math.acos(2 * RANDOM.nextDouble() - 1); // 俯仰角
            double r = maxRadius * (0.8 + 0.2 * RANDOM.nextDouble()); // 随机半径波动

            double x = centerX + r * Math.sin(phi) * Math.cos(theta);
            double y = centerY + r * Math.sin(phi) * Math.sin(theta);
            double z = centerZ + r * Math.cos(phi);

            level.sendParticles(particle, x, y, z, 1, 0.1, 0.1, 0.1, 0.02);
        }

        // 球体内随机粒子（20%）
        int innerParticles = totalParticles - surfaceParticles;
        for (int i = 0; i < innerParticles; i++) {
            // 随机球体内坐标（均匀分布）
            double r = maxRadius * Math.pow(RANDOM.nextDouble(), 1 / 3.0); // 立方根保证均匀
            double theta = RANDOM.nextDouble() * 2 * Math.PI;
            double phi = Math.acos(2 * RANDOM.nextDouble() - 1);

            double x = centerX + r * Math.sin(phi) * Math.cos(theta);
            double y = centerY + r * Math.sin(phi) * Math.sin(theta);
            double z = centerZ + r * Math.cos(phi);

            level.sendParticles(particle, x, y, z, 1, 0, 0, 0, 0);
        }
    }
}

