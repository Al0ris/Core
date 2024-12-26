package me.joseph.core.data;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import me.joseph.core.Core;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {

    private final Core main;
    private final String connectionString;
    private MongoClient client;

    public ConnectionManager(Core main) {
        this.main = main;
        this.connectionString = main.getConfig().getString("mongoConnectionString");
    }

    public void connect() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        client = MongoClients.create(connectionString);
        main.getCoreLogger().logInfo("=> Connection Successful: " + preFlightChecks(client));
        List<Document> databases = client.listDatabases().into(new ArrayList<>());
        databases.forEach(db -> main.getCoreLogger().logInfo(db.toJson()));
    }

    private boolean preFlightChecks(MongoClient mongoClient) {
        Document ping = new Document("ping", 1);
        Document response = mongoClient.getDatabase("admin").runCommand(ping);
        main.getCoreLogger().logInfo("Response to ping: ");
        main.getCoreLogger().logInfo(response.toJson(JsonWriterSettings.builder().indent(true).build()));
        return response.get("ok", Number.class).intValue() == 1;
    }

    public MongoClient getClient() {
        return client;
    }

    public Core getMain() {
        return main;
    }
}
