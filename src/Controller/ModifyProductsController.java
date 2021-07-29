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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Model.Inventory.*;
import static Controller.AddProductsController.isInteger;

public class ModifyProductsController implements Initializable {

    private int id;

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
    public TextField searchPart;

    private ObservableList<Part> addPart = FXCollections.observableArrayList();
    private ObservableList<Part> removePart = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

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
        associatedParts = selectedProduct.getAllAssociatedParts();

        for (Part part : partList) {
            addPart.add(part);
        }

        for (Part part : associatedParts) {
            removePart.add(part);
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
            removePart.remove((Part) removePartTable.getSelectionModel().getSelectedItem());
        }
    }

    // Error - needed to update the actual product and not create a new one
    public void modifyProduct(ActionEvent actionEvent) throws IOException {

        String errorStr = AddProductsController.checkInputs(productName, productPrice, productMax, productMin, productInventory);

        if (errorStr.isEmpty()) {
            id = selectedProduct.getId();
            selectedProduct.setName(productName.getText());
            selectedProduct.setStock(Integer.parseInt(productInventory.getText()));
            selectedProduct.setPrice(Double.parseDouble(productPrice.getText()));
            selectedProduct.setMin(Integer.parseInt(productMin.getText()));
            selectedProduct.setMax(Integer.parseInt(productMax.getText()));

            // delete any associated parts
            for (Part part : addPart) {
                if (selectedProduct.deleteAssociatedPart(part)) {
                    JOptionPane.showMessageDialog(null, "Successfully removed associated part(s)!");
                };
            }
            // add selected associated parts
            for (Part part : removePart) {
                selectedProduct.addAssociatedPart(part);
            }

            updateProduct(getAllProducts().indexOf(selectedProduct), selectedProduct);

            // after save go back to main screen
            toMain(actionEvent);

        }
        else {
            // show error string to user
            JOptionPane.showMessageDialog(null, errorStr);
        }
    }

    /**
     *
     * @param keyEvent Takes in user typed text to search for part
     */
    public void searchPart(KeyEvent keyEvent) {
        String search = searchPart.getText();
        if (isInteger(search)) {
            Part part = lookupPart(Integer.parseInt(search));
            if (part == null) return;
            else addPartTable.getSelectionModel().select(part);
        }
        else {
            ObservableList<Part> parts = lookupPart(search);
            addPartTable.setItems(parts);
            addPartTable.getSelectionModel().selectFirst();
        }

    }
}