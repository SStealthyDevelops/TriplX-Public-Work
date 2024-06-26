package minigames.triplxmc.skydomination.commands.subcommands;

import minigames.triplxmc.skydomination.commands.SubCommand;
import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.game.GameManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;

public class StartCommand implements SubCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.cc("&cYou must be a player to use this command."));
            return;
        }

        Player p = (Player) sender;
        Game g = GameManager.getInstance().getGame(p);
        if (g == null) return;

        g.startCountdown();
        p.sendMessage(Color.cc("&aStarting game!"));
    }
}
