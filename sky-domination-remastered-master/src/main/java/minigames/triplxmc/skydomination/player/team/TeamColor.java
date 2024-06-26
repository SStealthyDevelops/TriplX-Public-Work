package minigames.triplxmc.skydomination.player.team;

import lombok.Getter;
import org.bukkit.ChatColor;

public enum TeamColor {

    RED(ChatColor.RED), BLUE(ChatColor.AQUA);

    @Getter
    private final ChatColor color;
    TeamColor(ChatColor color) {
        this.color = color;
    }

}
