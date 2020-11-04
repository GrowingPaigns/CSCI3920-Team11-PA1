package edu.ucdenver.catalogapp;

import edu.ucdenver.adminapp.AdminApp;
import edu.ucdenver.domain.Category;
import edu.ucdenver.domain.CategoryNode;
import edu.ucdenver.domain.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javax.swing.text.AbstractDocument;
import java.io.IOException;
import java.util.ArrayList;

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

    //Search Tab
    public Tab tabSearch;
    public Button btnSearch;
    public TextField searchText;
    public ListView<Product> lstProducts;
    public ListView<String> lstProductDetails;

    //Browse Tab
    public Tab tabBrowse;
    public TreeView<Category> productCategories;
    public ListView<Product> browseLstProducts;

    //Shopping Tab
    public Tab tabShop;


    public CatalogController(){
        this.lstProducts = new ListView<>();
        this.productCategories = new TreeView<>();
        this.productCategories.setShowRoot(true);
    }

    public void initialize(){

        this.productCategories.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Category>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Category>> observable, TreeItem<Category> oldValue, TreeItem<Category> newValue) {
                browseLstProducts.setItems(FXCollections.observableArrayList(CatalogApp.client.fetchProductsByCategory(newValue.getValue())));
            }
        });
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

    public void loadViews(Event event) {
        if(this.tabSearch.isSelected()){

        }else if (this.tabBrowse.isSelected()){
            TreeItem<Category> root = createTreeViewStructure(CatalogApp.client.getCategories());
            this.productCategories.setRoot(root);
            this.productCategories.refresh();
        }else if (this.tabShop.isSelected()){

        }
    }

    private TreeItem<Category> createTreeViewStructure(CategoryNode categories) {
        TreeItem<Category> root = new TreeItem<>();
        root.setValue(categories.getData());
        if(!categories.isLeaf()){
            for(CategoryNode node: categories.getChildren())
                root.getChildren().add(createTreeViewStructure(node));
            return root;
        }else
            return root;
    }

    public void searchCatalog(ActionEvent actionEvent) {
        ArrayList<Product> allProducts = CatalogApp.client.fetchProducts();
        ObservableList<Product> prodList = FXCollections.observableArrayList();
        if(!this.searchText.getText().isEmpty()){
            String key = this.searchText.getText();
            for(Product prod: allProducts){
                if(key.equals(prod.getId()) || key.equals(prod.getName()) || key.equals(prod.getBrandName()) || key.equals(prod.getDescription())){
                    prodList.add(prod);
                }
            }
        }
        this.searchText.clear();
        lstProducts.setItems(prodList);
    }
}
