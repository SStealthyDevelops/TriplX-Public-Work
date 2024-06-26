package minigames.triplxmc.skydomination.data.mongo;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import lombok.Setter;
import minigames.triplxmc.skydomination.achievements.Achievement;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AchievementsData {

    @Getter
    @Setter
    private static AchievementsData instance;

    public MongoCollection<Document> collection = MongoManager.getMongoClient().getDatabase("player-achievements").getCollection("skydomination-achievements");

    public boolean playerExists(final UUID uuid) {
        return collection.find(new Document("uuid", uuid.toString())).first() != null;
    }

    public void createPlayer(final Player player) {
        if (playerExists(player.getUniqueId())) return;
        Document document = new Document("uuid", player.getUniqueId().toString());
        document.append("achievements", "");
        collection.insertOne(document);
    }

    private void update(final UUID uuid, final String key, final Object newVal) {
        Document doc = collection.find(new Document("uuid", uuid.toString())).first();

        if (doc != null) {
            Bson updatedValue = new Document(key, newVal);
            Bson updateOp = new Document("$set", updatedValue);

            collection.updateOne(doc, updateOp);
        }

    }


    public Achievement[] getAchievements(final UUID uuid) {
        List<Achievement> achievementList = new ArrayList<>();

        Document doc = collection.find(new Document("uuid", uuid.toString())).first();
        String achievementStr = doc.getString("achievements");

        if (achievementStr.equals("")) return new Achievement[0];

        for (String str : achievementStr.split(" ")) {
            try {
                int i = Integer.parseInt(str);
                Achievement achievement = Achievement.fromID(i);
                achievementList.add(achievement);
            } catch (NumberFormatException | NullPointerException ignore) {
                Bukkit.getConsoleSender().sendMessage(Color.cc("Could not find Achievement:" + str));
            }
        }

        return achievementList.toArray(new Achievement[0]);
    }

    private String getAchievementString(final UUID uuid) {
        return collection.find(new Document("uuid", uuid.toString())).first().getString("achievements");
    }

    public void addAchievement(final UUID uuid, final Achievement achievement) {
        for (String str : getAchievementString(uuid).split(" ")) {
            if (str.equals(achievement.getId() + "")) {
                return;
            }
        }
        update(uuid, "achievements", getAchievementString(uuid) + " " + achievement.getId());
    }

}
