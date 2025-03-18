package com.example.uicrud;

import com.example.uicrud.DBConnection.Connection;
import com.example.uicrud.Models.ConnectionSet;
import com.mongodb.client.MongoClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;
import java.io.IOException;

public class CustomConnectConfigController {

    @FXML
    private TextField txtConnectionString, txtDbName, txtDbCollection;

    @FXML
    private PasswordField txtConnectionPassword;

    @FXML
    private Label lblStatus;

    private String finalConnectionString;  // Store the validated connection string

    @FXML
    protected void backToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Main");
        stage.show();
    }

    @FXML
    protected void checkConnection(ActionEvent event) {
        String rawConnectionString = txtConnectionString.getText();
        String password = txtConnectionPassword.getText();

        if (rawConnectionString.isEmpty() || password.isEmpty()) {
            lblStatus.setText("Error: Connection string and password required!");
            return;
        }

        // Temporarily test the connection
        try (MongoClient mongoClient = com.mongodb.client.MongoClients.create(rawConnectionString.replace("<db_password>", password))) {
            mongoClient.getDatabase("admin").runCommand(new Document("ping", 1));
            Platform.runLater(() -> lblStatus.setText("Connection successful!"));
        } catch (Exception e) {
            Platform.runLater(() -> lblStatus.setText("Connection failed: " + e.getMessage()));
            e.printStackTrace();
        }
    }


    @FXML
    protected void getCurrentConnection(ActionEvent event) {
        ConnectionSet connectionSet = ConnectionSet.getInstance();

        // Immediately display updated values
        txtConnectionString.setText(connectionSet.getConnectionString());
        txtConnectionPassword.setText(connectionSet.getConnectionPassword());
        txtDbName.setText(connectionSet.getDatabaseName());
        txtDbCollection.setText(connectionSet.getCollectionName());

        System.out.println("Current Connection: " + connectionSet.getConnectionString());
        System.out.println("Current Password: " + connectionSet.getConnectionPassword());
        System.out.println("Current Database: " + connectionSet.getDatabaseName());
        System.out.println("Current Collection: " + connectionSet.getCollectionName());
    }


    @FXML
    protected void connectToDatabase(ActionEvent event) {
        String databaseName = txtDbName.getText();
        String collectionName = txtDbCollection.getText();
        String connectionString = txtConnectionString.getText();
        String connectionPassword = txtConnectionPassword.getText();

        if (databaseName.isEmpty() || collectionName.isEmpty() || connectionString.isEmpty() || connectionPassword.isEmpty()) {
            lblStatus.setText("Error: Please fill in all fields!");
            return;
        }

        // Get the singleton instance
        ConnectionSet connectionSet = ConnectionSet.getInstance();

        // Update values in the singleton
        connectionSet.setConnectionString(connectionString);
        connectionSet.setConnectionPassword(connectionPassword);
        connectionSet.setDatabaseName(databaseName);
        connectionSet.setCollectionName(collectionName);

        Connection.initializeMongoClient();

        lblStatus.setText("Connected to DB: " + databaseName);
        System.out.println(connectionSet.getConnectionString());
    }



}
