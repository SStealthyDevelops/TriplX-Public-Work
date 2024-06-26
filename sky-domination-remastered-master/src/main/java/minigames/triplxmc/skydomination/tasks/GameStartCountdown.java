package minigames.triplxmc.skydomination.tasks;

import minigames.triplxmc.skydomination.game.Game;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class GameStartCountdown extends BukkitRunnable {

    private Game game;
    private int seconds = 10;
    public GameStartCountdown(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        seconds--;
        if (seconds == 0) {
            try {
                game.soundAll(Sound.FUSE);
                game.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.cancel();
            return;
        }

        if (seconds <= 5) {
            if (seconds == 1) {
                game.broadcastMessage("&eGame starting in &b" + seconds + " &e second!");
            } else {
                game.broadcastMessage("&eGame starting in &b" + seconds + " &e seconds!");
            }
            game.titleAll("&e&l" + seconds, "&aGood luck!");
            game.soundAll(Sound.ORB_PICKUP);
        }



    }
}
