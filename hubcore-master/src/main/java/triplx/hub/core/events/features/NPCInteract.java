package triplx.hub.core.events.features;

import triplx.hub.core.npc.NPCClickManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class NPCInteract implements Listener {


    @EventHandler
    public void onNPCInteract(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        if (!entity.hasMetadata("NPC")) {
            return;
        }


        Player p = e.getPlayer();
        String name = entity.getCustomName();

        NPCClickManager.click(p, name);
    }

}
