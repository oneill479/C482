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
        selectedPart = MainScreenController.getPart();
        System.out.println(selectedPart);
        partId.setText(Integer.toString(selectedPart.getId()));
        partName.setText(selectedPart.getName());
        partInventory.setText(Integer.toString(selectedPart.getStock()));
        partPrice.setText(Double.toString(selectedPart.getPrice()));
        partMax.setText(Integer.toString(selectedPart.getMax()));
        partMin.setText(Integer.toString(selectedPart.getMin()));
        System.out.println(selectedPart.getClass());

        if (selectedPart.getClass() == InHouse.class) {
            inHouse.setSelected(true);
            InHouse internal = (InHouse) selectedPart;
            partMultiChoice.setText(Integer.toString(internal.getMachineId()));

        }

        if (selectedPart.getClass() == Outsourced.class) {
            outsourced.setSelected(true);
            sourceText.setText("Company");
            Outsourced external = (Outsourced) selectedPart;
            partMultiChoice.setText(external.getCompanyName());
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
    }

    public void setOutsourced(ActionEvent actionEvent) {
    }

    public void addNewPart(ActionEvent actionEvent) {
    }
}