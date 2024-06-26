package minigames.triplxmc.skydomination.map;

import core.triplxmc.world.api.WorldAPI;
import core.triplxmc.world.manager.TWorld;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import triplx.core.api.chat.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapManager {

    @Getter
    @Setter
    private static MapManager instance;

    @Getter
    private List<GameMap> maps;

    @Getter
    private List<GameMap> activeMaps;

    public void init() {
        maps = new ArrayList<>();
        activeMaps = new ArrayList<>();



    }

    public GameMap getMap(String world) {
        world = world.replace("_active", "");
        for (GameMap m : maps) {
            if (m.getWorld().equals(world)) {
                return m;
            }
        }
        Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not find world: " + world + ". The search is case-sensitive!"));
        return null;
    }

    public void addMap(GameMap map) {
        maps.add(map);
    }

    public World createActiveWorld(GameMap map) throws IOException {
        WorldAPI.getInstance().addWorld(new TWorld(Bukkit.getWorld(map.getWorld()), map.getWorld() + "_active", 90, false));
        return Bukkit.getWorld(map.getWorld() + "_active");
    }

    public void deleteActiveWorld(GameMap map) {
        Bukkit.unloadWorld(map.getWorld() + "active", false);
        activeMaps.remove(map);
        WorldAPI.getInstance().deleteWorld(map.getWorld() + "_active");
    }

    public void deleteActiveWorldNoArray(GameMap map) {
        WorldAPI.getInstance().deleteWorld(map.getWorld() + "_active");
    }

    public GameMap getAvailableMap() {
        for (GameMap map : maps) {
            if (!activeMaps.contains(map)) {
                return map;
            }
        }
        return null;
    }

    public void deleteAllActiveWorlds() {
        for (GameMap map : activeMaps) {
            deleteActiveWorldNoArray(map);
        }
        activeMaps.clear();
    }
}
