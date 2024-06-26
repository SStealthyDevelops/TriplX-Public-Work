package triplx.hub.core.commands.hubcore;

import org.bukkit.command.CommandSender;
import triplx.core.api.chat.Color;
import triplx.hub.core.commands.SubCommand;
import triplx.hub.core.commands.hubcore.subcommands.BypassCommand;
import triplx.hub.core.commands.hubcore.subcommands.LockCommand;
import triplx.hub.core.commands.hubcore.subcommands.ReloadCommand;

import java.util.HashMap;

public class SubCommandManager {

    private static final HashMap<String, SubCommand> commands = new HashMap<>();

    public static void init() {
        // commands.add(new Command());
        commands.put("bypass", new BypassCommand());
        commands.put("lock", new LockCommand());
        commands.put("reload", new ReloadCommand());
    }

    public static void runCommand(CommandSender sender, String command, String[] args) {
        SubCommand cmd = commands.get(command.toLowerCase());
        if (cmd == null) {
            sender.sendMessage(HubcoreCommand.helpMessage);
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
