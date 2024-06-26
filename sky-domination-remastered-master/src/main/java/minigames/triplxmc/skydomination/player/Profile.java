package minigames.triplxmc.skydomination.player;

import lombok.Getter;
import lombok.Setter;
import minigames.triplxmc.skydomination.Core;
import minigames.triplxmc.skydomination.achievements.Achievement;
import minigames.triplxmc.skydomination.data.mongo.AchievementsData;
import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.game.GameManager;
import minigames.triplxmc.skydomination.hero.Hero;
import minigames.triplxmc.skydomination.inventory.guis.game.shops.personal.PersonalShopItem;
import minigames.triplxmc.skydomination.player.team.Team;
import minigames.triplxmc.skydomination.scoreboard.GameBoard;
import minigames.triplxmc.skydomination.tasks.PlayerRespawn;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import triplx.core.api.chat.Color;
import triplx.core.api.utils.NameTagManager;

import java.util.*;

@Getter
public class Profile {

    private Team team; // team on
    private int kills; // players killed
    private int deaths; // times died
    @Setter private int bases; // bases captured

    @Setter private Hero hero;
    @Setter private boolean spectator;

    @Setter private int credits;

    @Setter public Map<Integer, PersonalShopItem> favorites;

    @Setter private long lastAttack;
    @Setter private Player lastAttacker;

    private final Player player;
    private final UUID uuid;

    private final List<Achievement> achievements;

    @Getter @Setter private GameBoard board;

    @Setter private String actionBar;

    public Profile(Player player) {
        favorites = new HashMap<>();
        this.player = player;
        this.uuid = player.getUniqueId();
        this.achievements = Arrays.asList(AchievementsData.getInstance().getAchievements(player.getUniqueId()));
    }

    public void sendMessage(String message) {
        player.sendMessage(Color.cc(message));
    }

    public void addKill() {
        if (this.achievements.contains(Achievement.FIRST_KILL)) {
            achievements.add(Achievement.FIRST_KILL);
        }
        this.kills++;
    }

    public void addDeath() {
        this.deaths++;
    }

    public boolean updateTeam(Team team) {
        if (team.getPlayers().size() >= (team.getGame().getMatchSize()/2)) {
            return false;
        }
        this.team = team;
        return true;
    }

    public boolean buyItem(PersonalShopItem item) { // this doesn't account for ENCHANTMENTs // TODO
        if (credits >= item.getPrice()) {
            credits -= item.getPrice();
            player.getInventory().addItem(item.getIcon().getStack());
            player.sendMessage(Color.cc("&aBought " + item.getIcon().getMeta().getDisplayName() + " &afor " + item.getPrice() + "."));
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 0.3f, 1.0f);
            return true;
        }
        player.playSound(player.getLocation(), Sound.ANVIL_LAND, 0.15f, 1.0f);
        sendMessage(Color.cc("&cYou are " + (item.getPrice() - credits) + " credits short to buy that!"));
        return false;
    }

    public Game getGame() {
        return GameManager.getInstance().getGame(player);
    }

    public void setPrefix(String prefix) {
        NameTagManager.getInstance().setPrefix(this.player, Color.cc(prefix));
    }

    public void setSpectator() {
        sendMessage("Spectator");
        this.getPlayer().getInventory().clear();
        this.spectator = true;
        Player player = this.getPlayer();
        player.getInventory().setArmorContents(null);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 255, true, false));
        player.teleport(player.getLocation().add(0, 20, 0));
        player.setAllowFlight(true);
        new PlayerRespawn(this).runTaskLater(Core.getInstance(), 100L); // 5 seconds
    }

}
