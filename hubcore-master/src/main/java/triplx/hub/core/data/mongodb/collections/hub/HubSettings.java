package triplx.hub.core.data.mongodb.collections.hub;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import triplx.core.api.chat.Color;
import triplx.hub.core.Hubcore;
import triplx.hub.core.data.mongodb.MongoManager;

public class HubSettings {

    @SuppressWarnings("all")
    private static MongoCollection collection = MongoManager.getDatabase().getCollection("hub-settings");

    public static final boolean debugMode = Hubcore.getConfiguration().getBoolean("debug-mode");


    public static SettingClass settings(String HUBTYPE) {
        Document document;
        try {
            document = (Document) collection.find(new Document("hub-type", HUBTYPE)).first();
        } catch (NullPointerException e) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not find hub-type: " + HUBTYPE));
            document = (Document) collection.find(new Document("hub-type", "MAIN")).first();
        }

        String xLimits = document.getString("world-limit-x");

        String zLimits = document.getString("world-limit-z");
        int voidLimit = document.getInteger("void-teleport-level");


        String[] spawn = document.getString("spawnpoint").split(" ");
        // 1 1 1 90 90
        double spawnX = Double.parseDouble(spawn[0]);
        double spawnY = Double.parseDouble(spawn[1]);
        double spawnZ = Double.parseDouble(spawn[2]);
        double yaw = Double.parseDouble(spawn[3]);
        double pitch = Double.parseDouble(spawn[4]);


        return new SettingClass(xLimits, zLimits, voidLimit, spawnX, spawnY, spawnZ, yaw, pitch);
    }

}
