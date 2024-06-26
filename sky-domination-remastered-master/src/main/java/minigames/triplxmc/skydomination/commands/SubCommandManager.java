package minigames.triplxmc.skydomination.commands;

import minigames.triplxmc.skydomination.commands.subcommands.EndCommand;
import minigames.triplxmc.skydomination.commands.subcommands.ListCommand;
import minigames.triplxmc.skydomination.commands.subcommands.StartCommand;
import org.bukkit.command.CommandSender;
import triplx.core.api.chat.Color;

import java.util.HashMap;

public class SubCommandManager {

    private final static HashMap<String, SubCommand> commands = new HashMap<>();

    public static void init() {
        // commands.add(new Command());
        commands.put("list", new ListCommand());
        commands.put("start", new StartCommand());
        commands.put("end", new EndCommand());
    }

    public static String helpMessage = Color.cc("&cUsage: /game <list/start/end>");

    public static void runCommand(CommandSender sender, String command, String[] args) {
        SubCommand cmd = commands.get(command.toLowerCase());
        if (cmd == null) {
            sender.sendMessage(helpMessage);
            return;
        }
        cmd.onExecute(sender, args);
    }

    public static SubCommand getCommand(String label) {
        for (String command : commands.keySet()) {
            if (command.equalsIgnoreCase(label)) {
                return commands.get(command);
            }
        }
        return null;
    }


}
