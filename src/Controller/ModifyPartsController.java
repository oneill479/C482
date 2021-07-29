package Controller;

/**
 * Class ModifyPartsController.java
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
import java.util.ResourceBundle;

import static Model.Inventory.getAllParts;
import static Model.Inventory.updatePart;

/**
 * This method modifies a part in the inventory
 */
public class ModifyPartsController implements Initializable {

    private int id, stock, min, max, machineId;
    private String name, company;
    private double price;
    private boolean internal, external;

    private Part selectedPart;

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

    InHouse in;
    Outsourced out;

    /**
     * This initializes the modify parts controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // get selected part from main screen controller
        selectedPart = MainScreenController.getPart();
        // fill in all the text boxes
        partId.setText(Integer.toString(selectedPart.getId()));
        partName.setText(selectedPart.getName());
        partInventory.setText(Integer.toString(selectedPart.getStock()));
        partPrice.setText(Double.toString(selectedPart.getPrice()));
        partMax.setText(Integer.toString(selectedPart.getMax()));
        partMin.setText(Integer.toString(selectedPart.getMin()));

        if (selectedPart.getClass() == InHouse.class) {
            inHouse.setSelected(true);
            in = (InHouse) selectedPart;
            partMultiChoice.setText(Integer.toString(in.getMachineId()));
            internal = true;
            external = false;
        }

        if (selectedPart.getClass() == Outsourced.class) {
            outsourced.setSelected(true);
            sourceText.setText("Company");
            out = (Outsourced) selectedPart;
            partMultiChoice.setText(out.getCompanyName());
            external = true;
            internal = false;
        }

    }

    /**
     * This method takes the user to the main screen
     * @param  actionEvent Save or cancel button being pressed
     * @throws IOException Checks to see if the main screen will load correctly
     */
    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method sets the selection to In-House
     * @param actionEvent When In-House radio button is clicked
     */
    public void setInHouse(ActionEvent actionEvent) {
        internal = true;
        external = false;
        sourceText.setText("Machine ID");
        if (selectedPart.getClass() == InHouse.class) {
            partMultiChoice.setText(Integer.toString(in.getMachineId()));
        }
        else {
            partMultiChoice.setText("");
        }
    }

    /**
     *  This method sets the selection to Outsourced
     * @param actionEvent When Outsourced radio button is clicked
     */
    public void setOutsourced(ActionEvent actionEvent) {
        external = true;
        internal = false;
        sourceText.setText("Company");
        if (selectedPart.getClass() == Outsourced.class) {
            partMultiChoice.setText(out.getCompanyName());
        }
        else {
            partMultiChoice.setText("");
        }
    }

    /**
     * This method modifies a part to the inventory
     * @param actionEvent Save button clicked to modify the part
     * @throws IOException Checks to make sure the main screen loads correctly
     */
    public void modifyPart(ActionEvent actionEvent) throws IOException {

        String errorStr = AddPartsController.checkInputs(partName, partPrice, partMax, partMin, partInventory, partMultiChoice, internal, external);

        if (errorStr.isEmpty()) {
            id = selectedPart.getId();
            name = partName.getText();
            stock = Integer.parseInt(partInventory.getText());
            price = Double.parseDouble(partPrice.getText());
            min = Integer.parseInt(partMin.getText());
            max = Integer.parseInt(partMax.getText());

            if (inHouse.isSelected()) {
                machineId = Integer.parseInt(partMultiChoice.getText());
                Part newPart = new InHouse(id, name, price, stock, min, max, machineId);
                updatePart(getAllParts().indexOf(selectedPart), newPart);
            }
            if (outsourced.isSelected()) {
                company = partMultiChoice.getText();
                Part newPart = new Outsourced(id, name, price, stock, min, max, company);
                updatePart(getAllParts().indexOf(selectedPart), newPart);
            }

            // after save go back to main screen
            toMain(actionEvent);

        }
        else {
            // show error string to user
            JOptionPane.showMessageDialog(null, errorStr);
        }
    }
}