package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    // Runtime Error: did not set allParts and allProducts to FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    //public static Part lookupPart(int partId) {

   // }

   // public static Product lookupProduct(int productId) {

    //}

    //public static ObservableList<Part> lookupPart(String partName) {

    //}

    //public static ObservableList<Product> lookupProduct(String productName) {

    //}

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        try {
            int selection = allParts.indexOf(selectedPart);
            allParts.remove(selection);
            return true;
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static boolean deleteProduct(Product selectedProduct) {
        try {
            int selection = allProducts.indexOf(selectedProduct);
            allProducts.remove(selection);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
