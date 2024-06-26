package minigames.triplxmc.skydomination.map.constructors;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;

public class NoWorldLocation {

    @Getter
    @Setter
    private int x, y, z;

    public NoWorldLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Location getLocationWithWorld(World world, NoWorldLocation loc) {
        return new Location(world, loc.x, loc.y, loc.z);
    }

}
