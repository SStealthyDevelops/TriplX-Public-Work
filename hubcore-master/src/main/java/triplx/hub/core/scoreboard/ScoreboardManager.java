package triplx.hub.core.scoreboard;

import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.core.api.data.player.PlayerUniData;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;
import triplx.core.api.scoreboard.TScoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardManager {

    public static void sendScoreboard(Player player) {

        List<String> titles = new ArrayList<>();
        titles.add(Color.cc("&c&lTriplX Network"));
        titles.add(Color.cc("&6&lTriplX Network"));
        titles.add(Color.cc("&c&lTriplX Network"));
        titles.add(Color.cc("&6&lTriplX Network"));
        titles.add(Color.cc("&6&lT&c&lriplX Network"));
        titles.add(Color.cc("&c&lT&6&lr&c&liplX Network"));
        titles.add(Color.cc("&c&lTr&6&li&c&lplX Network"));
        titles.add(Color.cc("&c&lTri&6&lp&c&llX Network"));
        titles.add(Color.cc("&c&lTrip&6&ll&c&lX Network"));
        titles.add(Color.cc("&c&lTripl&6&lX &c&lN&c&letwork"));
        titles.add(Color.cc("&c&lTriplX &6&lN&c&letwork"));
        titles.add(Color.cc("&c&lTriplX N&6&le&c&ltwork"));
        titles.add(Color.cc("&c&lTriplX Ne&6&lt&c&lwork"));
        titles.add(Color.cc("&c&lTriplX Net&6&lw&c&lork"));
        titles.add(Color.cc("&c&lTriplX Netw&6&lo&c&lrk"));
        titles.add(Color.cc("&c&lTriplX Netwo&6&lr&c&lk"));
        titles.add(Color.cc("&c&lTriplX Networ&6&lk"));
        titles.add(Color.cc("&c&lTriplX Network"));


        Map<Integer, List<String>> lines = new HashMap<>();


        final Rank rank = RankingManager.getRank(player);
        final List<String> line1 = new ArrayList<>();
        line1.add(Color.cc("&f&lRank: " + rank.getPrefix().replace("[", "").replace("]", "")));

        final List<String> line2 = new ArrayList<>();
        line2.add(Color.cc("&f&lLevel: &b" + PlayerUniData.getLevel(player.getUniqueId())));

        final List<String> empty = new ArrayList<>();
        empty.add("  ");

        final List<String> empty2 = new ArrayList<>();
        empty2.add("");

        lines.put(4, empty);
        lines.put(3, line1);
        lines.put(2, empty2);
        lines.put(1, line2);


        TScoreboard scoreboard = new TScoreboard(titles, lines, 1);
        scoreboard.send(player);
    }

}
