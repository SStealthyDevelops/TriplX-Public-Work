package minigames.triplxmc.skydomination.game;

import lombok.Getter;
import lombok.Setter;
import minigames.triplxmc.skydomination.Core;
import minigames.triplxmc.skydomination.data.mongo.OverallStats;
import minigames.triplxmc.skydomination.hero.Hero;
import minigames.triplxmc.skydomination.inventory.Item;
import minigames.triplxmc.skydomination.map.GameMap;
import minigames.triplxmc.skydomination.map.MapManager;
import minigames.triplxmc.skydomination.map.constructors.NoWorldLocation;
import minigames.triplxmc.skydomination.nms.SkullCreator;
import minigames.triplxmc.skydomination.npc.NPCWrapper;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.ProfileManager;
import minigames.triplxmc.skydomination.player.team.Team;
import minigames.triplxmc.skydomination.player.team.TeamColor;
import minigames.triplxmc.skydomination.scoreboard.GameBoard;
import minigames.triplxmc.skydomination.tasks.FastTickTask;
import minigames.triplxmc.skydomination.tasks.CreditAddTask;
import minigames.triplxmc.skydomination.tasks.GameStartCountdown;
import minigames.triplxmc.skydomination.tasks.GameTickTask;
import minigames.triplxmc.skydomination.utils.Base64Strings;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.RankingManager;

import java.io.IOException;
import java.util.*;

import static minigames.triplxmc.skydomination.game.GameState.*;

@Getter
@Setter
public class Game {

    private final Team redTeam;
    private final Team blueTeam;

    private final GameMap map;
    private final World activeWorld;

    private final ArrayList<Profile> players;

    private final int matchSize = 12; // 6 players per team?

    private GameState state;

    private BukkitTask countdownTask;
    private BukkitTask tickTask;
    private BukkitTask creditTask;
    private BukkitTask fastTickTask;


    private Location spawnLocation;

    public Game(GameMap map) throws IOException {
        state = BUILDING;

        this.map = map;

        this.activeWorld = MapManager.getInstance().createActiveWorld(map);
        players = new ArrayList<>();

        map.getTeamOneSpawnLocs()[0].setWorld(activeWorld);
        map.getTeamTwoSpawnLocs()[0].setWorld(activeWorld);

        this.redTeam = new Team(this, TeamColor.RED, map.getTeamOneSpawnLocs()[0]);
        this.blueTeam = new Team(this, TeamColor.BLUE, map.getTeamTwoSpawnLocs()[0]);

        this.spawnLocation = new Location(activeWorld, map.getLobbySpawnLocation().getX(), map.getLobbySpawnLocation().getY(), map.getLobbySpawnLocation().getZ(), map.getLobbySpawnLocation().getYaw(), map.getLobbySpawnLocation().getPitch());

        this.fastTickTask = new FastTickTask(this).runTaskTimer(Core.getInstance(), 0, 5);

        for (NoWorldLocation loc : this.map.getTeamShops()) {
            Location location = NoWorldLocation.getLocationWithWorld(activeWorld, loc);
            NPCWrapper.getInstance().spawnTeamShop(location);
        }

        for (NoWorldLocation loc : this.map.getPersonalShops()) {
            Location location = NoWorldLocation.getLocationWithWorld(activeWorld, loc);
            NPCWrapper.getInstance().spawnPersonalShop(location);
        }

        state = LOBBY;
    }

    @SuppressWarnings("all")
    public void join(Profile player) {
        player.getPlayer().getInventory().clear();
        if (player.getPlayer() == null) System.out.println("NULL PLAYER");
        switch (this.state) {
            case LOBBY:
                players.add(player);
                if (players.size() == matchSize) {
                    state = STARTING;
                    startCountdown();
                }

                player.getPlayer().teleport(spawnLocation);
                broadcastMessage("&e" + player.getPlayer().getDisplayName() + " joined. (&b" + players.size() + "&e/&b" + matchSize + "&e)");
                players.forEach(profile -> {
                    profile.setBoard(new GameBoard(profile, this));
                    profile.getBoard().update(false);
                    profile.getPlayer().setScoreboard(profile.getBoard().getBoard());
                });
                break;
            case ACTIVE:
                broadcastMessage(player.getPlayer().getDisplayName() + " &areconnected.");

                players.forEach(profile -> {
                    profile.getBoard().update(false);
                    profile.getPlayer().setScoreboard(profile.getBoard().getBoard());
                });
                player.setPrefix(Color.cc(player.getTeam() == redTeam ? "&c[RED] " : "&b[BLUE] "));
                break;
            default:
                // send to a different server (probably)

                break;
        }

        // give player team selector
        Item teamSelector = new Item(Material.WOOL, "&b&lTeam Selector");

        Item leaveLobby = new Item(Material.BARRIER, "&c&lLeave Lobby");

        ItemStack heroSelector = SkullCreator.itemFromBase64(Base64Strings.overkillHead);
        ItemMeta meta = heroSelector.getItemMeta();
        meta.setDisplayName(Color.cc("&d&lSelect Hero"));
        heroSelector.setItemMeta(meta);

        ItemMeta m = heroSelector.getItemMeta();
        m.setDisplayName(Color.cc("&d&lHero Selector"));
        String[] lore = new String[]{"", Color.cc("&7Select a hero"), Color.cc("&7with awesome abilities!")};
        m.setLore(Arrays.asList(lore.clone()));

        heroSelector.setItemMeta(m);

        player.getPlayer().getInventory().setItem(1, heroSelector);
        player.getPlayer().getInventory().setItem(0, teamSelector.getStack());
        player.getPlayer().getInventory().setItem(8, leaveLobby.getStack());


    }
    
