package minigames.triplxmc.skydomination.events;

import minigames.triplxmc.skydomination.game.GameManager;
import minigames.triplxmc.skydomination.game.GameState;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.ProfileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDrop implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        try {
            Profile profile = ProfileManager.getInstance().getProfile(player);
            if (GameManager.getInstance().getGame(player).getState() != GameState.ACTIVE || profile.isSpectator()) e.setCancelled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
