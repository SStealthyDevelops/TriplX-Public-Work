package triplx.core.api.handlers;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import triplx.core.api.TriplXAPI;
import triplx.core.api.chat.Color;
import triplx.core.api.data.MongoManager;

public class APIReloadHandler {

    @Getter
    @Setter
    private static APIReloadHandler instance;

    public void reload(CommandSender sender) {
        // MongoReload
        MongoManager.setUri(new MongoClientURI("[PRIVATE]"));
        MongoManager.setMongoClient(new MongoClient(MongoManager.getUri()));
        MongoManager.setDatabase(MongoManager.getMongoClient().getDatabase("triplx-core"));
        sender.sendMessage(Color.cc("&a  - MongoDB connection re-established.."));

        sender.sendMessage(Color.cc("&c&l[TRIPLX] &cReloaded &aDevAPI " + TriplXAPI.getInstance().getDescription().getVersion() + "&c."));
    }

    public void reload() {
        // MongoReload
        MongoManager.setUri(new MongoClientURI("[PRIVATE]]"));
        MongoManager.setMongoClient(new MongoClient(MongoManager.getUri()));
        MongoManager.setDatabase(MongoManager.getMongoClient().getDatabase("triplx-core"));
    }

}
