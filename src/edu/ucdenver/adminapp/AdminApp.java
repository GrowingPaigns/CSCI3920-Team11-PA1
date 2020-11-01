package edu.ucdenver.adminapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminApp extends Application {

    Stage window = null;


    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("IP-Port.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 720, 544));
        primaryStage.show();
        window = primaryStage;
    }
}
