package triplx.hub.core.gui.inventory.guis.user;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import triplx.hub.core.gui.inventory.Menu;

public class AchievementsPage implements Menu {


    @Getter
    private final Inventory inventory = Bukkit.createInventory(this, 54, "");


    public AchievementsPage(Player player) {

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
