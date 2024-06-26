package minigames.triplxmc.skydomination.achievements;

import lombok.Getter;
import org.bukkit.Bukkit;
import triplx.core.api.chat.Color;

public enum Achievement {


    // KILLS
    FIRST_KILL(1),
    HUNDREDTH_KILL(2),
    THOUSANDTH_KILL(3),

    // BASES
    BASE_CAPTURED(4),


    // WINS
    FIRST_WIN(5);

    @Getter
    private final int id;
    Achievement(int ID) {
        this.id = ID;
    }

    public static Achievement fromID(int id) {
        for (Achievement achievement : values()) {
            if (achievement.id == id) return achievement;
        }
        Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not find achievement with id: " + id));
        return null;
    }



}
