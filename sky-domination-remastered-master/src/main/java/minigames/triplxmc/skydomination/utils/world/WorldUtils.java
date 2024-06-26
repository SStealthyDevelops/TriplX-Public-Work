package minigames.triplxmc.skydomination.utils.world;

import minigames.triplxmc.skydomination.map.constructors.NoWorldLocation;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorldUtils {

    public static Set<Location> blocksFromTwoPoints(Location loc1, Location loc2) {
        Set<Location> blocks = new HashSet<>();

        int topBlockX = (Math.max(loc1.getBlockX(), loc2.getBlockX()));
        int bottomBlockX = (Math.min(loc1.getBlockX(), loc2.getBlockX()));

        int topBlockY = (Math.max(loc1.getBlockY(), loc2.getBlockY()));
        int bottomBlockY = (Math.min(loc1.getBlockY(), loc2.getBlockY()));

        int topBlockZ = (Math.max(loc1.getBlockZ(), loc2.getBlockZ()));
        int bottomBlockZ = (Math.min(loc1.getBlockZ(), loc2.getBlockZ()));

        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);

                    blocks.add(block.getLocation());
                    System.out.println("BASE: " + block.getLocation().getX() + " " + block.getLocation().getY() + " " + block.getZ());
                }
            }
        }

        return blocks;
    }

    public static Set<NoWorldLocation> blocksFromTwoPoints(NoWorldLocation loc1, NoWorldLocation loc2) {
        Set<NoWorldLocation> blocks = new HashSet<>();

        int topBlockX = (Math.max(loc1.getX(), loc2.getX()));
        int bottomBlockX = (Math.min(loc1.getX(), loc2.getX()));

        int topBlockY = (Math.max(loc1.getY(), loc2.getY()));
        int bottomBlockY = (Math.min(loc1.getY(), loc2.getY()));

        int topBlockZ = (Math.max(loc1.getZ(), loc2.getZ()));
        int bottomBlockZ = (Math.min(loc1.getZ(), loc2.getZ()));

        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {
                    NoWorldLocation block = new NoWorldLocation(x, y , z);

                    blocks.add(block);
                }
            }
        }

        return blocks;
    }


}
