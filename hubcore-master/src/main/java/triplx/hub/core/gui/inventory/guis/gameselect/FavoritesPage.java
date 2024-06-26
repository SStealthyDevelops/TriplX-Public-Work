package triplx.hub.core.gui.inventory.guis.gameselect;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import triplx.core.api.chat.Color;
import triplx.hub.core.Hubcore;
import triplx.hub.core.cache.player.UserCache;
import triplx.hub.core.cache.player.UserCacheManager;
import triplx.hub.core.data.mongodb.collections.hub.HubSettings;
import triplx.hub.core.gui.inventory.guis.GUIUtils;
import triplx.hub.core.gui.inventory.Item;
import triplx.hub.core.gui.inventory.Menu;
import triplx.hub.core.gui.inventory.guis.user.AchievementsPage;
import triplx.hub.core.gui.inventory.guis.user.ProfilePage;
import triplx.hub.core.utils.Game;

import java.util.HashMap;
import java.util.List;

public class FavoritesPage implements Menu {

    @Getter
    private final Inventory inventory = Bukkit.createInventory(this, 54, Color.cc("&a&lYour Favorite Gamemodes"));

    private final HashMap<Integer, Game> gameSlots = new HashMap<>();

    public FavoritesPage(Player player) {
        UserCache cache = UserCacheManager.getInstance().findUser(player.getUniqueId());
        if (cache.getFavorites().size() == 0) { // if they do not have favorite games, open the minigames page
            new MiniGamesPage(player);
            return;
        }

        // profile item
        ItemStack profile = GUIUtils.getHead(player.getName());
        SkullMeta meta = (SkullMeta) profile.getItemMeta();
        meta.setDisplayName(Color.cc("&6Your Profile"));
        profile.setItemMeta(meta);
        inventory.setItem(GUIUtils.getSlot(3, 1), profile);


        // achievements item
        Item achievements = new Item(Material.PAPER, "&cYour Achievements");
        achievements.addLore("&bView your coolest").addLore("&bachievements.");
        inventory.setItem(GUIUtils.getSlot(4, 1), achievements.getStack());


        // the actual games
        List<Game> favorites = cache.getFavorites();

        int row = 2, column = 3; // max row = 4, max column = 7

        for (Game game : favorites) {
            Item item = new Item(game.getIcon(), game.getColoredTitle());
            item.addLore("");
            item.addLore("&aTo remove this game");
            item.addLore("&afrom your favorites, &6right click&a.");
            inventory.setItem(GUIUtils.getSlot(row, column), item.getStack());
            gameSlots.put(GUIUtils.getSlot(row, column), game);


            column++;

            if (column > 7) {
                column = 3;
            }
            row++;

            if (row > 4) {
                // TODO
                System.out.println("Figure out how many gamemodes is the max, send player message when max is reached, prevent this from happening");
            }
        }
        player.openInventory(inventory);
    }

    @Override
    public boolean onClick(Player player, int slot, ClickType type) {

        if (slot == GUIUtils.getSlot(3, 1)) {
            // profile gui
//            if (HubSettings.debugMode) {
//                player.sendMessage(Color.cc("&d&lTRIPLX DEBUG >> &cOpen profile GUI"));
//            }
            new ProfilePage(player);
            return true;
        } else if (slot == GUIUtils.getSlot(4, 1)) {
            // achievements
//            if (HubSettings.debugMode) {
//                player.sendMessage(Color.cc("&d&lTRIPLX DEBUG >> &cOpen achievements GUI"));
//            }
            new AchievementsPage(player);
            return true;
        } else if (gameSlots.get(slot) != null) {
                // go that game lobby
                if (!HubSettings.debugMode) {
                    player.performCommand((Hubcore.getConfiguration().getString("commands.game-hub-switch")
                            .replace("%game%", gameSlots.get(slot).getName())));
                } else player.sendMessage(Color.cc("&d&lTRIPLX DEBUG >> &cGame selected: " + gameSlots.get(slot).getName()));

                return true;
        } else return false;

    }

    @Override
    public void onOpen(Player player) {

    }

    @Override
    public void onClose(Player player) {

    }
}
