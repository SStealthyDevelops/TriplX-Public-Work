package minigames.triplxmc.skydomination.events;

import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.game.GameManager;
import minigames.triplxmc.skydomination.map.constructors.Base;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.ProfileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        // block breakage during lobby handled in PlayerInteract Listener

        Profile profile = ProfileManager.getInstance().getProfile(p);
        if (profile == null) return;

        Game game = GameManager.getInstance().getGame(p);
        if (game == null) return;

        Base base = game.getMap().getBase(e.getBlock().getLocation());
        if (base != null) {
            e.setCancelled(true);
//            game.broadcastMessage("Base broken");
        }


    }

}
