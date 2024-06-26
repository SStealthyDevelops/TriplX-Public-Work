package triplx.hub.core.nms;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;


public class Reflection {

    @Getter
    @Setter
    private static Reflection instance;

    public void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void sendParticles(Player player, String particle, Float x, Float y, Float z, Float xOffset, Float yOffset, Float zOffset, Float count, Float speed) {
        try {
            Object enumParticles = getNMSClass("PacketPlayOutWorldParticlces").getDeclaredClasses()[0].getField(particle).get(null);


            Constructor<?> particleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0]
                    , getNMSClass("IChatBaseComponent")
                    , float.class
                    , float.class
                    , float.class
                    , float.class
                    , float.class
                    , float.class
                    , float.class
                    , float.class);
            Object packet = particleConstructor.newInstance(enumParticles, x, y, z, xOffset, yOffset, zOffset, count, speed);

            // sendPacket(player, packet);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
