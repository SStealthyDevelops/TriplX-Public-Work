package minigames.triplxmc.skydomination.events;

import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.game.GameState;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.ProfileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;

public class PlayerChat implements Listener {

    @EventHandler(priority = EventPriority.LOW) // takes place after filters, etc
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        Profile profile = ProfileManager.getInstance().getProfile(player.getUniqueId());

        if (profile.getGame() == null) return;

        Game game = profile.getGame();

        if (game.getState() == GameState.ACTIVE) {
            e.setCancelled(true);

            String msg = e.getMessage();

            Rank rank = RankingManager.getRank(player);

            String finalMessage = "";
            finalMessage += profile.getTeam() == profile.getGame().getRedTeam() ? "&c[RED] " : "&b[BLUE] ";
            finalMessage += rank.getPrefix() + player.getName();
            finalMessage += "&f: ";
            if (rank == Rank.DEFAULT) {
                finalMessage += "&7";
            }
            finalMessage += msg;
            profile.getTeam().sendMessage(finalMessage);
        } else if (game.getState() == GameState.LOBBY || game.getState() == GameState.STARTING) {
            e.setCancelled(true);

            String msg = e.getMessage();

            Rank rank = RankingManager.getRank(player);

            String finalMessage = "";
            finalMessage += rank.getPrefix() + player.getName();
            finalMessage += "&f: ";
            if (rank == Rank.DEFAULT) {
                finalMessage += "&7";
            }
            finalMessage += msg;
            game.broadcastMessage(finalMessage);
        }


    }

}
