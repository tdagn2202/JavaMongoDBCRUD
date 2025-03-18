module com.example.uicrud {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires logback.classic;
    requires org.slf4j;

    opens com.example.uicrud.Models to javafx.base;
    opens com.example.uicrud to javafx.fxml;
    exports com.example.uicrud;
}