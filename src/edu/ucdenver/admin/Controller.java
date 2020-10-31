package edu.ucdenver.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

public class Controller
{
    public TextField txtUsername;
    public TextField txtPassword;
    public Button btnLogin;

    public void AdminLogin(ActionEvent actionEvent) {
        if (txtUsername.getText() != null && txtPassword.getText() != null) {
            try {
                changeScene("adminApp.fxml");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void changeScene(String fxml) throws Exception{
        Parent pane = FXMLLoader.load(
                getClass().getResource(fxml));

        //You can get a reference to the scene from any object currently in it I guess. I could've
        //made this call on btnLogin instead, but right now this seems like the simplest implementation of
        //this and I don't think we'll need anything more robust.
        txtUsername.getScene().setRoot(pane);
    }
}
