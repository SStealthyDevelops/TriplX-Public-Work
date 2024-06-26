package minigames.triplxmc.skydomination.events;

import minigames.triplxmc.skydomination.data.mongo.AchievementsData;
import minigames.triplxmc.skydomination.data.mongo.HeroStats;
import minigames.triplxmc.skydomination.data.mongo.OverallStats;
import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.game.GameManager;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;

public class PlayerJoinQuit implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        AchievementsData.getInstance().createPlayer(p);

        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
        Profile profile;
        if (ProfileManager.getInstance().getProfile(p) != null) {
            profile = ProfileManager.getInstance().getProfile(p);
        } else {
            profile = new Profile(p);
            ProfileManager.getInstance().getProfiles().put(profile.getUuid(), profile);
        }

        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);

        profile.setPrefix(RankingManager.getRank(p).getPrefix());

        e.setJoinMessage(null);

        try {
            if (GameManager.getInstance().getGame(p) != null) { //if they are rejoining send them back into game
                GameManager.getInstance().getGame(p).join(profile);
                return;
            }

            Game g = GameManager.getInstance().getLobbyGame();
            if (g == null) {
                Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not find an active LOBBY match."));
                p.kickPlayer(Color.cc("&cCould not find a suitable match... Internal error occurred.")); // shouldn't get here- the Login event should prevent this
                return;
            }
            g.join(profile);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        OverallStats.getInstance().createPlayer(p.getUniqueId());
        HeroStats.getInstance().createPlayer(p.getUniqueId());
        // ShopFavoritesData
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if (GameManager.getInstance().getLobbyGame() == null && !RankingManager.getRank(e.getPlayer()).hasPermission(Rank.MODERATOR) && GameManager.getInstance().getGame(e.getPlayer()) == null) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Color.cc("\n&cCould not connect to game server: Server is full!"));
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Game game = GameManager.getInstance().getGame(e.getPlayer());
        e.setQuitMessage(null);
        if (game == null) return;
        game.quit(e.getPlayer());
    }




}
