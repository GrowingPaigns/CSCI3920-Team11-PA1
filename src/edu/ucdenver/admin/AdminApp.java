package edu.ucdenver.admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminApp extends Application {

    Stage stage = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("adminLogin.fxml"));
        primaryStage.setTitle("Admin Client");
        primaryStage.setScene(new Scene(root, 720, 480));
        primaryStage.show();
        stage = primaryStage;
    }
}
