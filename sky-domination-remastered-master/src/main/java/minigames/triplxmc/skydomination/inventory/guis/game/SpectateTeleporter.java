package minigames.triplxmc.skydomination.inventory.guis.game;


import lombok.Getter;
import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.game.GameState;
import minigames.triplxmc.skydomination.inventory.Menu;
import minigames.triplxmc.skydomination.player.Profile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import triplx.core.api.chat.Color;

public class SpectateTeleporter implements Menu {

    @Getter
    private Inventory inventory;

    public SpectateTeleporter(Profile profile) {
        Game game = profile.getTeam().getGame();
        if (game == null || game.getState() != GameState.ACTIVE) {
            return;
        }

        inventory = Bukkit.createInventory(this, 54, Color.cc("Spectate Selector"));
    }

    @Override
    public boolean onClick(Player player, int slot, ClickType type) {
        return false;
    }

}
