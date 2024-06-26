package triplx.hub.core.commands.hubcore.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.hub.core.cache.player.UserCache;
import triplx.hub.core.cache.player.UserCacheManager;
import triplx.hub.core.commands.SubCommand;

public class BypassCommand implements SubCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        UserCache cache = UserCacheManager.getInstance().findUser(p);

        cache.setBypass(!cache.isBypass());
        String attachment = cache.isBypass() ? "&bnow bypassing" : "&cno longer bypassing";
        p.sendMessage(Color.cc("&aYou are " + attachment + " &a basic restrictions."));
    }
}
