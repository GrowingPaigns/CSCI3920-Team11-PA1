package edu.ucdenver.adminapp;

import edu.ucdenver.client.Client;
import edu.ucdenver.domain.System;
import edu.ucdenver.server.ClientWorker;
import edu.ucdenver.server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;

public class AdminApp extends Application implements Serializable {

    public static ScreenController screenController;
    public static Server server;

    public static void main(String[] args) {
        Application.launch(args);
    }
    public static Client client = new Client();
    public static Controller c;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("IP-Port.fxml"));
        Parent root = loader.load();
        c = loader.getController();
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 720, 544));
        screenController = new ScreenController(primaryStage.getScene());
        screenController.addScreen("UserLogin", FXMLLoader.load(getClass().getResource("AdminLogin.fxml")));
        screenController.addScreen("AdminApp", FXMLLoader.load(getClass().getResource("adminapp.fxml")));
        screenController.addScreen("ip-port", FXMLLoader.load(getClass().getResource("IP-Port.fxml")));
        primaryStage.show();
    }

}
