package edu.ucdenver.catalogapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CatalogController {
    //buttons
    public Button btnExit;
    public Button btnBack;

    //Port\IP
    public TextField txtPort = null;
    public TextField txtIP = null;
    public Button btnConnect;

    //Login
    public Button btnLogin;
    public TextField txtUsername;
    public PasswordField txtPassword;

    public void clientLogin(ActionEvent actionEvent) {
        if (txtUsername.getText() != null && txtPassword.getText() != null) {
            try {
                changeScene("catalogApp");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void changeScene(String fxml) throws IOException {
        CatalogApp.screenController.activate(fxml);
    }


    // General Usage -----------------------------------------------------------------------------------------------------

    public void goBack(ActionEvent actionEvent) throws IOException {
        changeScene("IP-Port");
    }

    public void exitApp(ActionEvent actionEvent) {
        Runtime.getRuntime().exit(0);
    }

    // IP\Port GUI Usage -----------------------------------------------------------------------------------------------

    public void ipToLogin(ActionEvent actionEvent) throws IOException
    {
        if (txtIP.getText() != null && txtPort.getText() != null) {
            CatalogApp.client.setServerIP(txtIP.getText());
            CatalogApp.client.setServerPort(Integer.parseInt(txtPort.getText()));
            CatalogApp.client.connect();

            if(CatalogApp.client.isConnected()){
                changeScene("ClientLogin");
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not connect to server. Check your server IP" +
                        " and Port values.");
                txtPort.clear();
                txtIP.clear();
                alert.show();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill out all fields");
            alert.show();
        }
    }

    // Client Login Usage -------------------------------------------------------------------------------------------------

    public void signIn(ActionEvent actionEvent) throws IOException {

        if(CatalogApp.client.loginUser(txtUsername.getText(), txtPassword.getText()))
            changeScene("CatalogApp");
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Incorrect Username or password");
            txtPort.clear();
            txtIP.clear();
            alert.show();
        }
    }
}
