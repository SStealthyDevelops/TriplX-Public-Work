package minigames.triplxmc.skydomination.nms;

import org.bukkit.Bukkit;

public class Reflection {

    public static String version;

    static {
        version = Bukkit.getServer().getClass().getPackage().getName();
        version = version.substring(version.lastIndexOf(".") + 1);
    }

}
