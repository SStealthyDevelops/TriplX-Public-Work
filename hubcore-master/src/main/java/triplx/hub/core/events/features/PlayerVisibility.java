package triplx.hub.core.events.features;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;
import triplx.hub.core.Hubcore;
import triplx.hub.core.cache.player.UserCache;
import triplx.hub.core.cache.player.UserCacheManager;

public class PlayerVisibility implements Listener {

    @Getter
    @Setter
    private static PlayerVisibility instance;

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Bukkit.getScheduler().runTaskLater(Hubcore.getInstance(), () -> {
            updateVisibility(player);
            updateVisibilities(player);
        }, 5L);
    }

    public void updateVisibility(Player player) {
        if (UserCacheManager.getInstance().findUser(player.getUniqueId()) == null) System.out.println("player data ");
        System.out.println(UserCacheManager.getInstance().findUser(player.getUniqueId()).isPlayerVisibility());
        boolean visibility = UserCacheManager.getInstance().findUser(player.getUniqueId()).isPlayerVisibility();

        if (visibility) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                player.showPlayer(online);
            }
        } else {
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (!RankingManager.getRank(online).hasPermission(Rank.YOUTUBE)) { // TODO make sure they arent a mod in hidden mode or something
                    player.hidePlayer(online);
                }
            }
        }
    }

    public void updateVisibilities(Player joined) {
        if (RankingManager.getRank(joined).hasPermission(Rank.YOUTUBE)) return; // OR IS A MOD IN MOD MODE OR SMT
        for (Player p : Bukkit.getOnlinePlayers()) {
            UserCache c = UserCacheManager.getInstance().findUser(p);
            if (!c.isPlayerVisibility()) {
                p.hidePlayer(joined);
            }
        }
    }

}
