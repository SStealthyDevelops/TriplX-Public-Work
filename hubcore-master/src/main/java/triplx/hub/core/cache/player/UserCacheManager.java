package triplx.hub.core.cache.player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.hub.core.data.mongodb.collections.player.PlayerHubSettings;

import java.util.HashMap;
import java.util.UUID;

public class UserCacheManager {

    @Getter
    @Setter
    private static UserCacheManager instance;

    public HashMap<UUID, UserCache> userCaches;

    public UserCacheManager() {
        userCaches = new HashMap<>();
    }

    public void flush() {
        userCaches.clear();
        Bukkit.getConsoleSender().sendMessage(Color.cc("&c[TRIPLX HUB] &cLocal data cache cleared.."));
    }


    public UserCache findUser(UUID uuid) {
        try {
            return userCaches.get(uuid);
        } catch (NullPointerException e) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getUniqueId() == uuid) {
                    return new UserCache(uuid);
                }
            }
            e.printStackTrace();
            return null;
        }
    }

    public UserCache findUser(Player player) {
        try {
            return findUser(player.getUniqueId());
        } catch (NullPointerException e) {
            if (Bukkit.getOnlinePlayers().contains(player)) {
                return new UserCache(player.getUniqueId());
            }
            e.printStackTrace();
            return null;
        }
    }

    public void addCache(UserCache cache) {
        if (!userCaches.containsKey(cache.getUuid())) {
            userCaches.put(cache.getUuid(), cache);
        }
    }

    public void removeCache(UUID cache) {
        if (userCaches.containsKey(cache)) {
            userCaches.remove(cache);
        } else {
            System.out.println("UserCache could not be removed.");
        }
    }

    public void updateCache(UserCache cache) {
        removeCache(cache.getUuid());
        addCache(cache);
    }

    public void onEnable() {
        if (Bukkit.getOnlinePlayers() == null || Bukkit.getOnlinePlayers().size() == 0) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            UserCache cache = new UserCache(p.getUniqueId());
            cache.setFlying(PlayerHubSettings.getInstance().isFlying(p.getUniqueId()));
            cache.setFavorites(PlayerHubSettings.getInstance().getFavorites(p.getUniqueId()));
            UserCacheManager.getInstance().addCache(cache);
        }
    }

}

