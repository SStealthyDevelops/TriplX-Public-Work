package minigames.triplxmc.skydomination.tasks;

import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.player.Profile;
import org.bukkit.scheduler.BukkitRunnable;

public class CreditAddTask extends BukkitRunnable {

    private final Game game;
    public CreditAddTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        for (Profile profile : game.getPlayers()) {
            profile.setCredits(profile.getCredits() + 5);
        }
    }
}
