package org.example.employeeperformanceevaluationsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Инициализируем EmployeeApp
        EmployeeApp employeeApp = new EmployeeApp();

        // Запускаем EmployeeApp и передаем primaryStage
        employeeApp.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args); // Запуск JavaFX приложения
    }
}
