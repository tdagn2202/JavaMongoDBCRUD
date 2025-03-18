package com.example.uicrud;

import com.example.uicrud.DBConnection.Connection;
import com.example.uicrud.Models.Student;
import com.example.uicrud.MongDBMethods.MongoDBMethos;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;


public class DViewController {

    @FXML
    TextField txtId, txtName;

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
    protected void deleteStudentById(ActionEvent event) throws IOException {
        MongoClient mongoClient = Connection.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SampleCRUD");
        MongoCollection<Document> studentCollection = database.getCollection("Student");

        ObjectId studentId = new ObjectId(txtId.getText());

        MongoDBMethos.deleteStudentById(String.valueOf(studentId));
        showAlert("Notification", "Student has been successfully deleted!");
        txtId.clear();
        txtName.clear();
    }

    @FXML
    protected void checkAvailable (ActionEvent event) throws IOException {
        MongoClient mongoClient = Connection.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("SampleCRUD");
        MongoCollection<Document> studentCollection = database.getCollection("Student");
        Student result = new Student();

        String studentId = txtId.getText();
        for (Document doc : studentCollection.find(Filters.eq("_id", new ObjectId(studentId)))) {
            result = convertToStudent(doc);
        }

        txtName.setText(result.getStudentName());
    }

    private Student convertToStudent(Document doc) {
        return new Student(
                doc.getString("student_name"),
                doc.getInteger("student_age"),
                doc.getInteger("class_id"),
                doc.getString("class_name")
        );
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
