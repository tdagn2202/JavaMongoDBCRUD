package com.example.uicrud;

import com.example.uicrud.MongDBMethods.MongoDBMethos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CViewController {

    @FXML
    Button btnInsertOne, btnInsertMany, btnNextDoc;
    @FXML
    TextField txtStudentName, txtStudentAge, txtClassId, txtClassName;

    private List<Document> listOfNewDoc = new ArrayList<>();
    private int numOfDocs = 0;

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

    @FXML
    protected void handleBtnInsertOneClick(ActionEvent event) {
        String studentName, className;
        Integer studentAge, classId;

        studentName = txtStudentName.getText();
        className = txtClassName.getText();

        studentAge = Integer.valueOf(txtStudentAge.getText());
        classId = Integer.valueOf(txtClassId.getText());

        Document document = new Document();
        document.append("student_name", studentName);
        document.append("student_age", studentAge);
        document.append("class_id", classId);
        document.append("class_name", className);

        MongoDBMethos.insertOne(document);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Notification");
        alert.setContentText("Successfully inserted");
        alert.show();
    }

    @FXML
    protected void handleBtnNextValueClick(ActionEvent event) {
        String studentName = txtStudentName.getText();
        String className = txtClassName.getText();
        Integer studentAge = Integer.valueOf(txtStudentAge.getText());
        Integer classId = Integer.valueOf(txtClassId.getText());

        Document document = new Document();
        document.append("student_name", studentName);
        document.append("student_age", studentAge);
        document.append("class_id", classId);
        document.append("class_name", className);

        listOfNewDoc.add(document);
        numOfDocs++;

        btnNextDoc.setText("Record changes (" + numOfDocs + ")");

        txtStudentName.clear();
        txtStudentAge.clear();
        txtClassId.clear();
        txtClassName.clear();
    }

    @FXML
    protected void handleBtnInsertManyClick(ActionEvent event) {
        if (!listOfNewDoc.isEmpty()) {
            MongoDBMethos.insertMany(listOfNewDoc);
            listOfNewDoc.clear();
            numOfDocs = 0;
            btnNextDoc.setText("Record changes");


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("All documents inserted successfully!");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("No documents to insert.");
            alert.show();
        }
    }


}
