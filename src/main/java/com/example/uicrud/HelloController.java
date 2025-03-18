package com.example.uicrud;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private MenuItem menuButton;

    @FXML
    MenuItem mnuExit;

    @FXML
    private Button switchToCreateButton, switchToReadButton, switchToUpdateButton, switchToDeleteButton;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


    @FXML
    protected void handleSchemaButtonClicked() {
        System.out.println("Clickedd");
    }

    @FXML
    protected void onSwitchToCreateClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CView.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Create new document");
        stage.show();
        System.out.println("Clicked");
    }

    @FXML
    protected void onSwitchToReadClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("RView.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Create new document");
        stage.show();
        System.out.println("Clicked");
    }

    @FXML
    protected void onSwitchToUpdateClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UView.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Create new document");
        stage.show();
        System.out.println("Clicked");
    }

    @FXML
    protected void onSwitchToDeleteClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DView.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Create new document");
        stage.show();
        System.out.println("Clicked");
    }

    @FXML
    protected void handleExit(ActionEvent event) throws IOException {
        Platform.exit();
    }

    @FXML
    private void viewSchema(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewSchema.fxml"));
            Parent root = loader.load();

            // Get the current stage from the menu item's event
            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}