package triplx.hub.core.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import triplx.hub.core.gui.inventory.guis.gameselect.FavoritesPage;

public class PlayerSneakEvent implements Listener {

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        // new FavoritesPage(e.getPlayer());
    }

}
