package org.example.employeeperformanceevaluationsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Загружаем FXML файл
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));

            // Загружаем корневой элемент из FXML
            Parent root = loader.load();

            // Создаем сцену с корневым элементом
            Scene scene = new Scene(root);

            // Устанавливаем сцену в окно
            primaryStage.setScene(scene);

            // Настроим заголовок окна
            primaryStage.setTitle("Employee Performance Evaluation");

            // Получаем доступ к контроллеру из FXML, если необходимо
            HelloController controller = loader.getController();

            // Теперь мы можем передать контроллеру данные или выполнить дополнительные действия
            // Например, передать управление в EmployeeApp
            EmployeeApp employeeApp = new EmployeeApp();
            employeeApp.start(primaryStage); // Здесь передаем сцену в EmployeeApp, если это нужно

            // Показываем окно
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace(); // Печатает стек ошибок в консоль для отладки
        }
    }

    public static void main(String[] args) {
        launch(args); // Запуск JavaFX приложения
    }
}
