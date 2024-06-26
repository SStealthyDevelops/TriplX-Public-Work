package core.triplx.punishment.events;

import core.triplx.punishment.Core;
import core.triplx.punishment.guis.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClick implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void onClick(InventoryClickEvent event) {
        System.out.println("getting called");
        if (!(event.getInventory().getHolder() instanceof Menu)) {
            event.setCancelled(false);
            return;
        }
        Bukkit.getScheduler().runTaskLater(Core.getInstance(), new Runnable() { // need to delay it or it wont work (stupid ffs)
            @Override
            public void run() {

                final InventoryHolder holder = event.getInventory().getHolder();

                System.out.println(holder.toString()); // worked for first menu, not second



                event.setCancelled(((Menu) holder).onClick((Player) event.getWhoClicked(), event.getSlot(), event.getClick()));
                System.out.println();
            }
        }, 2L);
    }

    @EventHandler private void onOpen(InventoryOpenEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu) ((Menu) holder).onOpen((Player) event.getPlayer());
    }

    @EventHandler private void onClose(InventoryCloseEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu) ((Menu) holder).onClose((Player) event.getPlayer());
    }



}
