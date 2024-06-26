package triplx.hub.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.hub.core.data.mongodb.collections.player.PlayerLoginData;
import triplx.hub.core.login.PlayerLoginUtil;

public class LoginCommand implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        assert label.equalsIgnoreCase("login");

        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.cc("&cOnly players can use the /login command."));
            return true;
        }

        Player p = (Player) sender;

        if (args.length == 0) {
            if (PlayerLoginData.getInstance().hasLogin(p.getUniqueId())) {
                if (PlayerLoginUtil.getInstance().locked.contains(p.getUniqueId())) {
                    p.sendMessage(Color.cc("&cUse /login <password> to login!"));
                    return true;
                } else {
                    //TODO: open LOGIN GUI
                }
            } else {
                p.sendMessage(Color.cc("&cTo create a login, use /login create"));
                return true;
            }
        } else

        if (args.length == 1) {
            String arg = args[0];

            if (PlayerLoginData.getInstance().hasLogin(p.getUniqueId())) {
                // assume its the password
                boolean passed = PlayerLoginUtil.getInstance().authenticate(p.getUniqueId(), arg);
                System.out.println(arg);
                arg = "";
                if (!passed) {
                    p.sendMessage(Color.cc("&cIncorrect password. Please try again."));
                    return true;
                } else {
                    try {
                        PlayerLoginUtil.getInstance().locked.remove(p.getUniqueId());
                        p.sendMessage(Color.cc("&aWelcome back, " + p.getDisplayName() + "&a."));
                    } catch (Exception e) {
                        System.out.println("Error unlocking player " + p.getName());
                    }
                    return true;
                }
            } else { // they dont have a login
                if (arg.equalsIgnoreCase("create")) {
                    // create a login profile for them
                    p.sendMessage(Color.cc("&aPlease type your email in the chat."));
                    PlayerLoginUtil.getInstance().creationStep.put(p.getUniqueId(), 0);
                    return true;
                } else if (arg.equalsIgnoreCase("confirm")) {
                    String pswd = PlayerLoginUtil.getInstance().passwords.get(p.getUniqueId());
                    // System.out.println("2: " + pswd);
                    PlayerLoginData.getInstance().setPassword(p.getUniqueId(), pswd, "");
                    p.sendMessage(Color.cc("&aYou successfully set your password."));
                } else {
                    p.sendMessage(Color.cc("&cTo create a login, use /login create"));
                    return true;
                }
            }


        }


        return true;
    }
}
