package minigames.triplxmc.skydomination.tasks;

import lombok.Getter;
import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.nms.Actionbar;
import minigames.triplxmc.skydomination.player.Profile;
import org.bukkit.scheduler.BukkitRunnable;
import triplx.core.api.chat.Color;

public class FastTickTask extends BukkitRunnable {

    @Getter
    private final Game game;

    public FastTickTask(final Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        for (Profile profile : game.getPlayers()) {
            if (profile.getActionBar() != null) {
                Actionbar.sendActionBar(profile.getPlayer(), Color.cc(profile.getActionBar()));
            }
            profile.getBoard().update(true);
        }
    }
}
