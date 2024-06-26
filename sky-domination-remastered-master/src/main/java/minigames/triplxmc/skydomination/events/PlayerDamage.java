package minigames.triplxmc.skydomination.events;

import minigames.triplxmc.skydomination.game.GameManager;
import minigames.triplxmc.skydomination.game.GameState;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.ProfileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamage implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (GameManager.getInstance().getGame(player) != null) {
                if (GameManager.getInstance().getGame(player).getState() != GameState.ACTIVE) e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            Player damaged = (Player) e.getEntity();
            Profile damagedProfile = ProfileManager.getInstance().getProfile(damaged);
            damagedProfile.setLastAttack(System.currentTimeMillis());
            damagedProfile.setLastAttacker(damager);
        }
    }


}
