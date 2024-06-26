package minigames.triplxmc.skydomination.events;

import lombok.Getter;
import minigames.triplxmc.skydomination.game.GameManager;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.ProfileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import triplx.core.api.chat.Color;

import static minigames.triplxmc.skydomination.events.PlayerDeath.DeathMessageType.*;

public class PlayerDeath implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onKill(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Profile p = ProfileManager.getInstance().getProfile((Player) e.getEntity());
            if (p == null) return; //for NPCs
            if (p.isSpectator()) {
                e.setCancelled(true);
                return;
            }

            Player player = p.getPlayer();

            if (!(e.getDamager() instanceof Player)) {
                if (e.getDamage() > player.getHealth()) {
                    e.setCancelled(true);
                    die(player, e.getCause());
                }
            }
        }
        if (e.getDamager() instanceof Player) {
            Profile p = ProfileManager.getInstance().getProfile((Player) e.getDamager());
            if (p.isSpectator()) e.setCancelled(true);
        }
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            Player damaged = (Player) e.getEntity();

            if (e.getDamage() >= damaged.getHealth()) {
                e.setCancelled(true);
                kill(damager, damaged);
            }

        }



    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (e.getDamage() > player.getHealth() && e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                e.setCancelled(true);
                die(player, e.getCause());
            }
        }
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        // should never happen, but if it happens on another world or something
        e.setDeathMessage(null);
        e.getEntity().getInventory().clear();
    }

    public void kill(Player k, Player d) {
        Profile killer = ProfileManager.getInstance().getProfile(k);
        Profile died = ProfileManager.getInstance().getProfile(d);

        killer.addKill();
        died.addDeath();

        killer.getHero().kill(died);
        died.setSpectator();

        GameManager.getInstance().getGame(k).broadcastMessage("&b" + killer.getPlayer().getName() + " killed " + died.getPlayer().getName() + ".");

        killer.setCredits(killer.getCredits() + 25); // TODO: change this around maybe
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Profile profile = ProfileManager.getInstance().getProfile(e.getPlayer());
        Player player = e.getPlayer();
        if (e.getTo().getY() <= 4.5) {
            if (profile.getLastAttack() + 5000 >= System.currentTimeMillis()) {                    // if within past 5 seconds, award kill to killer (duh)
                kill(profile.getLastAttacker(), player);
                profile.setSpectator();
            }
        }
    }

    public void die(Player player, EntityDamageEvent.DamageCause cause) {
        Profile profile = ProfileManager.getInstance().getProfile(player);
        switch (cause) {
            case FALL:

                // attacked at x
                // within x + 5 is now.. award kill
                if (profile.getLastAttack() + 5000 >= System.currentTimeMillis()) {                    // if within past 5 seconds, award kill to killer (duh)
                    kill(profile.getLastAttacker(), player);
                    profile.setSpectator();
                    break;
                }

                GameManager.getInstance().getGame(player).broadcastMessage("&f" + player.getName() + " &cdied of a fall.");
                profile.setSpectator();
                break;
            case FIRE:
            case FIRE_TICK:
                if (profile.getLastAttack() + 5000 >= System.currentTimeMillis()) {                    // if within past 5 seconds, award kill to killer (duh)
                    kill(profile.getLastAttacker(), player);
                    profile.setSpectator();
                    break;
                }
            default:
                GameManager.getInstance().getGame(player).broadcastMessage("&f" + player.getName() + " &cdied.");
                profile.setSpectator();
                break;
        }
    }

    enum DeathMessage {

        DEFAULT_MELEE("Default", "DEFAULT_MELEE", MELEE, "&b%killer% killed %died%."),
        DEFAULT_FALL("Default", "DEFAULT_FALL", FALL, "&b%died% died of a fall."),
        DEFAULT_MELEE_FALL("Default", "DEFAULT_MELEE_FALL", MELEE_FALL, "&b%died% died of a fall from %killer%"),
        DEFAULT_VOID("Default", "DEFAULT_VOID", VOID, "&b%died% fell into the void."),
        DEFAULT_MELEE_VOID("Default", "DEFAULT_MELEE_VOID", MELEE_VOID, "&b&died% was hit into the void by %killer%"),
        DEFAULT_SELF_HARM("Default", "DEFAULT_SELF_HARM", SELF_HARM, "&b%died% died. ");

        @Getter private final String className, name, message;
        @Getter private final DeathMessageType type;
        DeathMessage(String className, String name, DeathMessageType type, String message) {
            this.className = className;
            this.name = name;
            this.type = type;
            this.message = message;
        }

        public static String getMessage(String className, DeathMessageType type, String killer, String died) {
            for (DeathMessage dm : values()) {
                if (dm.getClassName().equalsIgnoreCase(className) && dm.getType() == type) {
                    return Color.cc(dm.getMessage().replace("%killer%", killer).replace("%died%", died));
                }
            }
            return Color.cc("&b%died% died.").replace("%died%", died);
        }

    }

    enum DeathMessageType {
        MELEE, FALL, MELEE_FALL, VOID, MELEE_VOID, SELF_HARM
    }


}
