package triplx.hub.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;
import triplx.hub.core.Hubcore;
import triplx.hub.core.cache.player.UserCache;
import triplx.hub.core.cache.player.UserCacheManager;
import triplx.hub.core.data.mongodb.collections.player.PlayerHubSettings;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;
            Rank rank = RankingManager.getRank(player);

            if (!rank.hasPermission(Rank.VIP)) {
                player.sendMessage(Color.cc("&cTo use this feature, buy &aVIP &cat &6" + Hubcore.getConfiguration().getString("store-link")) + "&c.");
                return true;
            }

            // player has permission

            try {
                UserCache cache = UserCacheManager.getInstance().findUser(player);


                boolean newBool = !cache.isFlying();
                cache.setFlying(newBool);

                UserCacheManager.getInstance().updateCache(cache);


                PlayerHubSettings.getInstance().setFlying(player.getUniqueId(), newBool);

                String add;
                add = newBool ? Color.cc("&bnow") : Color.cc("&bno longer");

                player.sendMessage(Color.cc("&aYou are " + add + " &aflying."));
                return true;
            } catch (Exception e) {
                player.sendMessage(Color.cc("&cThere was an error completing this command."));
            }
        }
        return true;
    }
}
