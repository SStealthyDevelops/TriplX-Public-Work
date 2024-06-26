package minigames.triplxmc.skydomination.map;

import lombok.Getter;
import minigames.triplxmc.skydomination.map.constructors.Base;
import minigames.triplxmc.skydomination.map.constructors.NoWorldLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Set;

@Getter
public class GameMap {

    private final String mapName;
    private final String world;
    private final String[] teamOneSpawn;
    private final String[] teamTwoSpawn;

    private final String bordersX;
    private final String bordersY;
    private final String bordersZ;

    private final Base[] bases;

    private final Material[] unbreakableBlocks;

    private final Location[] teamOneSpawnLocs;
    private final Location[] teamTwoSpawnLocs;

    private final String lobbySpawnpoint;

    private final Location lobbySpawnLocation;

    private final Set<NoWorldLocation> personalShops, teamShops;

    public GameMap(String mapName, String world, String[] teamOneSpawn, String[] teamTwoSpawn, String bordersX, String bordersY, String bordersZ, Material[] unbreakableBlocks, Base[] bases, String lobbySpawnpoint, Set<NoWorldLocation> personalShops, Set<NoWorldLocation> teamShops) {
        this.mapName = mapName;
        this.world = world;
        this.teamOneSpawn = teamOneSpawn;
        this.teamTwoSpawn = teamTwoSpawn;
        this.bordersX = bordersX;
        this.bordersY = bordersY;
        this.bordersZ = bordersZ;
        this.unbreakableBlocks = unbreakableBlocks;
        this.bases = bases;
        this.teamShops = teamShops;
        this.personalShops = personalShops;

        ArrayList<Location> locations1 = new ArrayList<>();
        for (String string : teamOneSpawn) {
            String[] ints = string.split(" ");
            locations1.add(new Location(Bukkit.getWorld(world), Integer.parseInt(ints[0]), Integer.parseInt(ints[1]), Integer.parseInt(ints[2])));
        }

        this.teamOneSpawnLocs = locations1.toArray(new Location[0]);

        ArrayList<Location> locations2 = new ArrayList<>();
        for (String string : teamTwoSpawn) {
            String[] ints = string.split(" ");
            locations2.add(new Location(Bukkit.getWorld(world), Integer.parseInt(ints[0]), Integer.parseInt(ints[1]), Integer.parseInt(ints[2])));
        }

        this.teamTwoSpawnLocs = locations2.toArray(new Location[0]);

        this.lobbySpawnpoint = lobbySpawnpoint;

        String[] ar = this.lobbySpawnpoint.split(" ");

        this.lobbySpawnLocation = new Location(Bukkit.getWorld(world), Double.parseDouble(ar[0]), Double.parseDouble(ar[1]), Double.parseDouble(ar[2]), (float) Double.parseDouble(ar[3]), (float) Double.parseDouble(ar[4]));
    }


    public Base getBase(Location location) {
        for (Base b : bases) {
            for (NoWorldLocation block : b.getBlocks()) {
                if (location.getBlock().getLocation().equals(NoWorldLocation.getLocationWithWorld(Bukkit.getWorld(world + "_active"), block))) {
                    return b;
                }
            }
        }
        return null;
    }



}
