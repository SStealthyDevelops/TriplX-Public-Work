package minigames.triplxmc.skydomination.tasks;

import minigames.triplxmc.skydomination.player.Profile;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerRespawn extends BukkitRunnable {

    private final Profile profile;

    public PlayerRespawn(Profile profile) {
        this.profile = profile;
    }

    @Override
    public void run() {
        profile.setSpectator(false);
        profile.getPlayer().setAllowFlight(false);
        for (PotionEffect effect : profile.getPlayer().getActivePotionEffects()) {
            profile.getPlayer().removePotionEffect(effect.getType());
        }
        profile.getPlayer().setHealth(20);
        profile.getPlayer().setFoodLevel(20);
        profile.getPlayer().teleport(profile.getTeam().getSpawnPoint());
        profile.getPlayer().getInventory().clear();
        profile.getHero().initializeInventory(profile);
    }
}
