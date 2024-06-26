package triplx.hub.core.gui.inventory.guis.gameselect;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import triplx.hub.core.gui.inventory.Menu;

public class MiniGamesPage implements Menu {

    @Getter
    private Inventory inventory;

    public MiniGamesPage(Player player) {

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
