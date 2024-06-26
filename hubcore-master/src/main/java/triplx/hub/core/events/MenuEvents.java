package triplx.hub.core.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import triplx.hub.core.Hubcore;
import triplx.hub.core.gui.inventory.Menu;

public class MenuEvents implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void onClick(InventoryClickEvent event) {
        System.out.println("getting called");
        event.setCancelled(true);
        // need to delay it or it wont work (stupid ffs)
        Bukkit.getScheduler().runTaskLater(Hubcore.getInstance(), () -> {
            if (!(event.getInventory().getHolder() instanceof Menu)) return;
            final InventoryHolder holder = event.getInventory().getHolder();
            event.setCancelled(((Menu) holder).onClick((Player) event.getWhoClicked(), event.getSlot(), event.getClick()));
        }, 2L);
    }

    @EventHandler private void onOpen(InventoryOpenEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu) {
            ((Menu) holder).onOpen((Player) event.getPlayer());
        }
    }

    @EventHandler private void onClose(InventoryCloseEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu) ((Menu) holder).onClose((Player) event.getPlayer());
    }

}
