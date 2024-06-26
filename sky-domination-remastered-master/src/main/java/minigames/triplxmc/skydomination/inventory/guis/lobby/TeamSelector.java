package minigames.triplxmc.skydomination.inventory.guis.lobby;

import lombok.Getter;
import minigames.triplxmc.skydomination.game.GameManager;
import minigames.triplxmc.skydomination.inventory.GUIUtils;
import minigames.triplxmc.skydomination.inventory.Item;
import minigames.triplxmc.skydomination.inventory.Menu;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import triplx.core.api.chat.Color;

@Getter
public class TeamSelector implements Menu {

    private final Inventory inventory;

    public TeamSelector(Player player) {
        inventory = Bukkit.createInventory(this, 27, Color.cc("&aTeam Select"));

        Item redTeam = new Item(Material.WOOL, 14, Color.cc("&cTeam Red"));
        redTeam.addLore("");
        redTeam.addLore("&7Join the &cred&7 team");

        Item blueTeam = new Item(Material.WOOL, 3, Color.cc("&bTeam Blue"));
        blueTeam.addLore("");
        blueTeam.addLore("&7Join the &bblue&7 team");

        inventory.setItem(GUIUtils.getSlot(2, 4), redTeam.getStack());
        inventory.setItem(GUIUtils.getSlot(2, 6), blueTeam.getStack());

        player.openInventory(inventory);
    }

    @Override
    public boolean onClick(Player player, int slot, ClickType type) {
        try {
            Profile profile = ProfileManager.getInstance().getProfile(player);
            if (slot == GUIUtils.getSlot(2, 4)) { // red team
                if (profile.updateTeam(GameManager.getInstance().getGame(player).getRedTeam())) {
                    player.sendMessage(Color.cc("&aYou joined the &cRED &ateam!"));
                } else {
                    player.sendMessage(Color.cc("&cToo many players on that team!"));
                }
            } else if (slot == GUIUtils.getSlot(2, 6)) { // blue team
                if (profile.updateTeam(GameManager.getInstance().getGame(player).getBlueTeam())) {
                    player.sendMessage(Color.cc("&aYou joined the &bBLUE &ateam!"));
                } else {
                    player.sendMessage(Color.cc("&cToo many players on that team!"));
                }
            } else return true;
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 0.3f, 1f);
            player.closeInventory();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
