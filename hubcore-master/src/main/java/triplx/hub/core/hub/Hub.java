package triplx.hub.core.hub;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import triplx.hub.core.data.mongodb.collections.hub.HubSettings;
import triplx.hub.core.data.mongodb.collections.hub.SettingClass;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class Hub {

    private final String world;
    private final String type;
    private final int maxPlayers;

    private HubStatus status;


    public ArrayList<UUID> players;


    public SettingClass settings;

    public Hub(String world, int maxPlayers, String type) {
        this.world = world;
        this.maxPlayers = maxPlayers;
        this.status = HubStatus.MAINTANENCE;
        players = new ArrayList<>();
        this.type = type;
        this.settings = HubSettings.settings(type);
    }

    public void update() {
        if (players.size() >= maxPlayers) {
            status = HubStatus.FULL;
        } else {
            status = HubStatus.OPEN;
        }
    }

    public void join(Player player, boolean silent) {
        if (!silent) {
            update();
        }
    }

    public void leave(Player player, boolean silent) {
        if (!silent) {
            update();
        }
    }



    /* public HubStatus updateStatus() {
        if (this.players.size() > maxPlayers) {

        }
    } */
}

