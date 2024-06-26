package minigames.triplxmc.skydomination;

import lombok.Getter;
import minigames.triplxmc.skydomination.commands.GameCommand;
import minigames.triplxmc.skydomination.commands.SubCommandManager;
import minigames.triplxmc.skydomination.data.mongo.AchievementsData;
import minigames.triplxmc.skydomination.data.mongo.HeroStats;
import minigames.triplxmc.skydomination.data.mongo.MongoManager;
import minigames.triplxmc.skydomination.data.mongo.OverallStats;
import minigames.triplxmc.skydomination.events.EventHandler;
import minigames.triplxmc.skydomination.game.GameManager;
import minigames.triplxmc.skydomination.map.JSONManager;
import minigames.triplxmc.skydomination.map.MapManager;
import minigames.triplxmc.skydomination.player.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import triplx.core.api.chat.Color;
import triplx.core.api.data.server.ServerManager;

import java.io.IOException;

public class Core extends JavaPlugin {

    @Getter
    private static Core instance;

    @Getter
    private final String serverName = getConfig().getString("server-name");
    @Getter
    private final String bungeeName = getConfig().getString("server-bungee");

    @Override
    public void onEnable() {

        try {
            getConfig().options().copyDefaults(true);
            saveConfig();

            if (!getConfig().getBoolean("enabled")) {
                Bukkit.getConsoleSender().sendMessage(Color.cc("&c-------------------------"));
                Bukkit.getConsoleSender().sendMessage(Color.cc("&c&lSKY DOMINATION DISABLED"));
                Bukkit.getConsoleSender().sendMessage(Color.cc("&cv" + getDescription().getVersion() + "; Developed by SStealthy (c) TriplX 2020"));
                Bukkit.getConsoleSender().sendMessage(Color.cc("&c-------------------------"));
                return;
            }

            instance = this;

            Bukkit.getScheduler().runTaskLater(this, () -> ServerManager.getInstance().registerServer("skydomination-servers", serverName, bungeeName), 0L);

            Bukkit.getScheduler().runTaskLater(this, () -> {
                try {
                    init();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, 4L);

        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cError loading Sky Domination " + getDescription().getVersion() + "&c. Shutting down."));
            getServer().shutdown();
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.kickPlayer(Color.cc("&cServer reboot."));
        }
        MapManager.getInstance().deleteAllActiveWorlds();
    }

    private void init() throws IOException {
         MapManager.setInstance(new MapManager());
            MapManager.getInstance().init();

            JSONManager.setInstance(new JSONManager());
            JSONManager.getInstance().init();


            GameManager.setInstance(new GameManager());
            GameManager.getInstance().init();

            ProfileManager.setInstance(new ProfileManager());
            ProfileManager.getInstance().init();

            new EventHandler(this);

            SubCommandManager.init();

            getCommand("game").setExecutor(new GameCommand());


            MongoManager.init();
            OverallStats.setInstance(new OverallStats());
            HeroStats.setInstance(new HeroStats());
            AchievementsData.setInstance(new AchievementsData());
//        }
    }

}
