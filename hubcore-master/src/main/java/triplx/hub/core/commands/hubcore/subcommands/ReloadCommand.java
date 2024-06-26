package triplx.hub.core.commands.hubcore.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;
import triplx.hub.core.cache.player.UserCache;
import triplx.hub.core.cache.player.UserCacheManager;
import triplx.hub.core.commands.SubCommand;
import triplx.hub.core.data.mongodb.collections.player.PlayerHubSettings;

import java.util.UUID;

public class ReloadCommand implements SubCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Rank rank = RankingManager.getRank(player);
            if (!rank.hasPermission(Rank.ADMIN)) {
                player.sendMessage(Color.cc("&cYou must be an administrator to use this command."));
                return;
            }

            /*

            Reload:

            Reset all player cache
            TODO: Add the other stuff that needs to be done

             */

            UserCacheManager.getInstance().flush();

            for (Player p : Bukkit.getOnlinePlayers()) {
                UUID u = p.getUniqueId();
                UserCache c = new UserCache(p.getUniqueId());
                c.setFlying(PlayerHubSettings.getInstance().isFlying(u));
                c.setBypass(false);
                c.setFavorites(PlayerHubSettings.getInstance().getFavorites(u));
                c.setPlayerVisibility(PlayerHubSettings.getInstance().arePlayersVisible(u));
                UserCacheManager.getInstance().addCache(c);

                if (RankingManager.getRank(p).hasPermission(Rank.ADMIN)) {
                    p.sendMessage(Color.cc("&cThe hub was reloaded by &b" + player.getName() + "&c."))  ;
                }
            }





        }



    }
}
