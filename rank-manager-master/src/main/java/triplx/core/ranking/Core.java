package triplx.core.ranking;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import triplx.core.ranking.commands.RankCommand;
import triplx.core.ranking.commands.SubCommandManager;
import triplx.core.ranking.data.MongoManager;
import triplx.core.ranking.events.PlayerChat;
import triplx.core.ranking.events.PlayerJoin;
import triplx.core.ranking.utils.Color;

public class Core extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("rank").setExecutor(new RankCommand());
        SubCommandManager.init();

        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerChat(), this);

        Bukkit.getConsoleSender().sendMessage(Color.cc("&c&l[TRIPLX CORE] &aRanking enabled!"));

        MongoManager.init();
    }

    @Override
    public void onDisable() {

    }



}
