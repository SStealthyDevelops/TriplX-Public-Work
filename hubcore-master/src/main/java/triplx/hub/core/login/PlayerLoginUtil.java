package triplx.hub.core.login;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.hub.core.data.mongodb.collections.player.PlayerLoginData;
import triplx.hub.core.utils.RegexUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerLoginUtil {

    @Getter
    @Setter
    private static PlayerLoginUtil instance;

    public ArrayList<UUID> locked;

    // step 0: email
    // step 1: password
    // anything after..?
    public HashMap<UUID, Integer> creationStep;

    public HashMap<UUID, String> passwords;

    public void init() {
        locked = new ArrayList<>();
        creationStep = new HashMap<>();
        passwords = new HashMap<>();
    }



    // password field NOT ENCRYPTED
    public boolean authenticate(UUID uuid, String password) {
        return PlayerLoginData.getInstance().confirmPassword(uuid, password);
    }


    public boolean hasLogin(UUID uuid) {
        return PlayerLoginData.getInstance().hasLogin(uuid);
    }

    public void addData(final Player p, final String data) {
        if (creationStep.containsKey(p.getUniqueId())) {
            int i = creationStep.get(p.getUniqueId());
            if (i < 0 || i > 1) {
                Bukkit.getConsoleSender().sendMessage(Color.cc("&cPlayer is on an invalid step of login creation."));
                return;
            }
            if (i == 0) {
                //its an email
                if (!RegexUtil.isEmail(data)) {
                    // not an email
                    p.sendMessage(Color.cc("&cPlease enter a valid email."));
                    return;
                } else {
                    PlayerLoginData.getInstance().setEmail(p.getUniqueId(), data.toLowerCase());
                    creationStep.replace(p.getUniqueId(), 0, 1);
                    p.sendMessage(Color.cc("&aSet email to &b" + data + "&a."));
                    p.sendMessage(Color.cc("&aPlease type in your password."));
                    return;
                }
            } else {
                // password

                if (!RegexUtil.validPassword(data)) {
                    TextComponent text = new TextComponent(Color.cc("&cPlease enter a valid password. &7[HOVER] &c. All your data is securely encrypted."));
                    text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Color.cc("Passwords must contain 1 uppercase and 1 lowercase letter, a symbol, and no spaces.")).create()));
                    p.spigot().sendMessage(text);
                    return;
                }


                TextComponent text = new TextComponent(Color.cc("&bPlease confirm your password: "));
                TextComponent view = new TextComponent(Color.cc("&7[VIEW] "));
                view.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Color.cc("&7Password: " ) +data).create()));

                text.addExtra(view);

                TextComponent confirm = new TextComponent(Color.cc("&a&l[CONFIRM]"));
                confirm.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/login confirm"));

                text.addExtra(confirm);

                passwords.put(p.getUniqueId(), data);
//                System.out.println(data);
                p.spigot().sendMessage(text);
                creationStep.remove(p.getUniqueId());
                // PlayerLoginData.getInstance().setPassword(p.getUniqueId(), data, null);
            }
        }
    }

}
