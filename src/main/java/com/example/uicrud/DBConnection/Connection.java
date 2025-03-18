package com.example.uicrud.DBConnection;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.LoggerFactory;



public class Connection {

    static Logger root = (Logger) LoggerFactory
            .getLogger(Logger.ROOT_LOGGER_NAME);

    static {
        root.setLevel(Level.INFO);
    }
    private static final String CONNECTION_STRING = "mongodb+srv://dangb2203551:abc@cluster0.nuxcv.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static MongoClient mongoClient = null;

    public static MongoClient getMongoClient() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_STRING);
        }
        return mongoClient;
    }

    public static void createConnection() {
        try {
            MongoClient client = getMongoClient();
            System.out.println("Connection established!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
