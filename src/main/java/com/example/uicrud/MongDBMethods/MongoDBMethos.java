package com.example.uicrud.MongDBMethods;


import com.example.uicrud.DBConnection.Connection;
import com.example.uicrud.Models.ConnectionSet;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

public class MongoDBMethos {
    static ConnectionSet connectionSet = ConnectionSet.getInstance();

    //C - CREATE
    public static void insertOne(Document document) {

        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected");

        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        System.out.println("Using connection: " + connectionSet.getConnectionString());
        System.out.println("Using Database: " + connectionSet.getDatabaseName());
        System.out.println("Using Collection: " + connectionSet.getCollectionName());


        studentCollection.insertOne(document);
        System.out.println("Successfully inserted!");
    }

    public static void insertMany(List<Document> documents) {
        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected");

        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        studentCollection.insertMany(documents);
        System.out.println("Successfully inserted!");

    }


    //R - READ
    public static void listAllStudent() {
        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected");

        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        FindIterable<Document> students = studentCollection.find();
        for (Document student : students) {
            System.out.println(student.toJson());
        }
    }

    public static void findStudentByName(String studentName){
        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected");

        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        Bson filter = Filters.eq("name", studentName);

        FindIterable<Document> students = studentCollection.find(filter);
        for (Document student : students) {
            System.out.println(student.toJson());
        }
    }

    public static void findStudentByClass(String studentClassName){
        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected");

        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        Bson filter = Filters.eq("class_name", studentClassName);

        FindIterable<Document> students = studentCollection.find(filter);
        for (Document student : students) {
            System.out.println(student.toJson());
        }
    }

    public static void findStudentByAge(Integer studentAge){
        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected");

        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        Bson filter = Filters.eq("age", studentAge);

        FindIterable<Document> students = studentCollection.find(filter);
        for (Document student : students) {
            System.out.println(student.toJson());
        }
    }

    // U - UPDATE

    public static void updateStudentName(String studentId, String newName) {
        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected");

        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        Bson filter = Filters.eq("_id", new ObjectId(studentId));
        Bson updateOperation = new Document("$set", new Document("student_name", newName));

        studentCollection.updateOne(filter, updateOperation);
        System.out.println("Student name updated successfully!");
    }


    public static void updateStudentClassById(String studentId, String newClassName) {
        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected");

        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        Bson filter = Filters.eq("_id", new ObjectId(studentId));
        Bson updateOperation = new Document("$set", new Document("class_name", newClassName));

        studentCollection.updateOne(filter, updateOperation);
        System.out.println("Student class updated successfully!");
    }

    public static void updateStudentAgeById(String studentId, int newAge) {
        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected");

        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        Bson filter = Filters.eq("_id", new ObjectId(studentId));
        Bson updateOperation = new Document("$set", new Document("student_age", newAge));

        studentCollection.updateOne(filter, updateOperation);
        System.out.println("Student age updated successfully!");
    }

    public static void updateStudentById(ObjectId studentId, String studentName, Integer studentAge, Integer classId, String className) {
        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected");

        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        Bson filter = Filters.eq("_id", studentId);

        Document updateFields = new Document();
        if (studentName != null && !studentName.isEmpty()) {
            updateFields.append("student_name", studentName);
        }
        if (studentAge != null) {
            updateFields.append("student_age", studentAge);
        }
        if (classId != null) {
            updateFields.append("class_id", classId);
        }
        if (className != null && !className.isEmpty()) {
            updateFields.append("class_name", className);
        }

        if (!updateFields.isEmpty()) {
            Bson updateOperation = new Document("$set", updateFields);
            studentCollection.updateOne(filter, updateOperation);
            System.out.println("Student information updated successfully!");
        } else {
            System.out.println("No valid fields to update.");
        }
    }



    // D - DELETE

    public static void deleteStudentById(String studentId) {
        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected");

        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        Bson filter = Filters.eq("_id", new ObjectId(studentId));

        studentCollection.deleteOne(filter);
        System.out.println("Student deleted successfully!");
    }

    public static void deleteStudentByName(String studentName) {
        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected");

        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        Bson filter = Filters.eq("name", studentName);

        studentCollection.deleteOne(filter);
        System.out.println("Student deleted successfully!");
    }


    public static void deleteStudentsByClass(String className) {
        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected");

        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        Bson filter = Filters.eq("class_name", className);

        studentCollection.deleteMany(filter);
        System.out.println("All students from class " + className + " deleted successfully!");
    }

    public static void deleteAllStudents() {
        MongoClient mongoClient = Connection.getMongoClient();
        System.out.println("MongoDB Connected");

        MongoDatabase sampleCRUD = mongoClient.getDatabase(connectionSet.getDatabaseName());
        MongoCollection<Document> studentCollection = sampleCRUD.getCollection(connectionSet.getCollectionName());

        studentCollection.deleteMany(new Document()); // Empty filter deletes all documents
        System.out.println("All students deleted successfully!");
    }
}
