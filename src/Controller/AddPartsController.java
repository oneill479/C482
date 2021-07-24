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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Model.Inventory.addPart;
import static Model.Inventory.getAllParts;

public class AddPartsController implements Initializable {

    private int id, stock, min, max, machineId, listLength;
    private String name, company;
    private double price;

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

    public void setInHouse(ActionEvent actionEvent) {
        sourceText.setText("Machine ID");
    }

    public void setOutsourced(ActionEvent actionEvent) {
        sourceText.setText("Company");
    }

    public boolean isLetters(String text) {
        char[] textArray = text.toCharArray();

        for (char index : textArray) {
            if(!Character.isLetter(index)) return false;
        }
        return true;
    }

    public boolean isNumber(String text) {
        char[] textArray = text.toCharArray();

        for (char index : textArray) {
            if(!Character.isDigit(index)) return false;
        }
        return true;
    }

    public String checkInputs () {
        String error  = "";

        // name check
        if (partName.getText().isEmpty()) error.concat("Name cannot be empty\n");
        if (!isLetters(partName.getText())) error.concat("Name must be only letters\n");
        // inventory check
        if (partInventory.getText().isEmpty()) error.concat("Name cannot be empty\n");
        else if (isNumber(partInventory.getText())) {

        }
        else error.concat("Inventory must be a number\n");
        //if (partInventory.getText() < partMin.getText()) error.concat("Inventory cannot be less than Min");
        //if (partInventory.getText() < partMax.getText()) error.concat("Inventory cannot be greater than Max");

    }


    /*Min should be less than Max; and Inv should be between those two values.
    The user should not delete a product that has a part associated with it.
    The application confirms the “Delete” and “Remove” actions.
    The application will not crash when inappropriate user data is entered in the forms; instead, error messages should be generated.*/


}