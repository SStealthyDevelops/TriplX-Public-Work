package triplx.hub.core.messages;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import triplx.hub.core.Hubcore;

import java.util.ArrayList;
import java.util.Random;

public class AutoMessage {

    private static final ArrayList<TextComponent> messages = new ArrayList<>();

    public static void init() {
        addMessages();
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Hubcore.getInstance(), new Runnable() {
            @Override
            public void run() {
                broadcastRandom();
            }
        }, 20, 6000 ); // every 5 min = 20 * 60 * 5 =
    }

    private static void broadcastRandom() {
        int i = new Random().nextInt(messages.size());
        TextComponent component = messages.get(i);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(component);
        }
    }

    @SuppressWarnings("all")
    private static void addMessages() {
        String storeLink = Hubcore.getConfiguration().getString("store-link");
        String forumsLink = Hubcore.getConfiguration().getString("forums-link");

        if (true) { // store message
            TextComponent component = new TextComponent(ChatColor.translateAlternateColorCodes('&',
                    "&c&lTRIPLX NETWORK: &6Please consider supporting by &6donating at our online store: &b&n" + storeLink + "&6. It helps us &6keep developing awesome stuff for you guys!"));
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&aVisit the store!")).create()));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, storeLink));
            messages.add(component);
        }

        if (true) {
            TextComponent component = new TextComponent(ChatColor.translateAlternateColorCodes('&',
                    "&c&lTRIPLX NETWORK: &6Have you signed up for the &6forum? &6If not, make sure to join for information on incoming updates, &6applying for staff, and more! Sign up here: &b&n" + forumsLink + "&6."));

            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&aVisit the forums!")).create()));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, forumsLink));
            messages.add(component);
        }

    }

    
}
