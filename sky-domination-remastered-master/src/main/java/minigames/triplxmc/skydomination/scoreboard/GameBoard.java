package minigames.triplxmc.skydomination.scoreboard;

import lombok.Getter;
import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.game.GameState;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.team.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import triplx.core.api.chat.Color;

@Getter
public class GameBoard {

    private final Profile profile;
    private final Game game;

    private Scoreboard board;

    private int titleIndex = 0;

    public GameBoard(Profile profile, Game game) {
        this.profile = profile;
        this.game = game;
    }

    public void update(boolean tick) {

        board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("GameBoard", "Dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(Color.cc(title[0]));

        assert game != null;
        GameState state = game.getState();

        if (tick) {
            objective.setDisplayName(Color.cc(title[titleIndex]));
            this.profile.getPlayer().setScoreboard(board);
        }


        titleIndex++;
        if (titleIndex >= title.length) titleIndex = 0;


        switch (state) {
            case LOBBY:
            case STARTING:
                Team players = board.registerNewTeam("players");
                Team heroSelection = board.registerNewTeam("hero");
                Team teamSelection = board.registerNewTeam("team");

                heroSelection.setPrefix("");
                teamSelection.setPrefix("");

                heroSelection.addEntry(Color.cc("&eHero Selection: "));
                String heroName = profile.getHero() == null ? "&7None" : profile.getHero().getName();
                heroSelection.setSuffix(Color.cc(heroName));

                teamSelection.addEntry(Color.cc("&eTeam Selection: "));
                String teamSelect = profile.getTeam() == null ? Color.cc("&7None") : (profile.getTeam().getColor() == TeamColor.RED ? Color.cc("&c[RED]") : Color.cc("&b[BLUE]"));
                teamSelection.setSuffix(teamSelect);

                players.setPrefix("");
                players.addEntry(Color.cc("&ePlayers: "));
                players.setSuffix(Color.cc("&b" + game.getPlayers().size() + "&e/&b12"));


                objective.getScore(Color.cc("&ePlayers: ")).setScore(2);
                objective.getScore(Color.cc("&6play.triplxmc.net")).setScore(0);
                objective.getScore(" ").setScore(1);
                objective.getScore(Color.cc("&eTeam Selection: ")).setScore(4);
                objective.getScore(Color.cc("&eHero Selection: ")).setScore(5);
                objective.getScore("      ").setScore(3);


                break;
            case ACTIVE:

                Team redScore = board.registerNewTeam("redScore");
                Team blueScore = board.registerNewTeam("blueScore");
                Team credits = board.registerNewTeam("creds");
                Team kills = board.registerNewTeam("kills");

                redScore.addEntry(Color.cc("&c&lRED: "));
                blueScore.addEntry(Color.cc("&b&lBLUE: "));

                objective.getScore(Color.cc("&b&lBLUE: ")).setScore(7);
                objective.getScore(Color.cc("&c&lRED: ")).setScore(8);
                objective.getScore("").setScore(4);
                objective.getScore("  ").setScore(9);
                objective.getScore(" ").setScore(6);
                objective.getScore("   ").setScore(2);
                objective.getScore("     ").setScore(1);
                objective.getScore(Color.cc("&6play.triplxmc.net")).setScore(0);

                credits.addEntry(Color.cc("&eCredits: "));
                objective.getScore(Color.cc("&eCredits: ")).setScore(5);
                credits.setSuffix(Color.cc("&6" + profile.getCredits()));

                kills.addEntry(Color.cc("&eKills: "));
                objective.getScore(Color.cc("&eKills: ")).setScore(3);
                kills.setSuffix(Color.cc("&f" + profile.getKills()));

                redScore.setPrefix("");
                blueScore.setPrefix("");
                kills.setPrefix("");
                credits.setPrefix("");


                redScore.setSuffix(Color.cc("&6" + game.getRedTeam().getScore()));
                blueScore.setSuffix(Color.cc("&6" + game.getBlueTeam().getScore()));

                break;
            case END:

                break;
        }

    }


    private final String[] title = new String[]{
            "&b&lSky Domination",
            "&e&lSky Domination",
            "&b&lSky &e&lDomination",
            "&e&lSky &b&lDomination",
            "&b&lS&e&lky Domination",
            "&e&lS&b&lk&e&ly Domination",
            "&e&lSk&b&ly &e&lDomination",
            "&e&lSky &b&lD&e&lomination",
            "&e&lSky D&b&lo&e&lmination",
            "&e&lSky Do&b&lm&e&lination",
            "&e&lSky Dom&b&li&e&lnation",
            "&e&lSky Domi&b&ln&e&lation",
            "&e&lSky Domin&b&la&e&ltion",
            "&e&lSky Domina&b&lt&e&lion",
            "&e&lSky Dominat&b&li&e&lon",
            "&e&lSky Dominati&b&lo&e&ln",
            "&e&lSky Dominatio&b&ln"
    };





}
