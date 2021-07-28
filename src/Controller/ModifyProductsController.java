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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Model.Inventory.*;

public class ModifyProductsController implements Initializable {

    private int id, stock, min, max;
    private String name;
    private double price;

    private Product selectedProduct;

    public TableView addPartTable;
    public TableColumn addIdColumn;
    public TableColumn addNameColumn;
    public TableColumn addInventoryColumn;
    public TableColumn addPriceColumn;

    public TableView removePartTable;
    public TableColumn removeIdColumn;
    public TableColumn removeNameColumn;
    public TableColumn removeInventoryColumn;
    public TableColumn removePriceColumn;

    public TextField productId;
    public TextField productName;
    public TextField productInventory;
    public TextField productPrice;
    public TextField productMax;
    public TextField productMin;

    private ObservableList<Part> addPart = FXCollections.observableArrayList();
    private ObservableList<Part> removePart = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // get selected part from main screen controller
        selectedProduct = MainScreenController.getProduct();
        // fill in all the text boxes
        productId.setText(Integer.toString(selectedProduct.getId()));
        productName.setText(selectedProduct.getName());
        productInventory.setText(Integer.toString(selectedProduct.getStock()));
        productPrice.setText(Double.toString(selectedProduct.getPrice()));
        productMax.setText(Integer.toString(selectedProduct.getMax()));
        productMin.setText(Integer.toString(selectedProduct.getMin()));

        addPartTable.setItems(addPart);
        addIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        removePartTable.setItems(removePart);
        removeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        removeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        removeInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        removePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<Part> partList = getAllParts();

        for (Part part : partList) {
            addPart.add(part);
        }
    }

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void addPart(ActionEvent actionEvent) {

        if ((Part) addPartTable.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "You must select/highlight a part!");
        }
        else {
            removePart.add((Part) addPartTable.getSelectionModel().getSelectedItem());
        }

    }

    public void removePart(ActionEvent actionEvent) {
        if ((Part) removePartTable.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "You must select/highlight a part!");
        }
        else {
            removePart.remove((Part) addPartTable.getSelectionModel().getSelectedItem());
        }
    }

    public void modifyProduct(ActionEvent actionEvent) throws IOException {

        String errorStr = AddProductsController.checkInputs(productName, productPrice, productMax, productMin, productInventory);

        if (errorStr.isEmpty()) {
            name = productName.getText();
            stock = Integer.parseInt(productInventory.getText());
            price = Double.parseDouble(productPrice.getText());
            min = Integer.parseInt(productMin.getText());
            max = Integer.parseInt(productMax.getText());

            ObservableList<Part> getAllParts = selectedProduct.getAllAssociatedParts();

            for (Part part : getAllParts) {
                selectedProduct.deleteAssociatedPart(part);
            }

            Product newProduct = new Product(id, name, price, stock, min, max);
            updateProduct(id - 1, newProduct);

            // after save go back to main screen
            toMain(actionEvent);

        }
        else {
            // show error string to user
            JOptionPane.showMessageDialog(null, errorStr);
        }
    }
}