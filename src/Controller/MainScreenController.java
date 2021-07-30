package Controller;

/**
 * Class MainScreenController.java
 */

/**
 *
 * @author Caleb O'Neill
 */

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Controller.AddPartsController.isInteger;
import static Model.Inventory.*;

/**
 * This class is the main screen controller
 */
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
    private static Product selectedProduct;

    /**
     * This initializes the main screen controller
     */
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

    /**
     * This method takes the user to the add parts screen
     * @param actionEvent Add button clicked on parts section
     * @throws IOException Checks to see if add parts screen will load correctly
     */
    public void toAddParts(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddParts.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Parts");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This returns the part to another controller
     * @return Returns the selected part on parts table
     */
    public static Part getPart() {
        return selectedPart;
    }

    /**
     * This method takes the user to the modify parts screen
     * @param actionEvent Modify button clicked on parts section
     * @throws IOException Checks to see if user has selected a part to be modified
     */
    public void toModifyParts(ActionEvent actionEvent) throws IOException {
        selectedPart = (Part) partTable.getSelectionModel().getSelectedItem();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyParts.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Parts");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modify Parts");
            alert.setContentText("You must select a part!");
            alert.showAndWait();
        }

    }

    /**
     *
     * @param actionEvent Takes user to the add products screen
     * @throws IOException Checks to see if add products screen loads correctly
     */
    public void toAddProducts(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddProducts.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        System.out.println(stage);
        Scene scene = new Scene(root);
        stage.setTitle("Add Products");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @return Returns the product selected in products table
     */
    public static Product getProduct() {
        return selectedProduct;
    }

    /**
     *
     * @param actionEvent Takes user to the modify product screen
     * @throws IOException Checks to see if user has selected a product to be modified
     */
    public void toModifyProducts(ActionEvent actionEvent) throws IOException {
        selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyProducts.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Products");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modify Products");
            alert.setContentText("You must select a product!");
            alert.showAndWait();
        }
    }

    /**
     * This method removes a part
     * @param actionEvent Delete button clicked on parts section
     */
    public void removePart(ActionEvent actionEvent) {
        Part selectedPart = (Part) partTable.getSelectionModel().getSelectedItem();
        selectedPart = (Part) partTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete Parts");
            alert.setContentText("You must select a part!");
            alert.showAndWait();
            return;
        };

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
                Model.Inventory.deletePart(selectedPart);
                mainParts.remove(selectedPart);
                partSearch.setText("");
                partTable.setItems(getAllParts());
        }

    }

    /**
     * This method removes a product
     * @param actionEvent Delete button clicked on products section
     */
    public void removeProduct(ActionEvent actionEvent) {
        Product selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();
        selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete Products");
            alert.setContentText("You must select a product!");
            alert.showAndWait();
            return;
        };

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (selectedProduct.getAllAssociatedParts().size() > 0) {
                JOptionPane.showMessageDialog(null, "Can not delete a product with associated parts!");
            }
            else {
                Model.Inventory.deleteProduct(selectedProduct);
                mainProducts.remove(selectedProduct);
                productSearch.setText("");
                productTable.setItems(getAllProducts());
            }
        }
    }

    /**
     * This method searches for a part
     * @param keyEvent Takes in user typed text to search for part
     */
    public void partSearch(KeyEvent keyEvent) {
        String search = partSearch.getText();
        if (isInteger(search)) {
            Part part = lookupPart(Integer.parseInt(search));
            if (part == null) return;
            else partTable.getSelectionModel().select(part);
        }
        else if (search.isEmpty()) {
            partTable.setItems(getAllParts());
            partTable.getSelectionModel().clearSelection();
        }
        else {
            ObservableList<Part> parts = lookupPart(search);
            partTable.setItems(parts);
            partTable.getSelectionModel().selectFirst();
        }

    }

    /**
     * This method searches for a product
     * @param keyEvent Takes in user typed text to search for product
     */
    public void productSearch(KeyEvent keyEvent) {
        String search = productSearch.getText();
        if (isInteger(search)) {
            Product product = lookupProduct(Integer.parseInt(search));
            if (product == null) return;
            else productTable.getSelectionModel().select(product);
        }
        else if (search.isEmpty()) {
            productTable.setItems(getAllProducts());
            productTable.getSelectionModel().clearSelection();
        }
        else {
            ObservableList<Product> products = lookupProduct(search);
            productTable.setItems(products);
            productTable.getSelectionModel().selectFirst();
        }

    }

    /**
     * Exits program
     */
    public void exit() {
        System.exit(0);
    }

}