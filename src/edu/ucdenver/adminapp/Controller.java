package edu.ucdenver.adminapp;

import edu.ucdenver.client.Client;
import edu.ucdenver.domain.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

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
    public TextField txtHomeProductLocation;
    public Button btnHomeProductSubmit;
    public TextField txtBookTitle;
    public TextField txtAuthorName;
    public DatePicker dpPublicationDate;
    public TextField txtNumberOfPages;
    public Button btnBookSubmit;
    public TextField txtElectronicSerial;
    public DatePicker dpElectronicWarrantyDate1;
    public DatePicker dpElectronicWarrantyDate2;
    public Button btnElectronicSubmit;
    public TextArea txtComputerSpecs;
    public Button txtComputerSubmit;
    public Button btnCellphoneSubmit;
    public TextField txtCellphoneIMEI;
    public ComboBox<String> comboCellphoneOS;

    //Category Management
    public TextField txtCategoryID;
    public TextField txtCategoryName;
    public TextArea txtCategoryDescription;
    public ComboBox<Category> comboDefaultCategory;
    public Button btnAddCategory;
    public ComboBox<Category> comboParentCategory;
    public Tab tabCategoryManagement;
    public ListView<Category> lstRemoveCategory;
    public Button btnRemoveCategory;

    //Order management
    public ListView<User> lstUsersOrders;
    public ListView<Order> lstOrders;
    public DatePicker dpOrderSearchDate1;
    public DatePicker dpOrderSearchDate2;
    public Button btnSearchOrderByDate;
    public Tab tabOrderManagement;
    public Button btnSaveSystem;


    public Controller()
    {
        //client = new Client();
        this.lstUsers = new ListView<>();
        this.treeProductCategories = new TreeView<>();
        this.treeProductCategories.setShowRoot(true);
        this.comboDefaultCategory = new ComboBox<>();
        this.lstRemoveProducts = new ListView<>();
        this.comboProductType = new ComboBox<>();
        this.comboCellphoneOS = new ComboBox<>();
        this.comboParentCategory = new ComboBox();
        this.lstRemoveCategory = new ListView<>();
        this.lstUsersOrders = new ListView<>();
        this.lstOrders = new ListView<>();
    }

    public void initialize ()
    {
        String[] types = {"Home Product", "Electronic", "Cellphone", "Computer", "Book"};
        this.comboProductType.setItems(FXCollections.observableArrayList(types));
        this.comboCellphoneOS.setItems((FXCollections.observableArrayList("iOS", "Android")));

        this.lstUsersOrders.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                lstOrders.setItems(FXCollections.observableArrayList(newValue.getFinalizedOrders()));
            }
        });
    }


    public void changeScene(String fxml) throws IOException {
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
            TreeItem<Category> root = createTreeviewStructure(AdminApp.client.getCategories());
            treeProductCategories.setRoot(root);

            lstRemoveProducts.setItems(FXCollections.observableArrayList(AdminApp.client.fetchProducts()));
            lstRemoveProducts.refresh();
        }
        else if (this.tabCategoryManagement.isSelected())
        {
            CategoryNode categories = AdminApp.client.getCategories();
            ArrayList<Category> arrayList = categories.toArrayList(categories);
            comboParentCategory.setItems(FXCollections.observableArrayList(arrayList));
            lstRemoveCategory.setItems(FXCollections.observableArrayList(arrayList));
            comboDefaultCategory.setItems(FXCollections.observableArrayList(arrayList));
        }
        else if (this.tabOrderManagement.isSelected())
        {
            observableListUsers.setAll(AdminApp.client.fetchUsers());
            lstUsersOrders.setItems(observableListUsers);
            lstUsersOrders.refresh();
        }
    }

    private TreeItem<Category> createTreeviewStructure(CategoryNode tree) {
        TreeItem<Category> root = new TreeItem<>();
        root.setValue(tree.getData());
        if (!tree.isLeaf())
        {
            for (CategoryNode node: tree.getChildren())
                root.getChildren().add(createTreeviewStructure(node));
            return root;
        }
        else
            return root;
    }

    public void addProduct(ActionEvent actionEvent) {
        if (txtProductID.getText() != null && txtProductName.getText()!= null && txtProductBrandName.getText() != null
        && txtProductDescription.getParagraphs().toString() != null && dpDateAdded.getValue() != null &&
        !comboProductType.getSelectionModel().isEmpty())
        {
            if (comboProductType.getSelectionModel().selectedItemProperty().getValue().equals("Home Product"))
            {
                try {
                    //We do this in order ot have reference to controller that is instantiated when opening new scene.
                    promptAdditionalInformation("homeproduct.fxml");
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
            else if (comboProductType.getSelectionModel().selectedItemProperty().getValue().equals("Book"))
            {
                try {
                    //We do this in order ot have reference to controller that is instantiated when opening new scene.
                    promptAdditionalInformation("book.fxml");
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
            else if (comboProductType.getSelectionModel().selectedItemProperty().getValue().equals("Electronic"))
            {
                try {
                    //We do this in order ot have reference to controller that is instantiated when opening new scene.
                    promptAdditionalInformation("electronic.fxml");
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
            else if (comboProductType.getSelectionModel().selectedItemProperty().getValue().equals("Computer"))
            {
                try {
                    //We do this in order ot have reference to controller that is instantiated when opening new scene.
                    promptAdditionalInformation("computer.fxml");
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
            else if (comboProductType.getSelectionModel().selectedItemProperty().getValue().equals("Cellphone"))
            {
                try {
                    //We do this in order ot have reference to controller that is instantiated when opening new scene.
                    promptAdditionalInformation("cellphone.fxml");
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
        if (AdminApp.client.removeProduct(lstRemoveProducts.getSelectionModel().getSelectedItem()))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Product Removed Successfully");
            alert.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "That product no longer exists in the system");
            alert.show();
        }
    }

    public void addHomeProduct(ActionEvent actionEvent)
    {
        if (txtHomeProductLocation.getText() != null)
        {
            if (AdminApp.client.addHomeProduct(txtProductID.getText(), txtProductName.getText(), txtProductBrandName.getText(),
                    txtProductDescription.getParagraphs().toString(), dpDateAdded.getValue(), new ArrayList<>(treeProductCategories.getSelectionModel().getSelectedItems()),
                    txtHomeProductLocation.getText()))
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Product Added Successfully");
                txtProductName.clear();
                txtProductBrandName.clear();
                txtProductDescription.clear();
                txtProductID.clear();
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "That product ID already exists");
                txtProductName.clear();
                txtProductBrandName.clear();
                txtProductDescription.clear();
                alert.show();
            }

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill out all fields");
            alert.show();
        }
        Stage stage = (Stage) txtHomeProductLocation.getScene().getWindow();
        stage.close();
    }

    public void addBook(ActionEvent actionEvent) {
        if (txtBookTitle.getText() != null && txtAuthorName.getText() != null && dpPublicationDate.isArmed()
            && txtNumberOfPages.getText() != null)
        {
            if (AdminApp.client.addBook(txtProductID.getText(), txtProductName.getText(), txtProductBrandName.getText(),
                    txtProductDescription.getParagraphs().toString(), dpDateAdded.getValue(), new ArrayList<>(treeProductCategories.getSelectionModel().getSelectedItems()),
                    txtBookTitle.getText(), txtAuthorName.getText(), dpPublicationDate.getValue(), Integer.parseInt(txtNumberOfPages.getText())))
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Product Added Successfully");
                txtProductName.clear();
                txtProductBrandName.clear();
                txtProductDescription.clear();
                txtProductID.clear();
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "That product ID already exists");
                txtProductName.clear();
                txtProductBrandName.clear();
                txtProductDescription.clear();
                alert.show();
            }

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill out all fields");
            alert.show();
        }
        Stage stage = (Stage) txtBookTitle.getScene().getWindow();
        stage.close();
    }

    public void addElectronic(ActionEvent actionEvent) {
        if (txtElectronicSerial.getText() != null && dpElectronicWarrantyDate1.getValue() != null && dpElectronicWarrantyDate2.getValue() != null)
        {
            if (AdminApp.client.addElectronic(txtProductID.getText(), txtProductName.getText(), txtProductBrandName.getText(),
                    txtProductDescription.getParagraphs().toString(), dpDateAdded.getValue(),
                    new ArrayList<>(treeProductCategories.getSelectionModel().getSelectedItems()),
                    txtElectronicSerial.getText(),
                    new LocalDate[] {dpElectronicWarrantyDate1.getValue(),
                            dpElectronicWarrantyDate2.getValue()}))
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Product Added Successfully");
                txtProductName.clear();
                txtProductBrandName.clear();
                txtProductDescription.clear();
                txtProductID.clear();
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "That product ID already exists");
                txtProductName.clear();
                txtProductBrandName.clear();
                txtProductDescription.clear();
                alert.show();
            }

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill out all fields");
            alert.show();
        }
        Stage stage = (Stage) txtElectronicSerial.getScene().getWindow();
        stage.close();
    }

    public void addComputer(ActionEvent actionEvent) {
        if (txtElectronicSerial.getText() != null && dpElectronicWarrantyDate1.getValue() != null && dpElectronicWarrantyDate2.getValue() != null
        && !txtComputerSpecs.getParagraphs().isEmpty())
        {
            if (AdminApp.client.addComputer(txtProductID.getText(), txtProductName.getText(), txtProductBrandName.getText(),
                    txtProductDescription.getParagraphs().toString(), dpDateAdded.getValue(),
                    new ArrayList<>(treeProductCategories.getSelectionModel().getSelectedItems()),
                    txtElectronicSerial.getText(),
                    new LocalDate[] {dpElectronicWarrantyDate1.getValue(),
                            dpElectronicWarrantyDate2.getValue()}, txtComputerSpecs.getParagraphs().toString()))
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Product Added Successfully");
                txtProductName.clear();
                txtProductBrandName.clear();
                txtProductDescription.clear();
                txtProductID.clear();
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "That product ID already exists");
                txtProductName.clear();
                txtProductBrandName.clear();
                txtProductDescription.clear();
                alert.show();
            }

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill out all fields");
            alert.show();
        }
        Stage stage = (Stage) txtElectronicSerial.getScene().getWindow();
        stage.close();
    }

    public void addCellphone(ActionEvent actionEvent) {
        if (txtElectronicSerial.getText() != null && dpElectronicWarrantyDate1.getValue() != null && dpElectronicWarrantyDate2.getValue() != null
        && txtCellphoneIMEI.getText() != null && !comboCellphoneOS.getSelectionModel().isEmpty())
        {
            if (AdminApp.client.addCellphone(txtProductID.getText(), txtProductName.getText(), txtProductBrandName.getText(),
                    txtProductDescription.getParagraphs().toString(), dpDateAdded.getValue(),
                    new ArrayList<>(treeProductCategories.getSelectionModel().getSelectedItems()),
                    txtElectronicSerial.getText(),
                    new LocalDate[] {dpElectronicWarrantyDate1.getValue(),
                            dpElectronicWarrantyDate2.getValue()}, txtCellphoneIMEI.getText(), comboCellphoneOS.getSelectionModel().getSelectedItem()))
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Product Added Successfully");
                txtProductName.clear();
                txtProductBrandName.clear();
                txtProductDescription.clear();
                txtProductID.clear();
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "That product ID already exists");
                txtProductName.clear();
                txtProductBrandName.clear();
                txtProductDescription.clear();
                alert.show();
            }

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill out all fields");
            alert.show();
        }
        Stage stage = (Stage) txtElectronicSerial.getScene().getWindow();
        stage.close();
    }

    public void addCategory(ActionEvent actionEvent) {
        if (txtCategoryID.getText() != null && txtCategoryName.getText() != null && txtCategoryDescription.getParagraphs() != null)
        {
            if (AdminApp.client.addCategory(txtCategoryID.getText(), txtCategoryName.getText(), txtCategoryDescription.getParagraphs().toString(),
                    comboParentCategory.getSelectionModel().getSelectedItem()))
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Category Added Successfully");
                txtCategoryID.clear();
                txtCategoryName.clear();
                txtCategoryDescription.clear();
                comboParentCategory.getSelectionModel().select(null);
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "That Category ID already exists");
                txtProductName.clear();
                txtProductBrandName.clear();
                txtProductDescription.clear();
                alert.show();
            }

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill out all fields");
            alert.show();
        }
    }

    public void removeCategory(ActionEvent actionEvent) {
        if (AdminApp.client.removeCategory(lstRemoveCategory.getSelectionModel().getSelectedItem()))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Product Removed Successfully");
            alert.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "That Category no longer exists in the system");
            alert.show();
        }
    }

    public void setDefaultCategory(ActionEvent actionEvent) {
        if (AdminApp.client.setDefaultCategory(comboDefaultCategory.getSelectionModel().getSelectedItem()))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Default Category Set.");
            alert.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "That Category no longer exists in the system");
            alert.show();
        }
    }

    public void searchOrderByDate(ActionEvent actionEvent) {
        if (dpOrderSearchDate1.getValue() != null && dpOrderSearchDate2.getValue() != null){
            lstOrders.setItems(FXCollections.observableArrayList(AdminApp.client.getFinalizedOrderByDate(dpOrderSearchDate1.getValue(),
                    dpOrderSearchDate2.getValue())));
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill out all fields.");
            alert.show();
        }

    }

    public void promptAdditionalInformation(String fxml) throws IOException {
        //We do this in order ot have reference to controller that is instantiated when opening new scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        loader.setController(this);
        Parent newRoot = loader.load();
        Controller c = loader.getController();

        //Send the relevant data to this new controller
        c.txtProductID = this.txtProductID;
        c.txtProductName = this.txtProductName;
        c.txtProductBrandName = this.txtProductBrandName;
        c.txtProductDescription = this.txtProductDescription;
        c.dpDateAdded = this.dpDateAdded;
        c.comboProductType.getSelectionModel().select(this.comboProductType.getSelectionModel().getSelectedItem());
        Stage window = new Stage();
        window.setScene(new Scene(newRoot, 720, 544));
        window.show();
    }

    public void saveToFile(ActionEvent actionEvent){
        AdminApp.client.saveApp();
    }
}
