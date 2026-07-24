package first.fargo_soul.common.event.modEvent;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerFlyEvent extends PlayerEvent {

    private boolean allowingFly;
    private int MaxFlyTime;

    public PlayerFlyEvent(Player player) {
        super(player);
        this.allowingFly = false;
        this.MaxFlyTime = 60;
    }

    public int getMaxFlyTime() {
        return MaxFlyTime;
    }

    public void setMaxFlyTime(int flyTime) {
        this.MaxFlyTime = flyTime;
    }

    public void addMaxFlyTime(int flyTime) {
        this.MaxFlyTime += flyTime;
    }

    public boolean isAllowingFly() {
        return allowingFly;
    }

    public void setAllowingFly(boolean allowingFly) {
        this.allowingFly = allowingFly;
    }

}
