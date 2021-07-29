package Controller;

/**
 * Class AddPartsController.java
 */

/**
 *
 * @author Caleb O'Neill
 */

import Model.Part;
import Model.InHouse;
import Model.Outsourced;
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
import java.util.Random;
import java.util.ResourceBundle;

import static Model.Inventory.addPart;
import static Model.Inventory.getAllParts;

/**
 * This class adds a part to the inventory
 */
public class AddPartsController implements Initializable {

    private int id, stock, min, max, machineId;
    private String name, company;
    private double price;
    private boolean internal, external;

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

    /**
     * This initializes the add parts controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        id = getRandomPartId();
        partId.setText(String.valueOf(id));
        internal = true;
        external = false;
    }

    /**
     * This method generates a random part id
     * @return Returns a generated part id
     */
    public static int getRandomPartId () {
        Random rand = new Random();
        int upperBound = 25 + getAllParts().size();
        int randomId = rand.nextInt(upperBound);

        for (Part part : getAllParts()) {
            if (randomId == part.getId() || randomId == 0) {
                return getRandomPartId();
            }
        }
        return randomId;
    }

    /**
     * This method takes the user to the main screen
     * @param  actionEvent Save or cancel button being pressed
     * @throws IOException Checks to see if the main screen will load correctly
     */
    public void toMain(ActionEvent actionEvent) throws IOException {
        System.out.println(actionEvent);
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method adds a part to the inventory
     * @param actionEvent Save button clicked to save the part
     * @throws IOException Checks to make sure the main screen loads correctly
     */
    public void addNewPart(ActionEvent actionEvent) throws IOException {
        String errorStr = checkInputs(partName, partPrice, partMax, partMin, partInventory, partMultiChoice, internal, external);

        if (errorStr.isEmpty()) {
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

            // after save go back to main screen
            toMain(actionEvent);

        }
        else {
            // show error string to user
            JOptionPane.showMessageDialog(null, errorStr);
        }

    }

    /**
     * This method sets the selection to In-House
     * @param actionEvent When In-House radio button is clicked
     */
    public void setInHouse(ActionEvent actionEvent) {
        internal = true;
        external = false;
        sourceText.setText("Machine ID");
    }

    /**
     *  This method sets the selection to Outsourced
     * @param actionEvent When Outsourced radio button is clicked
     */
    public void setOutsourced(ActionEvent actionEvent) {
        external = true;
        internal = false;
        sourceText.setText("Company");
    }

    /**
     * This method checks to see if the string input has only letters or spaces
     * @param text String to be tested for characters
     * @return returns true if there are only characters and spaces, otherwise returns false
     */
    public static boolean isLetters(String text) {
        String trimmed = text.replaceAll("\\s+","");
        char[] textArray = trimmed.toCharArray();

        for (char index : textArray) {
            if(!Character.isLetter(index)) return false;
        }
        return true;
    }

    /**
     * This method checks to see if the string input has only digits
     * @param text String to be tested for digits
     * @return returns true if there are only digits, otherwise returns false
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
     * This method checks to see if the input string is a decimal number
     * @param text String to be tested as a decimal
     * @return returns true if it is a decimal number, otherwise returns false
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

    /**
     * This method checks to see if all the text fields pass input validation
     * @param name Part name
     * @param price Part price
     * @param max Part max
     * @param min Part min
     * @param inventory Part stock
     * @param multiChoice Part machine id or company name
     * @param in Part in-house
     * @param out Part outsourced
     * @return Returns a string of errors
     */
    public static String checkInputs(TextField name, TextField price, TextField max, TextField min, TextField inventory, TextField multiChoice, boolean in, boolean out) {
        StringBuilder errorBuild = new StringBuilder();
        int numError = 0;
        String error;

        // name check
        if (name.getText().isEmpty()) {
            errorBuild.append("Name cannot be empty\n");
        }
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
        else if (Integer.parseInt(min.getText()) < 0) {
            errorBuild.append("Min cannot be negative\n");
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

        // machine id
        if (in) {
            if (multiChoice.getText().isEmpty()) errorBuild.append("Machine ID cannot be empty\n");
            else if (!isInteger(max.getText())) errorBuild.append("Machine ID must be a number with no decimal\n");
        }
        // company name
        if (out) {
            if (multiChoice.getText().isEmpty()) errorBuild.append("Company Name cannot be empty\n");
            else if (!isLetters(multiChoice.getText())) errorBuild.append("Company Name must be only letters\n");
        }

        error = errorBuild.toString();
        return error;

    }

}