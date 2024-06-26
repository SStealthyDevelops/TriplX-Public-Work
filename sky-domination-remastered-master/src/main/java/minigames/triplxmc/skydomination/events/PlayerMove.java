package minigames.triplxmc.skydomination.events;

import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.game.GameManager;
import minigames.triplxmc.skydomination.map.constructors.Base;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.ProfileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("unused")
public class PlayerMove implements Listener {

    private final Set<UUID> onBases = new HashSet<>();

    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        checkBase(e);
    }

    private void checkBase(final PlayerMoveEvent e) {
        Player player = e.getPlayer();

        Profile profile = ProfileManager.getInstance().getProfile(player);

        if (GameManager.getInstance().getGame(player) == null) return;

        Game game = GameManager.getInstance().getGame(player);
        Base base = game.getMap().getBase(e.getTo().clone() //if you dont clone it will tp player one block lower every tick
                .subtract(0, 1, 0)); // might need to subtract Y by 2

        if (base == null) {
            if (onBases.contains(profile.getUuid())) {
                onBases.remove(profile.getUuid());
                for (Base b : game.getMap().getBases()) {
                    b.removeProfile(profile);
                }
            }
            return;
        }

        onBases.add(profile.getUuid());
        base.addProfile(profile);
    }

}
