package triplx.hub.core.hub;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class HubManager {

    @Getter
    @Setter
    private static HubManager instance;

    public HashMap<String, Hub> hubs = new HashMap<>();

    public void createHub(String world, int maxPlayers, String type) {
        Hub hub = new Hub(world, maxPlayers, type);
        addHub(hub);
        JSONManager.addHub(hub);
    }

    public void addHub(Hub hub) {
        hubs.put(hub.getWorld(), hub);
    }

    public Hub getAvailableHub(String type) {
        for (Hub hub : hubs.values()) {
            if (hub.getType().equalsIgnoreCase(type) && hub.getStatus() == HubStatus.OPEN) {
                return hub;
            }
        }
        return hubs.values().iterator().next();
    }

    public Hub getHub(String world) {
        return hubs.get(world);
    }


}
