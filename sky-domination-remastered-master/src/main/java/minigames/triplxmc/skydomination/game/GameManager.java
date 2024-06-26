package minigames.triplxmc.skydomination.game;

import lombok.Getter;
import lombok.Setter;
import minigames.triplxmc.skydomination.Core;
import minigames.triplxmc.skydomination.map.GameMap;
import minigames.triplxmc.skydomination.map.MapManager;
import minigames.triplxmc.skydomination.player.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.core.api.data.server.ServerManager;

import java.io.IOException;
import java.util.HashMap;

public class GameManager {

    @Getter
    private HashMap<World, Game> activeGames;

    @Setter
    @Getter
    private static GameManager instance;

    public void init() throws IOException {
        activeGames = new HashMap<>();

        createGame();
    }

    public Game getGame(final Player player) {
        for (Game game : getActiveGames().values()) {
//            System.out.println("game: " + game.getActiveWorld().toString());
            if (game.getPlayers().contains(ProfileManager.getInstance().getProfile(player.getUniqueId()))) {
                return game;
            }
        }
        Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not find game of player " + player.getName() + " in world " + player.getWorld().getName()));
        return null;
    }

    public Game getLobbyGame() {
        if (activeGames.size() == 0) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cNo active games to join."));
        }
        for (Game game : activeGames.values()) {
            if (game.getState() == GameState.LOBBY) {
                ServerManager.getInstance().setAvailable();
                return game;
            }
        }
        ServerManager.getInstance().setUnavailable();
        return null;
    }


    public void createGame() throws IOException {
        GameMap map = MapManager.getInstance().getAvailableMap();
        if (map == null) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not create a new game.. All maps are being used."));
            ServerManager.getInstance().setUnavailable();
            return;
        }
        ServerManager.getInstance().setAvailable();
        Game g = new Game(map);

        MapManager.getInstance().getActiveMaps().add(map);
        activeGames.put(Bukkit.getWorld(map.getWorld() + "_active"), g);
    }

    public void endGame(Game game) {
        activeGames.remove(game.getActiveWorld());
        MapManager.getInstance().deleteActiveWorld(game.getMap());

        Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> {
            try {
                createGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 60L);
        // game instance is nulled, new game instance is created on a random map
    }

}
