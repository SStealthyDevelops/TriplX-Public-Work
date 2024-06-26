package triplx.hub.core.data.mongodb.collections.hub;

import lombok.Getter;

public class SettingClass {

    @Getter
    private final String xLimits, zLimits;
    @Getter
    private final Integer voidLimit;

    @Getter
    private final double spawnX, spawnY, spawnZ, yaw, pitch;

    public SettingClass(String xLimits, String zLimits, int voidLimit, double spawnX, double spawnY, double spawnZ, double yaw, double pitch) {
        this.xLimits = xLimits;
        this.zLimits = zLimits;
        this.voidLimit = voidLimit;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.spawnZ = spawnZ;
        this.yaw = yaw;
        this.pitch = pitch;
    }



}
