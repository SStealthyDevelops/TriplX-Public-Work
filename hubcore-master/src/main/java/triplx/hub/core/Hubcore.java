package triplx.hub.core;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import triplx.core.api.TriplXAPI;
import triplx.core.api.chat.Color;
import triplx.core.api.data.server.ServerManager;
import triplx.hub.core.cache.player.UserCacheManager;
import triplx.hub.core.commands.FlyCommand;
import triplx.hub.core.commands.LoginCommand;
import triplx.hub.core.commands.hubcore.HubcoreCommand;
import triplx.hub.core.commands.hubcore.SubCommandManager;
import triplx.hub.core.data.mongodb.MongoManager;
import triplx.hub.core.data.mongodb.collections.player.PlayerHubSettings;
import triplx.hub.core.data.mongodb.collections.player.PlayerLoginData;
import triplx.hub.core.events.EventHandler;
import triplx.hub.core.events.features.PlayerVisibility;
import triplx.hub.core.gui.tablist.TablistManager;
import triplx.hub.core.hub.HubManager;
import triplx.hub.core.hub.JSONManager;
import triplx.hub.core.login.PlayerLoginUtil;
import triplx.hub.core.messages.AutoMessage;
import triplx.hub.core.nms.Reflection;

public class Hubcore extends JavaPlugin {

    @Getter
    private static Hubcore instance;

    @Getter
    private static TriplXAPI api;


    @Getter
    private static FileConfiguration configuration;

    @Override
    public void onEnable() {
        instance = this;

        api = TriplXAPI.getInstance();


        getConfig().options().copyDefaults(true);
        saveConfig();

        configuration = getConfig();

        String version = getDescription().getVersion();
        Bukkit.getConsoleSender().sendMessage(Color.cc("&c[TRIPLX HUB] &cHubcore enabled. Version: "+ version + "."));

        UserCacheManager.setInstance(new UserCacheManager());

        new EventHandler(getServer().getPluginManager(), this);

        MongoManager.init();

        AutoMessage.init();

        PlayerHubSettings.setInstance(new PlayerHubSettings());
        PlayerLoginData.setInstance(new PlayerLoginData());
        PlayerLoginUtil.setInstance(new PlayerLoginUtil());
        PlayerLoginUtil.getInstance().init();

        PlayerVisibility.setInstance(new PlayerVisibility());

        UserCacheManager.getInstance().onEnable();


        TablistManager.setInstance(new TablistManager());
        TablistManager.getInstance().init();

        SubCommandManager.init();

        Reflection.setInstance(new Reflection());

        getCommand("hubcore").setExecutor(new HubcoreCommand());

        HubManager.setInstance(new HubManager());
        JSONManager.init();


        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("login").setExecutor(new LoginCommand());

        String serverCollection = getConfig().getString("server-manager.collection");
        String serverName = getConfig().getString("server-manager.name");
        String bungeeName = getConfig().getString("server-manager.bungee-name");

        Bukkit.getScheduler().runTaskLater(this, () -> {
                ServerManager.getInstance().registerServer(serverCollection, serverName, bungeeName);
                ServerManager.getInstance().setAvailable();
        }, 2L);




    }

    @Override
    public void onDisable() {
        instance = null;
    }


}
