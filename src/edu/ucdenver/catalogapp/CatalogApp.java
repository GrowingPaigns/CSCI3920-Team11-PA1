package edu.ucdenver.catalogapp;

import edu.ucdenver.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CatalogApp extends Application {

    public static ScreenControllerCatalog screenController;
    public static Client client = new Client();
    public static CatalogController controller;
    public static void main(String[] args){Application.launch(args);}

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IP-Port-Catalog.fxml"));
        Parent root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        stage.setTitle("");
        stage.setScene(new Scene(root));
        screenController = new ScreenControllerCatalog(stage.getScene());
        screenController.addScreen("ClientLogin", FXMLLoader.load(getClass().getResource("ClientLogin.fxml")));
        screenController.addScreen("CatalogApp", FXMLLoader.load(getClass().getResource("catalogApp.fxml")));
        screenController.addScreen("IP-Port", FXMLLoader.load(getClass().getResource("IP-Port-Catalog.fxml")));

        stage.show();
    }
}
