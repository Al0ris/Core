package me.joseph.core.data;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import me.joseph.core.Core;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.UUID;

public class PlayerDataDIO {

    private MongoCollection<Document> collection;

    public PlayerDataDIO(ConnectionManager connectionManager) {
        Core main = connectionManager.getMain();
        MongoClient client = connectionManager.getClient();
        String databaseName = main.getConfig().getString("databaseName");
        if (databaseName == null) {
            main.getCoreLogger().logError("No database name found in config");
            return;
        }
        MongoDatabase database = client.getDatabase(databaseName);
        String collectionName = main.getConfig().getString("collectionName");
        if (collectionName == null) {
            main.getCoreLogger().logError("No collection name found in config");
            return;
        }
        this.collection = database.getCollection(collectionName);
    }

    public void saveToDatabase(CorePlayer corePlayer) {
        Document document = corePlayer.toDocument();
        if (exists(corePlayer)) {
            collection.findOneAndReplace(Filters.eq("playerUUID", corePlayer.getUuid().toString()), document);
            return;
        }
        collection.insertOne(document);
    }

    public CorePlayer getPlayerFromDB(Player player) {
        Document doc = collection.find(Filters.eq("playerUUID", player.getUniqueId().toString())).first();
        if (doc == null) {
            return new CorePlayer(player);
        }
        return new CorePlayer(UUID.fromString(doc.getString("playerUUID")), doc.getInteger("level"), doc.getDouble("xp"));
    }

    public boolean exists(Player player) {
        return exists(player.getUniqueId());
    }

    public boolean exists(CorePlayer corePlayer) {
        return exists(corePlayer.getUuid());
    }


    private boolean exists(UUID uuid) {
        long count = collection.countDocuments(new Document("playerUUID", uuid.toString()));

        return count > 0;
    }
}
