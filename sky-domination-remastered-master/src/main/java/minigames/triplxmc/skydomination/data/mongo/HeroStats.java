package minigames.triplxmc.skydomination.data.mongo;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import lombok.Setter;
import minigames.triplxmc.skydomination.hero.Hero;
import org.bson.Document;

import java.util.UUID;

public class HeroStats {

    @Getter
    @Setter
    private static HeroStats instance;

    private final MongoCollection<Document> collection = MongoManager.getDatabase().getCollection("hero-stats");

    public boolean playerExists(final UUID uuid) {
        return collection.find(new Document("uuid", uuid.toString())).first() != null;
    }

    public void createPlayer(final UUID uuid) {
        if (playerExists(uuid)) return;

        Document document = new Document("uuid", uuid.toString());

        Document overkill = new Document();
        overkill.append("kills", 0).append("deaths", 0).append("games-played", 0).append("ability-1", 0).append("ability-2", 0).append("bases-captured", 0);

        document.append("overkill", overkill);
    }

    private Document getDoc(final UUID uuid) {
        return collection.find(new Document("uuid", uuid.toString())).first();
    }

    public HeroStat getStats(UUID uuid, Hero hero) {
        Document doc = (Document) getDoc(uuid).get(hero.getKey());
        int kills = doc.getInteger("kills");
        int deaths = doc.getInteger("deaths");
        int games = doc.getInteger("games-played");
        int abilityOne = doc.getInteger("ability-1");
        int abilityTwo = doc.getInteger("ability-2");
        int basesCaptured = doc.getInteger("bases-captured");
        return new HeroStat(kills, deaths, games, abilityOne, abilityTwo, basesCaptured);
    }

}
