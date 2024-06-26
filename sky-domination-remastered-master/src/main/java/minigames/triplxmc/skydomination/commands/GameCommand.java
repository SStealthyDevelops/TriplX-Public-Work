package minigames.triplxmc.skydomination.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;

public class GameCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("game")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;
                Rank rank = RankingManager.getRank(player);
                if (!rank.hasPermission(Rank.MODERATOR)) {
                    player.sendMessage(Color.cc("&cYou do not have permission to do this."));
                    return false;
                }
                // has permission
            }

            if (args.length == 0) {
                sender.sendMessage(SubCommandManager.helpMessage);
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

        return true;
    }


}
