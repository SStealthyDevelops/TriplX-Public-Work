package triplx.core.api.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.Setter;

public class MongoManager {

    @Getter
    @Setter
    private static MongoClientURI uri;

    @Getter
    @Setter
    private static MongoClient mongoClient;

    @Getter
    @Setter
    private static MongoDatabase database;


    public static void init() {
        uri = new MongoClientURI("[PRIVATE]");
        mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase("triplx-core");
        // collection = database.getCollection("ranking");
    }

}
