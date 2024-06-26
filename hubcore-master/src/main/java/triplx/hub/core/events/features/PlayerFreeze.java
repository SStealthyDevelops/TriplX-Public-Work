package triplx.hub.core.events.features;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import triplx.core.api.chat.Color;
import triplx.hub.core.login.PlayerLoginUtil;

import java.util.UUID;

public class PlayerFreeze implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        UUID u = p.getUniqueId();
        if (PlayerLoginUtil.getInstance().locked.contains(u)) {
            if (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ()) return;
            p.sendMessage(Color.cc("&cPlease login to move."));
            e.setTo(e.getFrom());
        }
    }

}
