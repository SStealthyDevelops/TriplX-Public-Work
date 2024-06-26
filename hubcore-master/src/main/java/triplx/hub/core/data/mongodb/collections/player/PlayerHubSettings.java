package triplx.hub.core.data.mongodb.collections.player;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bson.conversions.Bson;
import triplx.hub.core.cache.player.UserCache;
import triplx.hub.core.cache.player.UserCacheManager;
import triplx.hub.core.data.mongodb.MongoManager;
import triplx.hub.core.utils.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerHubSettings {

    @Getter
    @Setter
    private static PlayerHubSettings instance;

    @SuppressWarnings("all")
    private MongoCollection collection = MongoManager.getDatabase().getCollection("user-settings");

    public boolean playerExists(UUID uuid) {
        Document doc = (Document) collection.find(new Document("uuid", uuid.toString())).first();
        return doc != null;
    }

    @SuppressWarnings("all")
    public void createUser(UUID uuid) {
        if (playerExists(uuid)) {
            return;
        }

        Document document = new Document("uuid", uuid.toString())
                .append("favorite-games", "")
                .append("player-visibility", false)
                .append("flight", false);

        collection.insertOne(document);
    }

    public List<Game> getFavorites(UUID uuid) {
        Document doc = (Document) collection.find(new Document("uuid", uuid.toString())).first();
        String[] games = doc.getString("favorite-games").split(" ");
        List<Game> favs = new ArrayList<>();

        if (games.length == 0) {
            return favs;
        }

        try {
            for (String game : games) {
                int i = Integer.parseInt(game); // throws number format exception if 'games' is empty
                Game g = Game.getGame(i);
                favs.add(g);
            }
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
        return favs;
    }

    public boolean isFlying(UUID uuid) {
        Document document = (Document) collection.find(new Document("uuid",uuid.toString())).first();
        return document.getBoolean("flight");
    }

    public void setFlying(UUID uuid, Boolean flying) {
        UserCacheManager.getInstance().findUser(uuid).setFlying(flying);
        update(uuid, "flight", flying);
    }

    public void update(UUID uuid, String key, Object newVal) {
        Document doc = (Document) collection.find(new Document("uuid", uuid.toString())).first();

        if (doc != null) {
            Bson updatedValue = new Document(key, newVal);
            Bson updateOp = new Document("$set", updatedValue);

            collection.updateOne(doc, updateOp);
        }

    }

    public boolean arePlayersVisible(UUID uuid) {
        Document document = (Document) collection.find(new Document("uuid",uuid.toString())).first();
        return document.getBoolean("player-visibility");
    }

    public boolean setPlayerVisibility(UUID uuid, boolean b) {
        UserCacheManager.getInstance().findUser(uuid).setPlayerVisibility(b);
        update(uuid, "player-visibility", b);
        return b;
    }






}

