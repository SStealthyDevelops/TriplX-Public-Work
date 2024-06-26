package triplx.core.ranking.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

public class MongoManager {

    @Getter
    private static MongoClientURI uri;

    @Getter
    private static MongoClient mongoClient;

    @Getter
    private static MongoDatabase database;

    @Getter
    private static MongoCollection<Document> collection;

    public static void init() {
        uri = new MongoClientURI("[PRIVATE]");
        mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase("triplx-core");
        collection = database.getCollection("ranking");
    }



}
