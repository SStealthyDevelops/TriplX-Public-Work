package triplx.hub.core.events.features;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;
import triplx.hub.core.cache.player.UserCache;
import triplx.hub.core.cache.player.UserCacheManager;

public class AntiInventory implements Listener {

    @EventHandler // people who are below builder rank cannot edit their inventory
    public void onItemDrop(PlayerDropItemEvent e) {
        e.setCancelled(fail(e.getPlayer()));
    }

    @EventHandler
    public void onItemMove(InventoryClickEvent e) {
        if (e.getInventory() == e.getWhoClicked().getInventory()) {
            e.setCancelled(fail((Player) e.getWhoClicked()));
        }
    }

    private boolean fail(Player player) {
        Rank rank = RankingManager.getRank(player);
        UserCache cache = UserCacheManager.getInstance().findUser(player);
        // they cant if they have player-mode on
        return !rank.hasPermission(Rank.BUILDER) || !cache.isBypass(); // failed
    }

}
