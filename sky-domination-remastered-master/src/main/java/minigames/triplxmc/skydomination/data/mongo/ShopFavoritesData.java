package minigames.triplxmc.skydomination.data.mongo;

import com.mongodb.client.MongoCollection;
import minigames.triplxmc.skydomination.inventory.guis.game.shops.personal.PersonalShopItem;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;

import java.util.*;

@SuppressWarnings("unused")
public class ShopFavoritesData {

    public MongoCollection<Document> collection = MongoManager.getDatabase().getCollection("shop-favorites");

    public boolean playerExists(final UUID uuid) {
        return collection.find(new Document("uuid", uuid.toString())).first() != null;
    }

    public void createPlayer(final Player player) {
        if (!playerExists(player.getUniqueId())) {
            Document document = new Document("uuid", player.getUniqueId().toString()).append("favorites", "");
            collection.insertOne(document);
        }
    }

    public Map<Integer, PersonalShopItem> getFavorites(final UUID uuid) {
        Map<Integer, PersonalShopItem> favorites = new HashMap<>();

        Document document = collection.find(new Document("uuid", uuid.toString())).first();

        String[] mapString = document.getString("favorites").split(";");
        for (String map : mapString) {
            try {
                Integer slot = Integer.parseInt(map.split(":")[0]);
                int id = Integer.parseInt(map.split(":")[1]);
                favorites.put(slot, PersonalShopItem.getItem(id));
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not create Item map for " + Arrays.toString(mapString)));
                e.printStackTrace();
            }
        }




        return favorites;
    }

}
