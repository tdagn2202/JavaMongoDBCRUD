package com.example.uicrud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;

public class RViewController {
    @FXML
    protected void handleGetStudentById(){
        openResultDialog("STUDENT_ID");
    }

    @FXML
    protected void handleGetAllStudent(){
        openResultDialog("ALL");
    }

    @FXML
    protected void handleGetStudentByName() {
        openResultDialog("NAME");
    }

    @FXML
    protected void handleGetStudentByAge() {
        openResultDialog("AGE");
    }

    @FXML
    protected void handleGetStudentByClassId() {
        openResultDialog("CLASS_ID");
    }

    @FXML
    protected void backToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Main");
        stage.show();
        System.out.println("Clicked");
    }

    private void openResultDialog(String queryType){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RResultView.fxml"));
            AnchorPane resultRoot = fxmlLoader.load();

            RResultViewController controller = fxmlLoader.getController();

            controller.loadData(queryType);

            Dialog<Void> dialog = new Dialog<>();
            dialog.getDialogPane().setContent(resultRoot);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

            dialog.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
