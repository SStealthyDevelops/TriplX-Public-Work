package triplx.hub.core.utils;


import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;

@Getter
public enum Game {

    SKYDOMINATION("Sky Domination", "&b&lSky Domination", 1, Material.DIAMOND_SWORD);


    private final int ID;
    private final String name;
    private final Material icon;
    private final String coloredTitle;

    Game(String name, String coloredTitle, int ID, Material icon) {
        this.ID = ID;
        this.name = name;
        this.icon = icon;
        this.coloredTitle = ChatColor.translateAlternateColorCodes('&', coloredTitle);
    }

    public static Game getGame(int i) {
        for (Game g : Game.values()) {
            if (g.getID() == i) {
                return g;
            }
        }
        System.out.println("Could not find game: " + i);
        return SKYDOMINATION;
    }

}
