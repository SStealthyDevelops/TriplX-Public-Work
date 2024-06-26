package minigames.triplxmc.skydomination.data.mongo;

import com.mongodb.client.MongoCollection;
import core.triplxmc.world.Core;
import lombok.Getter;
import lombok.Setter;
import minigames.triplxmc.skydomination.player.Profile;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;

import java.util.UUID;

public class OverallStats {

    MongoCollection<Document> collection = MongoManager.getDatabase().getCollection("overall-stats");

    @Getter
    @Setter
    private static OverallStats instance;

    public boolean playerExists(UUID uuid) {
        return collection.find(new Document("uuid", uuid.toString())).first() != null;
    }

    public void createPlayer(UUID uuid) {
        if (!playerExists(uuid)) {
            Document document = new Document("uuid", uuid.toString())
                    .append("kills", 0)
                    .append("deaths", 0)
                    .append("games-played", 0)
                    .append("bases-captured", 0);

            collection.insertOne(document);
        }
    }

    private Document getDoc(UUID uuid) {
        return collection.find(new Document("uuid", uuid.toString())).first();
    }

    public int getKills(UUID uuid) {
        return getDoc(uuid).getInteger("kills");
    }

    public int getDeaths(UUID uuid) {
        return getDoc(uuid).getInteger("deaths");
    }

    public int getGamesPlayed(UUID uuid) {
        return getDoc(uuid).getInteger("games-played");
    }

    public int getBasesCaptures(UUID uuid) {
        return getDoc(uuid).getInteger("bases-captured");
    }


    @SuppressWarnings("unused")
    // might not be needed.....?
    public double getKDR(UUID uuid) { // kill : death ratio
        double kdr = ((double) getKills(uuid) / (double) getDeaths(uuid));
        kdr = Math.round(kdr * 100.0) / 100.0;
        return kdr;
    }

    private void update(UUID uuid, String key, Object newVal) {
        Document doc = collection.find(new Document("uuid", uuid.toString())).first();

        if (doc != null) {
            Bson updatedValue = new Document(key, newVal);
            Bson updateOp = new Document("$set", updatedValue);

            collection.updateOne(doc, updateOp);
        }

    }


    public void pushData(final Profile profile) {
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
            final int kills = profile.getKills();
            final int deaths = profile.getDeaths();
            UUID u = profile.getPlayer().getUniqueId();
            update(u, "kills", getKills(u) + kills);
            update(u, "deaths", getDeaths(u) + deaths);
            update(u, "games-played", getGamesPlayed(u) + 1);
            update(u, "bases-captures", getBasesCaptures(u) + profile.getBases());
        });
    }

}