    public void quit(Player profile) {
        broadcastMessage("&e" + profile.getDisplayName() + " disconnected.");
        if (state == LOBBY) {
            players.remove(ProfileManager.getInstance().getProfile(profile));
            ProfileManager.getInstance().getProfiles().remove(profile.getUniqueId());
        } else if (state == STARTING) {
            state = LOBBY;
            ProfileManager.getInstance().getProfiles().remove(profile.getUniqueId());
            this.countdownTask.cancel();
            players.remove(ProfileManager.getInstance().getProfiles().get(profile.getUniqueId()));
            broadcastMessage("&eGame start cancelled.. awaiting more players.");
        }

        players.forEach(pr -> pr.getBoard().update(false));
    }


    @SuppressWarnings("unused")
    public void joinHidden(Player player) {
        //TODO: for moderators that are looking for hackers and stuff
    }

    public void startCountdown() {
        broadcastMessage("&eGame starting in 10 seconds");
        this.countdownTask = new GameStartCountdown(this).runTaskTimer(Core.getInstance(), 0, 20);
    }

    public void start() throws IOException {
        if (players.size() < 2) {
            broadcastMessage(Color.cc("&cCannot start game with less than 2 players!"));
            return;
        }
        GameManager.getInstance().createGame(); // create a new game everytime a game fills up

        for (int i = 0; i < players.size(); i++) {
            Profile profile = players.get(i);
            profile.getPlayer().getInventory().clear();
            profile.setCredits(15);
            if (profile.getHero() == null) {
                profile.setHero(Hero.randomHero(profile));
            }

            profile.getHero().initializeInventory(profile);
            profile.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 15 * 20, 255, true, false));
            if ((i % 2) == 0) {
                // team 1
                redTeam.getPlayers().add(profile);
                profile.updateTeam(redTeam);
                profile.getPlayer().teleport(redTeam.getSpawnPoint());
                profile.setPrefix(Color.cc("&c[RED] "));
            } else {
                // team 2
                blueTeam.getPlayers().add(players.get(i));
                profile.updateTeam(blueTeam);
                profile.getPlayer().teleport(blueTeam.getSpawnPoint());
                profile.setPrefix(Color.cc("&b[BLUE] "));
            }

        } // team distribution & anti damage

        this.tickTask = new GameTickTask(this).runTaskTimer(Core.getInstance(), 1, 20 * 4); //every 4 seconds??
        this.creditTask = new CreditAddTask(this).runTaskTimer(Core.getInstance(), 1, 20 * 10); // every 10 seconds add 5 credits?
        state = ACTIVE;
        players.forEach(profile -> profile.getBoard().update(false));
        
    }

    public boolean end() { // quick way to end the game without needing extra data
        if (this.state != ACTIVE) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cGame must be active to attempt a game ending."));
            return false;
        }
        if (redTeam.getScore() == blueTeam.getScore()) {
            end(null);
            return true;
        }
        end(redTeam.getScore() > blueTeam.getScore() ? redTeam : blueTeam);
        return true;
    }



    public void end(Team winner) {
        this.tickTask.cancel();
        this.creditTask.cancel();
        this.fastTickTask.cancel();
        for (Profile profile : players) {
            OverallStats.getInstance().pushData(profile); // add kills, deaths and bases to online stats
            profile.setPrefix(RankingManager.getRank(profile.getPlayer()).getPrefix());
        }

        if (winner == null) {
            // DRAW
            titleAll("&7&lDRAW!",
                    redTeam.getChatColor().toString() + redTeam.getScore() + " &7- " + blueTeam.getChatColor().toString() + blueTeam.getScore());
        } else {
            Team loser = winner == redTeam ? blueTeam : redTeam;

            titleAll(winner.getChatColor() + "&l" + winner.getColor() + " WON!",
                    winner.getChatColor().toString() + winner.getScore() + " &7- " + loser.getChatColor().toString() + loser.getScore());

        }

        Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> { // kik9
            for (Profile profile : players) {
                profile.getPlayer().performCommand(Core.getInstance().getConfig().getString("lobby-command"));
                profile.getPlayer().kickPlayer(Color.cc("&cGame ended.")); // since the lobby command isnt setup yet
            }
            ProfileManager.getInstance().clearProfiles(this);
        }, 100L);

        Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> GameManager.getInstance().endGame(this), 105L);

        soundAll(Sound.ENDERDRAGON_GROWL);

        players.forEach(profile -> profile.getBoard().update(false));

    } // end the game

    public void broadcastMessage(String message) {
        for (Profile p : players) {
            p.sendMessage(message);
        }
//        Bukkit.getConsoleSender().sendMessage(Color.cc("&6[GAME - " + activeWorld.getName() + "] &f" + message));
    } // send a message to everyone in the game

    

    @SuppressWarnings("deprecation")
    public void titleAll(String title, String subtitle) { // mine want to update
        // title to each player
        for (Profile profile : players) {
            profile.getPlayer().sendTitle(
                    Color.cc(title),
                    Color.cc(subtitle)
            );
        }
    }

    public void soundAll(Sound sound) {
        for (Profile profile : players) {
            Player p = profile.getPlayer();
            p.playSound(p.getLocation(), sound, 0.9f, 1.0f);
        }
    }

    public void particleAll(Location loc, Effect effect, int data, int radius) {
        loc.getWorld().playEffect(loc, effect, data, radius);
    }

    public Team getTeam(Team team) {
        return team == getRedTeam() ? getRedTeam() : getBlueTeam();
    }

}
