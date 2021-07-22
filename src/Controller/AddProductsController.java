package Controller;

import Model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductsController implements Initializable {

    public TableView productAddPartTable;
    public TableColumn productAddIdColumn;
    public TableColumn productAddNameColumn;
    public TableColumn productAddInventoryColumn;
    public TableColumn productAddPriceColumn;

    public TableView productRemovePartTable;
    public TableColumn productRemoveIdColumn;
    public TableColumn productRemoveNameColumn;
    public TableColumn productRemoveInventoryColumn;
    public TableColumn productRemovePriceColumn;

    public TextField addProductId;
    public TextField addProductName;
    public TextField addProductInventory;
    public TextField addProductPrice;
    public TextField addProductMax;
    public TextField addProductMin;

    private ObservableList<Part> addPart = FXCollections.observableArrayList();
    private ObservableList<Part> removePart = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productAddPartTable.setItems(addPart);
    }

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

}