package org.example.employeeperformanceevaluationsystem;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Инициализируем EmployeeApp
        EmployeeApp employeeApp = new EmployeeApp();
        employeeApp.start(primaryStage); // Запускаем EmployeeApp
    }

    public static void main(String[] args) {
        launch(args); // Запуск JavaFX приложения
    }
}