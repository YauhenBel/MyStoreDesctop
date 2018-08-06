package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Старт программы, запуск формы авторизации

public class Main extends Application {
    private static String FXMLSample = "../layouts/sample.fxml";
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(FXMLSample));
        primaryStage.setTitle("Главная");
        Scene scene = new Scene(root, 250, 156);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxWidth(250);
        primaryStage.setMaxHeight(156);

    }

    public static void main(String[] args) { launch(args); }
}
