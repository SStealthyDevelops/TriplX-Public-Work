package minigames.triplxmc.skydomination.player.team;

import lombok.Getter;
import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.player.Profile;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Team {

   private final List<Profile> players;
   private final TeamColor color;
   private final Location spawnPoint;

   private int score;

   private final ChatColor chatColor;

   private final Game game;

   public Team(Game game, TeamColor color, Location spawnPoint) {
       this.players = new ArrayList<>();
       this.color = color;
       this.chatColor = color.getColor();
       this.spawnPoint = spawnPoint;
       this.game = game;
   }

   public void addScore(int increment) {
       score += increment;
       if (score >= 100) {
           game.end(this);
       }
   }

   public void sendMessage(String message) {
       for (Profile profile : players) {
           profile.sendMessage(message);
       }
   }

}
