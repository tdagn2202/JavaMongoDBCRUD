package com.example.uicrud.DBConnection;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.example.uicrud.Models.ConnectionSet;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.LoggerFactory;

public class Connection {
    static Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    static {
        root.setLevel(Level.INFO);
    }

    private static MongoClient mongoClient = null;

    public static MongoClient getMongoClient() {
        if (mongoClient == null) {
            initializeMongoClient();
        }
        return mongoClient;
    }

    public static void initializeMongoClient() {
        closeMongoClient(); // Ensure old connection is closed
        String connectionString = ConnectionSet.getInstance().getConnectionString();
        System.out.println("Initializing MongoClient with: " + connectionString);
        mongoClient = MongoClients.create(connectionString);
    }

    public static void closeMongoClient() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    public static void createConnection() {
        try {
            initializeMongoClient();
            System.out.println("Connection established!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
