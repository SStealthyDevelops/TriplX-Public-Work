package minigames.triplxmc.skydomination.player;

import lombok.Getter;
import lombok.Setter;
import minigames.triplxmc.skydomination.game.Game;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ProfileManager {


    @Getter
    private HashMap<UUID, Profile> profiles;

    @Getter
    @Setter
    private static ProfileManager instance;

    public void init() {
        profiles = new HashMap<>();
    }

    public Profile getProfile(Player player) {
        return profiles.get(player.getUniqueId());
    }
    public Profile getProfile(UUID uuid) {
        return profiles.get(uuid);
    }


    public boolean clearProfiles(Game game) {
        try {
            for (Profile p : game.getPlayers()) {
                profiles.remove(p.getUuid());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
