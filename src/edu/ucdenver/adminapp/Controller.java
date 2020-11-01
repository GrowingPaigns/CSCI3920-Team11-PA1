package edu.ucdenver.adminapp;

import edu.ucdenver.client.Client;
import edu.ucdenver.domain.Category;
import edu.ucdenver.domain.HomeProduct;
import edu.ucdenver.domain.Product;
import edu.ucdenver.domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.io.IOException;
import java.time.LocalDate;

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
    public CheckBox checkBoxAdmin;

    public ListView<User> lstUsers;
    public Tab tabCreateUser;

    //Product Management
    public DatePicker dpDateAdded;
    public TextField txtProductID;
    public TextField txtProductName;
    public TextField txtProductBrandName;
    public TextArea txtProductDescription;
    public ComboBox<String> comboProductType;
    public TreeView<Category> treeProductCategories;
    public Tab tabProductManagement;
    public Button btnAddProduct;
    public Button btnRemoveProduct;
    public ListView<Product> lstRemoveProducts;

    //Category Management
    public TextField txtCategoryID;
    public TextField txtCategoryName;
    public TextArea txtCategoryDescription;
    public TextField txtRemoveCategoryID;
    public ComboBox<String> comboDefaultCategory;
    public TextField txtHomeProductLocation;
    public Button btnHomeProductSubmit;

    public Controller()
    {
        //client = new Client();
        this.lstUsers = new ListView<>();
        this.treeProductCategories = new TreeView<>();
        this.comboDefaultCategory = new ComboBox<>();
        this.lstRemoveProducts = new ListView<>();
    }

    public void initialize ()
    {

    }


    public void changeScene(String fxml) throws IOException {
//        Parent secondaryScene = FXMLLoader.load(getClass().getResource(fxml));
//        Parent primaryScene = FXMLLoader.load(getClass().getResource("IP-Port.fxml"));
//        Scene scene = new Scene(primaryScene);
//        Scene nextScene = new Scene(secondaryScene);
//
//        Stage window = new Stage();
//        window.setScene(scene);
//        window.close();
//        window.setScene(nextScene);
//        window.show();
        AdminApp.screenController.activate(fxml);
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
        if (txtIP.getText() != null && txtPort.getText() != null) {
            //client = new Client(txtIP.getText(), Integer.parseInt(txtPort.getText()));
            AdminApp.client.setServerIP(txtIP.getText());
            AdminApp.client.setServerPort(Integer.parseInt(txtPort.getText()));
            AdminApp.client.connect();

            if(AdminApp.client.isConnected()){
                changeScene("UserLogin");
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

    // Admin Login Usage -------------------------------------------------------------------------------------------------

    public void signIn(ActionEvent actionEvent) throws IOException {

        if(AdminApp.client.loginUser(txtUserName.getText(), txtPassword.getText()))
            changeScene("AdminApp");
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Incorrect Username or password");
            txtPort.clear();
            txtIP.clear();
            alert.show();
        }

    }

    public void createNewUser(ActionEvent actionEvent) {

        if (AdminApp.client == null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Client null");
            alert.show();
        }

        if (txtNewEmail.getText() != null && txtNewUser.getText() != null && txtNewPass.getText()!= null) {
            if (AdminApp.client.createNewUser(txtNewUser.getText(), txtNewEmail.getText(), txtNewPass.getText(), checkBoxAdmin.isSelected()))
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User Added Successfully");
                txtNewEmail.clear();
                txtNewUser.clear();
                txtNewPass.clear();
                alert.show();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "That username or email already exists");
                txtNewEmail.clear();
                txtNewUser.clear();
                txtNewPass.clear();
                alert.show();
            }

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill out all fields");
            alert.show();
        }
    }

    

    //create user GUI
    public void newUserScreen(ActionEvent actionEvent) throws Exception{
        changeScene("AdminApp");
    }

    //Sends back to AdminLogin
    public void backToLogin(ActionEvent actionEvent) throws Exception{
        changeScene("ip-port");
    }

    public void loadViews(Event event) {
        ObservableList<User> observableListUsers = FXCollections.observableArrayList();

        if (this.tabCreateUser.isSelected()) {
            observableListUsers.setAll(AdminApp.client.fetchUsers());
            lstUsers.setItems(observableListUsers);
            lstUsers.refresh();
        }
        else if (this.tabProductManagement.isSelected())
        {
            TreeItem<Category> root = AdminApp.client.getCategories().getRoot();
            treeProductCategories = new TreeView<>(root);

            //lstRemoveProducts.setItems(FXCollections.observableArrayList(AdminApp.client.fetchProducts()));
        }
    }

    public void addProduct(ActionEvent actionEvent) {
        if (txtProductID.getText() != null && txtProductName.getText()!= null && txtProductBrandName.getText() != null
        && txtProductDescription.getParagraphs().toString() != null && dpDateAdded.getValue() != null)
        {
            if (comboProductType.getSelectionModel().selectedItemProperty().equals("Home Product"))
            {
                try {
                    Parent newRoot = FXMLLoader.load(getClass().getResource("homeproduct.fxml"));
                    Stage window = new Stage();
                    window.setScene(new Scene(newRoot, 720, 544));
                    window.show();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
            else if (comboProductType.getSelectionModel().isEmpty())
            {
                if (AdminApp.client.addProduct (txtProductID.getText(), txtProductName.getText(), txtProductBrandName.getText(),
                        txtProductDescription.getParagraphs().toString(), dpDateAdded.getValue()))
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Product Added Successfully");
                    txtNewEmail.clear();
                    txtNewUser.clear();
                    txtNewPass.clear();
                    alert.show();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to add product");
                    alert.show();
                }
            }
            else if (comboProductType.getSelectionModel().selectedItemProperty().equals("Book"))
            {
                try {
                    Parent newRoot = FXMLLoader.load(getClass().getResource("book.fxml"));
                    Stage window = new Stage();
                    window.setScene(new Scene(newRoot, 720, 544));
                    window.show();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill out all fields");
            alert.show();
        }
    }

    public void removeProduct(ActionEvent actionEvent) {
        //AdminApp.client.removeProduct(lstRemoveProducts.getSelectionModel().getSelectedItems());
    }

    public void addHomeProduct(ActionEvent actionEvent)
    {
//        if (txtHomeProductLocation.getText() != null)
//        {
//            HomeProduct product = new HomeProduct(txtProductID.getText(), txtProductName.getText(), txtProductBrandName.getText(),
//            txtProductDescription.getParagraphs().toString(), dpDateAdded.getValue(), txtHomeProductLocation.getText());
//            if (AdminApp.client.addHomeProduct(product));
//        }
//        else
//        {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill out all fields");
//            alert.show();
//        }
    }

    public void addBookProduct(ActionEvent actionEvent)
    {
//        if (txtHomeProductLocation.getText() != null)
//        {
//            Book product = new Book(txtProductID.getText(), txtProductName.getText(), txtProductBrandName.getText(),
//                    txtProductDescription.getParagraphs().toString(), dpDateAdded.getValue(), );
//            if (AdminApp.client.addHomeProduct(product));
//        }
//        else
//        {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill out all fields");
//            alert.show();
//        }
    }
}
