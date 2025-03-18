package com.example.uicrud;

import com.example.uicrud.DBConnection.Connection;
import com.example.uicrud.Models.ConnectionSet;
import com.example.uicrud.Models.Student;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Optional;
import java.util.Scanner;

public class RResultViewController {

    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> colStudentName;
    @FXML
    private TableColumn<Student, Integer> colStudentAge;
    @FXML
    private TableColumn<Student, Integer> colClassId;
    @FXML
    private TableColumn<Student, String> colClassName;

    private final ObservableList<Student> studentList = FXCollections.observableArrayList();
    static ConnectionSet connectionSet = ConnectionSet.getInstance();

    @FXML
    public void initialize() {
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colStudentAge.setCellValueFactory(new PropertyValueFactory<>("studentAge"));
        colClassId.setCellValueFactory(new PropertyValueFactory<>("classId"));
        colClassName.setCellValueFactory(new PropertyValueFactory<>("className"));
        studentTable.setItems(studentList);
        System.out.println("studentTable đã khởi tạo: " + (studentTable != null)); // Debug
//        loadData("ALL");
    }

    public void loadData(String queryType) {
        System.out.println("Loading data with query type: " + queryType);
        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected"+connectionSet.getConnectionString());
        System.out.println("Database" + connectionSet.getDatabaseName());
        System.out.println("Collection" + connectionSet.getCollectionName());
        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        studentList.clear();

        switch (queryType) {
            case "ALL":
                for (Document doc : studentCollection.find()) {
                    Student student = convertToStudent(doc);
                    studentList.add(student);
                }
                break;

            case "CLASS_ID":
                int classId = getInputInt("Enter Class ID: ");
                for (Document doc : studentCollection.find(Filters.eq("class_id", classId))) {
                    studentList.add(convertToStudent(doc));
                }
                break;

            case "NAME":
                String name = getInputString("Enter Student Name: ");
                for (Document doc : studentCollection.find(Filters.regex("student_name", name, "i"))) {
                    studentList.add(convertToStudent(doc));
                }
                break;

            case "STUDENT_ID":
                String studentId = getInputString("Enter Student ID (24-char ObjectId): ");
                for (Document doc : studentCollection.find(Filters.eq("_id", new ObjectId(studentId)))) {
                    studentList.add(convertToStudent(doc));
                }
                break;

            case "AGE":
                int studentAge = getInputInt("Enter Student Age: ");
                System.out.println("DEBUG: Student Age Input = " + studentAge);

                for (Document doc : studentCollection.find(Filters.eq("student_age", studentAge))) {
                    studentList.add(convertToStudent(doc));
                }
                studentTable.refresh();
                break;
        }
    }

    private Student convertToStudent(Document doc) {
        return new Student(
                doc.getString("student_name"),
                doc.getInteger("student_age"),
                doc.getInteger("class_id"),
                doc.getString("class_name")
        );
    }

    private int getInputInt(String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter filter");
        dialog.setHeaderText(message);
        dialog.setContentText("Nhập số:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                return Integer.parseInt(result.get());
            } catch (NumberFormatException e) {
                showAlert("The value is invalid", "Please input an acceptable value!");
                return -1;
            }
        }
        return -1;
    }

    private String getInputString(String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter filter");
        dialog.setHeaderText(message);
        dialog.setContentText("Nhập dữ liệu:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
