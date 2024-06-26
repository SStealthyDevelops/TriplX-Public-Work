package minigames.triplxmc.skydomination.commands.subcommands;

import minigames.triplxmc.skydomination.commands.SubCommand;
import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.game.GameManager;
import org.bukkit.command.CommandSender;
import triplx.core.api.chat.Color;

public class ListCommand implements SubCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {

        StringBuilder b = new StringBuilder();
        b.append(Color.cc("&7------------ &dGAMES &7------------"));
        for (Game game : GameManager.getInstance().getActiveGames().values()) {
            b.append("\n").append(Color.cc("&aGame \"" + game.getMap().getMapName() + "\" &d(World: " + game.getActiveWorld().getName() + ") &b(State: " + game.getState().toString() + ")"));
        }

        sender.sendMessage(b.toString());

    }
}
