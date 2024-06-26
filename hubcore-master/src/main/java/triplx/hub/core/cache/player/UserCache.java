package triplx.hub.core.cache.player;

import lombok.Getter;
import lombok.Setter;
import triplx.hub.core.utils.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserCache {

    private final UUID uuid;
    private boolean bypass;
    private boolean flying;
    private List<Game> favorites;
    private boolean playerVisibility;

    public UserCache(UUID uuid) {
        this.uuid = uuid;
        this.bypass = false;
        this.favorites = new ArrayList<>();
        this.playerVisibility = true;

        UserCacheManager.getInstance().userCaches.put(this.uuid, this);
    }

}
