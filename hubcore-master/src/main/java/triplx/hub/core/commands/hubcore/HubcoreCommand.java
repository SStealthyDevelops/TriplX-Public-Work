package triplx.hub.core.commands.hubcore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;

public class HubcoreCommand implements CommandExecutor {


    public static final String helpMessage = Color.cc("&cUsage: /hubcore "); // TODO

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.cc("&cOnly players can use this command."));
            return true;
        }

        Player player = (Player) sender;

        if (!RankingManager.getRank(player).hasPermission(Rank.ADMIN)) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(helpMessage);
            return true;
        }

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i != 0) {
                str.append(args[i]).append(" ");
            }
        }

        SubCommandManager.runCommand(sender, args[0], str.toString().split(" "));

        return true;
    }
}

