package triplx.hub.core.events.features;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;
import triplx.hub.core.cache.player.UserCache;
import triplx.hub.core.cache.player.UserCacheManager;

public class WorldEdit implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        e.setCancelled(fail(e.getPlayer()));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(fail(e.getPlayer()));
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(fail(e.getPlayer()));
    }

    @EventHandler
    public void onCropTrample(PlayerInteractEvent e) {
        if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.SOIL) {
            e.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
            e.setCancelled(true);
        }
    }

    private boolean fail(Player player) {
        Rank rank = RankingManager.getRank(player);
        UserCache cache = UserCacheManager.getInstance().findUser(player);
        if (!rank.hasPermission(Rank.BUILDER) || !cache.isBypass()) { // they cant if they have player-mode on
            return true; // failed
        }
        return false;
    }


}
