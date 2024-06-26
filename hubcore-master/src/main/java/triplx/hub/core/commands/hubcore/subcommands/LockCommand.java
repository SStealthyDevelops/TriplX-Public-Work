package triplx.hub.core.commands.hubcore.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;
import triplx.hub.core.commands.SubCommand;
import triplx.hub.core.hub.Hub;
import triplx.hub.core.hub.HubManager;
import triplx.hub.core.hub.HubStatus;

public class LockCommand implements SubCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;

        Player player = (Player) sender;
        Rank rank = RankingManager.getRank(player);

        if (!rank.hasPermission(Rank.ADMIN)) {
            player.sendMessage(Color.cc("&cYou must be an administrator to use this command."));
            return;
        }

        try {
            String world = player.getWorld().getName();
            Hub hub = HubManager.getInstance().getHub(world);

            hub.setStatus(HubStatus.MAINTANENCE);

        } catch (Exception e) {
            player.sendMessage(Color.cc("&cError locking hub."));
            return;
        }
    }


}

