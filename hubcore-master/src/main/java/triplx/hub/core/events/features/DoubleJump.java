package triplx.hub.core.events.features;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;
import triplx.hub.core.cache.player.UserCache;
import triplx.hub.core.cache.player.UserCacheManager;
import triplx.hub.core.login.PlayerLoginUtil;

public class DoubleJump implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFlightToggle(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        if (PlayerLoginUtil.getInstance().locked.contains(player.getUniqueId())) return;
        UserCache cache = UserCacheManager.getInstance().findUser(player.getUniqueId());

        if (cache.isFlying() || player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            player.setAllowFlight(true);
            return;
        }

        // now for the double jump
        e.setCancelled(true);
        player.setAllowFlight(false);
        player.setFlying(false);

        Location loc  = player.getLocation();
        Vector v = loc.getDirection().multiply(1.2f).setY(1.2);
        player.setVelocity(v);
        loc.getWorld().playSound(loc, Sound.FIREWORK_BLAST, 1, 0.5f);
        //TODO particles
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (PlayerLoginUtil.getInstance().locked.contains(p.getUniqueId())) return;
        UserCache cache = UserCacheManager.getInstance().findUser(p.getUniqueId());
        if (cache == null) return;
        if (cache.isFlying() || p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) {
            p.setAllowFlight(true);
            return;
        }

        if (isOnGround(p)) {
            p.setAllowFlight(true);
        }
    }

    private boolean isOnGround(Player p) {
        return p.getLocation().getWorld().getBlockAt(p.getLocation().subtract(0, 1, 0)).getType() != Material.AIR;
    }

}
