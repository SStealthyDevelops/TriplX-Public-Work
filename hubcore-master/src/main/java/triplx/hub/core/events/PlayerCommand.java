package triplx.hub.core.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import triplx.core.api.chat.Color;
import triplx.hub.core.login.PlayerLoginUtil;

public class PlayerCommand implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().split(" ")[0].equalsIgnoreCase("/login")) return;

        if (PlayerLoginUtil.getInstance().locked.contains(e.getPlayer().getUniqueId())) {
            e.getPlayer().sendMessage(Color.cc("&cPlease login to use commands."));
            e.setCancelled(true);
        }


    }
}
