package Controller;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Model.Inventory.addPart;
import static Model.Inventory.updatePart;

public class ModifyPartsController implements Initializable {

    private int id, stock, min, max, machineId, listLength;
    private String name, company;
    private double price;
    boolean internal, external;

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
            InHouse in = (InHouse) selectedPart;
            partMultiChoice.setText(Integer.toString(in.getMachineId()));
            internal = true;
            external = false;
        }

        if (selectedPart.getClass() == Outsourced.class) {
            outsourced.setSelected(true);
            sourceText.setText("Company");
            Outsourced out = (Outsourced) selectedPart;
            partMultiChoice.setText(out.getCompanyName());
            external = true;
            internal = false;
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

    public void addNewPart(ActionEvent actionEvent) throws IOException {

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
                updatePart(id - 1, newPart);
            }
            if (outsourced.isSelected()) {
                company = partMultiChoice.getText();
                Part newPart = new Outsourced(id, name, price, stock, min, max, company);
                updatePart(id - 1, newPart);
            }

            // after save go back to main screen
            toMain(actionEvent);

        }
    }
}