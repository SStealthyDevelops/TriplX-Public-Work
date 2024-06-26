package minigames.triplxmc.skydomination.events;

import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.game.GameManager;
import minigames.triplxmc.skydomination.game.GameState;
import minigames.triplxmc.skydomination.inventory.guis.lobby.HeroSelector;
import minigames.triplxmc.skydomination.inventory.guis.lobby.TeamSelector;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Game g = GameManager.getInstance().getGame(p);

        if (g == null) {
            System.out.println(p.getName() + " is in a null game!");
            return;
        }

        if (g.getState() == GameState.LOBBY) { // prevents block breakage during LOBBY state
            e.setCancelled(true);
            switch (e.getPlayer().getInventory().getHeldItemSlot()) {
                case 0: // team select
                    new TeamSelector(p);
                    break;
                case 1: // hero select
                    new HeroSelector(p);
                    break;
                case 8: // leave lobby

                    break;
            }


        }

    }

}
