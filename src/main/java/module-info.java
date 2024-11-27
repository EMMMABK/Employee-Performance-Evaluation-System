module org.example.employeeperformanceevaluationsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.employeeperformanceevaluationsystem to javafx.fxml;
    exports org.example.employeeperformanceevaluationsystem;
}