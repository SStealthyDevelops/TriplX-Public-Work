package minigames.triplxmc.skydomination.events;

import minigames.triplxmc.skydomination.Core;
import minigames.triplxmc.skydomination.inventory.guis.game.shops.personal.PersonalShop;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.ProfileManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import triplx.core.api.chat.Color;

public class NPCClick implements Listener {

    final String personalShopName = Core.getInstance().getConfig().getString("personal-shop-name");
    final String teamShopName = Core.getInstance().getConfig().getString("team-shop-name");

    @EventHandler
    public void onClick(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        Profile profile = ProfileManager.getInstance().getProfile(player);
        Entity entity = e.getRightClicked();
        if (entity instanceof Player) {
            Player p = (Player) entity;
            if (ProfileManager.getInstance().getProfile(p) != null) return;
         }


        if (entity.getName().equals(Color.cc(personalShopName))) { // configurable name
            new PersonalShop(profile);
        } else if (entity.getCustomName().equals(Color.cc(teamShopName))) {
            // TODO
        }
    }

}
