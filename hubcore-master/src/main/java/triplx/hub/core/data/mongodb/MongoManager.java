package triplx.hub.core.data.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;

public class MongoManager {

    @Getter
    private static MongoClientURI uri;

    @Getter
    private static MongoClient mongoClient;

    @Getter
    private static MongoDatabase database;


    public static void init() {
        uri = new MongoClientURI("[PRIVATE]");
        mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase("triplx-hub");
    }



}
