package minigames.triplxmc.skydomination.commands.subcommands;

import minigames.triplxmc.skydomination.commands.SubCommand;
import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.game.GameManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;

public class EndCommand implements SubCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
         if (!(sender instanceof Player)) {
                sender.sendMessage(Color.cc("&cYou must be a player to use this command."));
                return;
            }

            Player p = (Player) sender;
            Game g = GameManager.getInstance().getGame(p);
            if (g == null) return;

            try {
                if (g.end()) {
                    p.sendMessage(Color.cc("&aSuccessfully ended the game."));
                } else {
                    p.sendMessage(Color.cc("&cCould not end game. Please refer to server logs."));
                }
            } catch (Exception e) {
                e.printStackTrace();
                p.sendMessage(Color.cc("&cCould not end game. Please refer to server logs."));
            }
    }
}
