package triplx.hub.core.gui.inventory.guis.user;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import triplx.core.api.chat.Color;
import triplx.core.api.data.player.PlayerUniData;
import triplx.hub.core.gui.inventory.Item;
import triplx.hub.core.gui.inventory.Menu;
import triplx.hub.core.gui.inventory.guis.GUIUtils;

import java.util.UUID;

public class ProfilePage implements Menu {

    @Getter
    private final Inventory inventory = Bukkit.createInventory(this, 54, Color.cc("&6&lYour Profile")); // 6 rows

    public ProfilePage(Player player) {
        final UUID uuid = player.getUniqueId();
        int karma = PlayerUniData.getKarma(uuid);
        int level = PlayerUniData.getLevel(uuid);
        int xp = PlayerUniData.getXP(uuid);

        Item achievements = new Item(Material.BOOK, "&bAchievements");
        achievements.addLore("&7All you coolest");
        achievements.addLore("&7achievements on TriplX!");
        inventory.setItem(GUIUtils.getSlot(3, 1), achievements.getStack());

        boolean highLevel = (level > 50);
        int maxXP = highLevel ? 17500 : 10000;

        int xpToNext = maxXP - xp;

        Item levelItem = new Item(Material.EMERALD, "&aCurrent Level: &b" + level);
        levelItem.addLore("&d" + xpToNext + " XP until level &a" + (level + 1));
        inventory.setItem(GUIUtils.getSlot(2, 3), levelItem.getStack());




        player.openInventory(inventory);
    }

    @Override
    public boolean onClick(Player player, int slot, ClickType type) {
        return false;
    }

    @Override
    public void onOpen(Player player) {

    }

    @Override
    public void onClose(Player player) {

    }
}
