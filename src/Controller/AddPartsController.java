package Controller;

import Model.Part;
import Model.InHouse;
import Model.Outsourced;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Model.Inventory.addPart;
import static Model.Inventory.getAllParts;

public class AddPartsController implements Initializable {

    private int id, stock, min, max, machineId, listLength;
    private String name, company;
    private double price;
    boolean internal, external;

    public Label sourceText;
    public TextField partId;
    public TextField partMultiChoice;
    public TextField partName;
    public TextField partInventory;
    public TextField partPrice;
    public TextField partMax;
    public TextField partMin;
    public RadioButton inHouse;
    public RadioButton outsourced;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        ObservableList<Part> parts = getAllParts();
        listLength = parts.size();
        id = listLength + 1;
        partId.setText(String.valueOf(id) + " (AUTO GENERATED)");
        internal = true;
        external = false;
    }

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void addNewPart(ActionEvent actionEvent) {
        String errorStr = checkInputs();

        if (errorStr == "") {
            name = partName.getText();
            stock = Integer.parseInt(partInventory.getText());
            price = Double.parseDouble(partPrice.getText());
            min = Integer.parseInt(partMin.getText());
            max = Integer.parseInt(partMax.getText());

            if (inHouse.isSelected()) {
                machineId = Integer.parseInt(partMultiChoice.getText());
                Part newPart = new InHouse(id, name, price, stock, min, max, machineId);
                addPart(newPart);
            }
            if (outsourced.isSelected()) {
                company = partMultiChoice.getText();
                Part newPart = new Outsourced(id, name, price, stock, min, max, company);
                addPart(newPart);
            }

        }
        else {
            JOptionPane.showMessageDialog(null, errorStr);
        }

    }

    public void setInHouse(ActionEvent actionEvent) {
        internal = true;
        external = false;
        sourceText.setText("Machine ID");
    }

    public void setOutsourced(ActionEvent actionEvent) {
        external = true;
        internal = false;
        sourceText.setText("Company");
    }

    public boolean isLetters(String text) {
        String trimmed = text.trim();
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
    public boolean isInteger(String text) {
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
    public boolean isDecimal(String text) {
        double num;

        try {
            num = Double.parseDouble(text);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }

    }


    public String checkInputs () {
        StringBuilder errorBuild = new StringBuilder();
        int numError = 0;
        String error;

        // name check
        if (partName.getText().isEmpty()) {
            errorBuild.append("Name cannot be empty\n");
        }
        else if (!isLetters(partName.getText())) errorBuild.append("Name must be only letters\n");
        // price
        if (partPrice.getText().isEmpty()) errorBuild.append("Price cannot be empty\n");
        else if (!isDecimal(partPrice.getText())) errorBuild.append("Price must be a number\n");

        // max
        if (partMax.getText().isEmpty()) {
            errorBuild.append("Price cannot be empty\n");
            numError++;
        }
        else if (!isInteger(partMax.getText())) {
            errorBuild.append("Max must be a number with no decimal\n");
            numError++;
        }
        // min
        if (partMin.getText().isEmpty()) {
            errorBuild.append("Price cannot be empty\n");
            numError++;
        }
        else if (!isInteger(partMax.getText())) {
            errorBuild.append("Min must be a number with no decimal\n");
            numError++;
        }
        // inventory check
        if (partInventory.getText().isEmpty()) {
            errorBuild.append("Inventory cannot be empty\n");
            numError++;
        }
        else if (!isInteger(partInventory.getText())) {
            errorBuild.append("Inventory must be a number with no decimal\n");
            numError++;
        }

        if (numError == 0) {
            if (Integer.parseInt(partMax.getText()) <= Integer.parseInt(partMin.getText())) {
                errorBuild.append("Max must be greater than min\n");
            }
            else if (Integer.parseInt(partInventory.getText()) < Integer.parseInt(partMin.getText())) {
                errorBuild.append("Inventory must be greater than or equal to min\n");
            }
            else if (Integer.parseInt(partInventory.getText()) > Integer.parseInt(partMax.getText())) {
                errorBuild.append("Inventory must be less than or equal to max\n");
            }
        }

        // machine id
        if (internal) {
            if (partMultiChoice.getText().isEmpty()) errorBuild.append("Machine ID cannot be empty\n");
            else if (!isInteger(partMax.getText())) errorBuild.append("Machine ID must be a number with no decimal\n");
        }
        // company name
        if (external) {
            if (partMultiChoice.getText().isEmpty()) errorBuild.append("Company Name cannot be empty\n");
            else if (!isLetters(partName.getText())) errorBuild.append("Company Name must be only letters\n");
        }

        error = errorBuild.toString();
        return error;

    }


    /*Min should be less than Max; and Inv should be between those two values.
    The user should not delete a product that has a part associated with it.
    The application confirms the “Delete” and “Remove” actions.
    The application will not crash when inappropriate user data is entered in the forms; instead, error messages should be generated.*/


}