package minigames.triplxmc.skydomination.events;

import minigames.triplxmc.skydomination.game.GameManager;
import minigames.triplxmc.skydomination.game.GameState;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.ProfileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class HandSlotChange implements Listener {

    @EventHandler
    public void onHandSwap(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        if (GameManager.getInstance().getGame(p).getState() == GameState.ACTIVE) {
            if (e.getNewSlot() == 8) { // slot 9, ultimate
                e.setCancelled(true);
                ProfileManager.getInstance().getProfile(p).getHero().useUltimate(ProfileManager.getInstance().getProfile(p));
            }
            if (e.getNewSlot() == 7) { // slow 8, tactical
                e.setCancelled(true);
                ProfileManager.getInstance().getProfile(p).getHero().useTactical(ProfileManager.getInstance().getProfile(p));
            }
        }

    }

    @EventHandler
    public void onInventoryModify(InventoryClickEvent e) {
        // prevent players from moving hero items
        Player p = (Player) e.getWhoClicked();
        Profile profile = ProfileManager.getInstance().getProfile(p.getUniqueId());
        if ((e.getSlot() == 8 || e.getSlot() == 7) && e.getClickedInventory() == e.getWhoClicked().getInventory() && profile.getGame().getState() == GameState.ACTIVE) {
            e.setCancelled(true);
        }

        if (e.getSlotType() == InventoryType.SlotType.ARMOR) e.setCancelled(true);

    }


}
