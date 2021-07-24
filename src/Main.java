import Model.Part;
import Model.Product;
import Model.InHouse;
import Model.Outsourced;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static Model.Inventory.addPart;
import static Model.Inventory.addProduct;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Part wheel = new InHouse(1, "Handle Bar", 17.99, 4, 2, 8, 2593);
        System.out.println(String.valueOf(wheel));
        addPart(wheel);
        Part tire = new Outsourced(2, "Tire", 12.99, 8, 1, 6, "Sears");
        addPart(tire);

        Product dirtBike = new Product(1, "Dirt Bike", 79.99, 3, 1, 4);
        addProduct(dirtBike);
        Product cruiser = new Product(2, "Cruiser Bike", 59.99, 5, 1, 8);
        addProduct(cruiser);

        Parent root = FXMLLoader.load(getClass().getResource("View/MainScreen.fxml"));
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) { launch(args); }

}
