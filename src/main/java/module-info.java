module org.example.employeeperformanceapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens org.example.employeeperformanceapp to javafx.fxml;
    exports org.example.employeeperformanceapp;
}