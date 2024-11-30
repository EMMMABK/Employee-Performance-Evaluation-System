package org.example.employeeperformanceevaluationsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Загружаем FXML файл
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));

        // Загружаем корневой элемент из FXML
        Parent root = loader.load();

        // Создаем сцену с корневым элементом
        Scene scene = new Scene(root);

        // Устанавливаем сцену в окно
        primaryStage.setScene(scene);

        // Настроим заголовок окна и показываем его
        primaryStage.setTitle("Employee Performance Evaluation");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Запуск приложения
    }
}
