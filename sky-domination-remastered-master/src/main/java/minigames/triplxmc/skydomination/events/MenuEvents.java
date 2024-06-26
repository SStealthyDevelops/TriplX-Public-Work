package minigames.triplxmc.skydomination.events;

import minigames.triplxmc.skydomination.Core;
import minigames.triplxmc.skydomination.game.GameManager;
import minigames.triplxmc.skydomination.game.GameState;
import minigames.triplxmc.skydomination.inventory.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import triplx.core.api.chat.Color;


public class MenuEvents implements Listener {

    @EventHandler(ignoreCancelled = true)
    private void onClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (!(event.getInventory().getHolder() instanceof Menu)) {
            if (GameManager.getInstance().getGame(p) == null) {
                Bukkit.getConsoleSender().sendMessage(Color.cc("&cPlayer " + p.getName() + " is in a null game.."));
                event.setCancelled(true);
                return;
            }
            if (GameManager.getInstance().getGame(p).getState() == GameState.LOBBY) {
                event.setCancelled(true);
                return;
            }
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);
        Bukkit.getScheduler().runTaskLater(Core.getInstance(), new Runnable() { // need to delay it or it wont work (stupid ffs)
            @Override
            public void run() {
                final InventoryHolder holder = event.getInventory().getHolder();
                ((Menu) holder).onClick((Player) event.getWhoClicked(), event.getSlot(), event.getClick());
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
