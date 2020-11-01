package edu.ucdenver.adminapp;

import edu.ucdenver.client.Client;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Controller {

    //Buttons
    public Button btnExit;
    public Button btnBack;
    public Button btnSignIn;
    public Button btnCreateUser;

    //Port\IP
    public TextField txtPort = null;
    public TextField txtIP = null;
    public Button btnConnect;

    //Login
    public TextField txtUserName;
    public TextField txtPassword;
    public TextField txtNewEmail;

    //New User
    public TextField txtNewUser;
    public TextField txtNewPass;
    public Button btnNewCreateUser;

    public ListView lstUsers;

    private Client client;

    public Controller()
    {
    }

    public void changeScene(String fxml) throws IOException {
        Parent secondaryScene = FXMLLoader.load(getClass().getResource(fxml));
        Parent primaryScene = FXMLLoader.load(getClass().getResource("IP-Port.fxml"));
        Scene scene = new Scene(primaryScene);
        Scene nextScene = new Scene(secondaryScene);

        Stage window = new Stage();
        window.setScene(scene);
        window.close();
        window.setScene(nextScene);
        window.show();
    }


    // General Usage -----------------------------------------------------------------------------------------------------

    public void goBack(ActionEvent actionEvent) throws IOException {
        changeScene("IP-Port.fxml");
    }

    public void exitApp(ActionEvent actionEvent) {
        Runtime.getRuntime().exit(0);
    }

    // IP\Port GUI Usage -----------------------------------------------------------------------------------------------

    public void ipToLogin(ActionEvent actionEvent) throws IOException
    {
        client = new Client (txtIP.toString(), Integer.parseInt(txtPort.getText()));
        client.connect();

        if(client.isConnected()){
            changeScene("AdminLogin.fxml");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not connect to server. Check your server IP" +
                    " and Port values.");
            txtPort.clear();
            txtIP.clear();
            alert.show();
        }
    }

    // Admin Login Usage -------------------------------------------------------------------------------------------------

    public void signIn(ActionEvent actionEvent) throws IOException {
        if(!txtUserName.getText().equals(nullString) && !txtPassword.getText().equals(nullString)){
            changeScene(".fxml");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Text Box Values Null");
            txtPort.clear();
            txtIP.clear();
            alert.show();
        }
        if(client.loginUser(txtUserName.getText(), txtPassword.getText()))

    }

    public void createNewUser(ActionEvent actionEvent) {
        if (!txtNewEmail.equals(nullString) && !txtNewUser.equals(nullString) && !txtNewPass.equals(nullString)){
            lstUsers.setItems(FXCollections.observableArrayList( txtNewEmail.getText() + ",  " + txtNewUser.getText() + ",  " + txtNewPass.getText() + "\n"));
        }
    }

    //create user GUI
    public void newUserScreen(ActionEvent actionEvent) throws Exception{
        changeScene("CreateUser.fxml");
    }

    //Sends back to AdminLogin
    public void backToLogin(ActionEvent actionEvent) throws Exception{
        changeScene("AdminLogin.fxml");
    }
}
