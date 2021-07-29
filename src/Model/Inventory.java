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

    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = getAllParts();

        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            if (part.getId() == partId) return part;
        }

        return null;
   }

    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = getAllProducts();

        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            if (product.getId() == productId) return product;
        }

        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        for (Part part : allParts) {
            if(part.getName().toLowerCase().contains(partName)) foundParts.add(part);
        }

        return foundParts;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = getAllProducts();

        for (Product product : allProducts) {
            if(product.getName().toLowerCase().contains(productName)) foundProducts.add(product);
        }

        return foundProducts;
    }

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
