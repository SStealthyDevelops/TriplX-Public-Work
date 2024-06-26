package triplx.hub.core.events.features;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerSpeed implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 1000000, 0, false, false);
        e.getPlayer().addPotionEffect(effect);
    }

}
