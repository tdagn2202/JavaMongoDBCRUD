package com.example.uicrud.Models;

public class ConnectionSet {
    private static ConnectionSet instance;

    private String rawConnectionString = "mongodb+srv://dangb2203551:<db_password>@cluster0.nuxcv.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private String connectionPassword = "abc";
    private String connectionString;
    private String databaseName = "SampleCRUD";
    private String collectionName = "Student";

    public ConnectionSet() {
        updateConnectionString();
    }

    public static ConnectionSet getInstance() {
        if (instance == null) {
            instance = new ConnectionSet();
        }
        return instance;
    }

    private void updateConnectionString() {
        this.connectionString = rawConnectionString.replace("<db_password>", connectionPassword);
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.rawConnectionString = connectionString;
        updateConnectionString();
    }

    public void setConnectionPassword(String connectionPassword) {
        this.connectionPassword = connectionPassword;
        updateConnectionString();
    }

    public String getConnectionPassword() {
        return connectionPassword;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}
