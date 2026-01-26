package First.fargo_soul.common.event.modEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public abstract class SprintEvent extends PlayerEvent {

    public SprintEvent(Player player) {
        super(player);
    }

    public static class Client extends SprintEvent {

        private boolean isSprinting;
        private Vec3 vec3;

        public Client(Player player, Vec3 vec3) {
            super(player);
            this.vec3 = vec3;
            this.isSprinting = false;
        }

        public Vec3 getVec3() {
            return vec3;
        }

        public void setVec3(Vec3 vec3) {
            this.vec3 = vec3;
        }

        public boolean isSprinting() {
            return isSprinting;
        }

        public void setSprinting(boolean sprinting) {
            isSprinting = sprinting;
        }

    }

    public static class Server extends SprintEvent {

        public Server(Player player) {
            super(player);
        }

    }

}
