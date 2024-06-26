package minigames.triplxmc.skydomination.tasks;

import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.map.constructors.Base;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTickTask extends BukkitRunnable {

    private final Game game;

    public GameTickTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        for (Base base : game.getMap().getBases()) {
            base.update();
        }
    }
}
