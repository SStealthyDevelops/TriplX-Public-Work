package triplx.hub.core.npc;

import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.hub.core.Hubcore;

public class NPCClickManager {

    public static void click(Player player, String name) {
        name = Color.cc(name);
        switch (name) {
            case "&c&lTRIPLX":
                player.sendMessage(Color.cc(Hubcore.getConfiguration().getString("messages.welcome")));
                return;
            case "": //TODO: add more NPCs
                break;
        }
    }

}
