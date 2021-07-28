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

public class AddProductsController implements Initializable {

    private int id, stock, min, max, listLength;
    private String name;
    private double price;

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

        ObservableList<Product> products = getAllProducts();
        listLength = products.size();
        id = listLength + 1;
        productId.setText(String.valueOf(id) + " (AUTO GENERATED)");

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

    public void addNewProduct(ActionEvent actionEvent) throws IOException {
        String errorStr = checkInputs(productName, productPrice, productMax, productMin, productInventory);

        if (errorStr.isEmpty()) {
            name = productName.getText();
            stock = Integer.parseInt(productInventory.getText());
            price = Double.parseDouble(productPrice.getText());
            min = Integer.parseInt(productMin.getText());
            max = Integer.parseInt(productMax.getText());

            Product newProduct = new Product(id, name, price, stock, min, max);
            // add all associated parts
            for (Part part : removePart) {
                newProduct.addAssociatedPart(part);
            }
            // add product to inventory
            addProduct(newProduct);

            // after save go back to main screen
            toMain(actionEvent);

        }
        else {
            // show error string to user
            JOptionPane.showMessageDialog(null, errorStr);
        }

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

    public static boolean isLetters(String text) {
        String trimmed = text.replaceAll("\\s+","");
        char[] textArray = trimmed.toCharArray();

        for (char index : textArray) {
            if(!Character.isLetter(index)) return false;
        }
        return true;
    }

    /**
     *
     * @param text
     * @return
     */
    public static boolean isInteger(String text) {
        int num;

        try {
            num = Integer.parseInt(text);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }

    }

    /**
     *
     * @param text
     * @return
     */
    public static boolean isDecimal(String text) {
        double num;

        try {
            num = Double.parseDouble(text);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }

    }

    public static String checkInputs(TextField name, TextField price, TextField max, TextField min, TextField inventory) {
        StringBuilder errorBuild = new StringBuilder();
        int numError = 0;
        String error;

        // name check
        if (name.getText().isEmpty()) errorBuild.append("Name cannot be empty\n");
        else if (!isLetters(name.getText())) errorBuild.append("Name must be only letters\n");
        // price
        if (price.getText().isEmpty()) errorBuild.append("Price cannot be empty\n");
        else if (!isDecimal(price.getText())) errorBuild.append("Price must be a number\n");

        // max
        if (max.getText().isEmpty()) {
            errorBuild.append("Max cannot be empty\n");
            numError++;
        }
        else if (!isInteger(max.getText())) {
            errorBuild.append("Max must be a number with no decimal\n");
            numError++;
        }
        // min
        if (min.getText().isEmpty()) {
            errorBuild.append("Min cannot be empty\n");
            numError++;
        }
        else if (!isInteger(min.getText())) {
            errorBuild.append("Min must be a number with no decimal\n");
            numError++;
        }
        // inventory check
        if (inventory.getText().isEmpty()) {
            errorBuild.append("Inventory cannot be empty\n");
            numError++;
        }
        else if (!isInteger(inventory.getText())) {
            errorBuild.append("Inventory must be a number with no decimal\n");
            numError++;
        }

        if (numError == 0) {
            if (Integer.parseInt(max.getText()) <= Integer.parseInt(min.getText())) {
                errorBuild.append("Max must be greater than min\n");
            }
            else if (Integer.parseInt(inventory.getText()) < Integer.parseInt(min.getText())) {
                errorBuild.append("Inventory must be greater than or equal to min\n");
            }
            else if (Integer.parseInt(inventory.getText()) > Integer.parseInt(max.getText())) {
                errorBuild.append("Inventory must be less than or equal to max\n");
            }
        }

        error = errorBuild.toString();
        return error;

    }

}