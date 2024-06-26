package triplx.hub.core.events.features;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import triplx.hub.core.login.PlayerLoginUtil;

public class PlayerChat implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void playerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        final String msg = e.getMessage();

        if (PlayerLoginUtil.getInstance().creationStep.containsKey(p.getUniqueId())) {
            e.setMessage(null);
            e.setCancelled(true);
            if (msg.equalsIgnoreCase("cancel")) {
                PlayerLoginUtil.getInstance().creationStep.remove(p.getUniqueId());
                return;
            }
            PlayerLoginUtil.getInstance().addData(p, msg);

        }
    }

}
