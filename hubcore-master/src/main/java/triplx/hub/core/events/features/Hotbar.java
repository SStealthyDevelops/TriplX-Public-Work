package triplx.hub.core.events.features;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import triplx.hub.core.data.mongodb.collections.player.PlayerHubSettings;
import triplx.hub.core.gui.inventory.Item;
import triplx.hub.core.gui.inventory.guis.gameselect.FavoritesPage;
import triplx.hub.core.login.PlayerLoginUtil;
import triplx.hub.core.utils.PlayerVisibilityUtil;

public class Hotbar implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
     joinInventory(e.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        // hotbar clicks
        Player p = e.getPlayer();
        if (PlayerLoginUtil.getInstance().locked.contains(p.getUniqueId())) return;
        ItemStack item = p.getItemInHand();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item.getType() == Material.COMPASS) {
                new FavoritesPage(p);
            }
            if (item.getType() == Material.INK_SACK) {
                PlayerVisibilityUtil.toggleVisibility(p);
            }



        }



    }

    private void joinInventory(Player player) {
        // compass - game select
        // profile - only if in MAIN hub ; opens profile GUI
        // achievements - only if in MAIN hub ; opens achievements GUI

        player.getInventory().clear();

        Item gameSelect = new Item(Material.COMPASS, "&a&lGame Select").addLore("&7Select a game to play").addLore("&7from our gamemodes!");

        boolean playersVisible = PlayerHubSettings.getInstance().arePlayersVisible(player.getUniqueId());
        Item visibility;
        if (playersVisible) {
            visibility = new Item(Material.INK_SACK, 10, "&aShowing Players")
            .addLore("&eRight click to hide")
            .addLore("&eall players.");
        } else {
            visibility = new Item(Material.INK_SACK, 8, "&7Hiding Players")
            .addLore("&eRight click to show")
            .addLore("&eall players.");
        }






        player.getInventory().setItem(8, visibility.getStack());
        player.getInventory().setItem(0, gameSelect.getStack());
    }

}
