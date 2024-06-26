package triplx.hub.core.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import triplx.hub.core.cache.player.UserCacheManager;
import triplx.hub.core.data.mongodb.collections.player.PlayerHubSettings;
import triplx.hub.core.events.features.PlayerVisibility;
import triplx.hub.core.gui.inventory.Item;

public class PlayerVisibilityUtil {

    public static void toggleVisibility(Player p) {
        PlayerHubSettings.getInstance().setPlayerVisibility(p.getUniqueId(), !PlayerHubSettings.getInstance().arePlayersVisible(p.getUniqueId()));

        Item visibility;
        if (UserCacheManager.getInstance().findUser(p.getUniqueId()).isPlayerVisibility()) {
            visibility = new Item(Material.INK_SACK, 10, "&aShowing Players")
                    .addLore("&eRight click to hide")
                    .addLore("&eall players.");
        } else {
            visibility = new Item(Material.INK_SACK, 8, "&7Hiding Players")
                    .addLore("&eRight click to show")
                    .addLore("&eall players.");
        }

        p.getInventory().setItem(8, visibility.getStack());

        PlayerVisibility.getInstance().updateVisibility(p);
    }

}
