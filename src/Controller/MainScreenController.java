package Controller;

import Model.Part;
import Model.Product;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    public TableView partTable;
    public TableColumn partIdColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryColumn;
    public TableColumn partPriceColumn;

    public TableView productTable;
    public TableColumn productIdColumn;
    public TableColumn productNameColumn;
    public TableColumn productInventoryColumn;
    public TableColumn productPriceColumn;

    public TextField partSearch;
    public TextField productSearch;

    private ObservableList<Part> mainParts = FXCollections.observableArrayList();
    private ObservableList<Product> mainProducts = FXCollections.observableArrayList();

    private static Part selectedPart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTable.setItems(mainParts);
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(mainProducts);
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<Part> partList = getAllParts();
        ObservableList<Product> productList = getAllProducts();

        for (Part part : partList) {
            mainParts.add(part);
        }

        for (Product product : productList) {
            mainProducts.add(product);
        }

    }

    public void toAddParts(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddParts.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Parts");
        stage.setScene(scene);
        stage.show();
    }

    public static Part getPart() {
        return selectedPart;
    }

    public void toModifyParts(ActionEvent actionEvent) throws IOException {
        selectedPart = (Part) partTable.getSelectionModel().getSelectedItem();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyParts.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setUserData(selectedPart);
            stage.setTitle("Modify Parts");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {

        }

    }

    // Error resolving onAction='#addAssociatedPart', either the event handler is not in the Namespace or there is an error in the script.
    public void toAddProducts(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddProducts.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        System.out.println(stage);
        Scene scene = new Scene(root);
        stage.setTitle("Add Products");
        stage.setScene(scene);
        stage.show();
    }

    public void toModifyProducts(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyProducts.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Modify Products");
        stage.setScene(scene);
        stage.show();
    }

    public void removePart(ActionEvent actionEvent) {
        Part selectedPart = (Part) partTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedPart);
        if(selectedPart == null) return;
        boolean result = Model.Inventory.deletePart(selectedPart);
        if (result) mainParts.remove(selectedPart);
        else JOptionPane.showMessageDialog(null, "Could not delete Part!");
    }

    public void removeProduct(ActionEvent actionEvent) {
        Product selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) return;
        boolean result = Model.Inventory.deleteProduct(selectedProduct);
        if (result) mainProducts.remove(selectedProduct);
        else JOptionPane.showMessageDialog(null, "Could not delete Product!");
    }

    public void exit() {
        System.exit(0);
    }

}