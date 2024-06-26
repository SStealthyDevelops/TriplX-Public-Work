package triplx.hub.core.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import triplx.core.api.ranking.RankingManager;
import triplx.core.api.utils.NameTagManager;
import triplx.hub.core.cache.player.UserCache;
import triplx.hub.core.cache.player.UserCacheManager;
import triplx.hub.core.data.mongodb.collections.player.PlayerHubSettings;
import triplx.hub.core.data.mongodb.collections.player.PlayerLoginData;
import triplx.hub.core.hub.Hub;
import triplx.hub.core.hub.HubManager;
import triplx.hub.core.login.PlayerLoginUtil;
import triplx.hub.core.scoreboard.ScoreboardManager;

import java.util.ArrayList;

public class PlayerJoinQuit implements Listener {


    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PlayerHubSettings.getInstance().createUser(e.getPlayer().getUniqueId());
        PlayerLoginData.getInstance().createPlayer(e.getPlayer());

        UserCache cache = new UserCache(player.getUniqueId());
        if (PlayerHubSettings.getInstance().playerExists(player.getUniqueId())) { // new player data would not save fast enough to load it here
            cache.setFavorites(PlayerHubSettings.getInstance().getFavorites(cache.getUuid()));
            cache.setFlying(PlayerHubSettings.getInstance().isFlying(cache.getUuid()));
            cache.setPlayerVisibility(PlayerHubSettings.getInstance().arePlayersVisible(cache.getUuid()));
        } else {
            cache.setFavorites(new ArrayList<>());
        }
        UserCacheManager.getInstance().addCache(cache);

        player.setGameMode(GameMode.SURVIVAL);

        ScoreboardManager.sendScoreboard(player);

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        NameTagManager.getInstance().setPrefix(player, RankingManager.getRank(player).getPrefix());



        e.setJoinMessage(null);
        // Hub h = HubManager.getInstance().getAvailableHub();
//        try {
//            // w = Bukkit.getWorld(h.getWorld());
//        } catch (Exception exce) {
//            w = Bukkit.getWorld("world");
//            exce.printStackTrace();
//        }

        Hub hub = HubManager.getInstance().getAvailableHub("MAIN");



        Location spawn = new Location(Bukkit.getWorld(hub.getWorld()), hub.settings.getSpawnX(), hub.settings.getSpawnY(), hub.settings.getSpawnZ(), (float) hub.settings.getYaw(), (float) hub.settings.getPitch());
        player.teleport(spawn);

//        ScoreboardManager.getInstance().addPlayer(player);
        joined(e);
//        PlayerLoginData.getInstance().updateLastIP(e.getPlayer());
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent e) {
        UserCacheManager.getInstance().removeCache(e.getPlayer().getUniqueId());
        e.setQuitMessage(null);
        try {
            PlayerLoginUtil.getInstance().locked.remove(e.getPlayer().getUniqueId());
        } catch (Exception exception) {
            // ignore
        }

        PlayerLoginData.getInstance().updateLastLeave(e.getPlayer().getUniqueId());
//        PlayerLoginData.getInstance().updateLastIP(e.getPlayer());
    }


    // for authentication/login
    public void joined(PlayerJoinEvent e) {
        final long now = System.currentTimeMillis();
        final long lastLogin = PlayerLoginData.getInstance().getLastQuit(e.getPlayer().getUniqueId());
        Player p = e.getPlayer();

//        String IP = p.getAddress().toString();


        // 1 minute
        if (((now - lastLogin) < 60000)) {
            return;
        }


        if (PlayerLoginUtil.getInstance().hasLogin(p.getUniqueId())) {

            PlayerLoginUtil.getInstance().locked.add(p.getUniqueId());



        }

    }





}
