package Model;

/**
 * Class Inventory.java
 */

/**
 *
 * @author Caleb O'Neill
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method looks up a part id
     * @param partId takes in a part id to search allParts
     * @return returns the part if there is a match
     */
    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = getAllParts();

        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            if (String.valueOf(part.getId()).startsWith(Integer.toString(partId))) return part;
        }

        return null;
   }

    /**
     * This method looks up a product id
     * @param productId takes in a part id to search allProducts
     * @return returns the product if there is a match
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = getAllProducts();

        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            if (String.valueOf(product.getId()).startsWith(Integer.toString(productId))) return product;
        }

        return null;
    }

    /**
     * This method looks up a part name
     * @param partName takes in a part name
     * @return returns the part if there is a match
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        for (Part part : allParts) {
            if(part.getName().toLowerCase().contains(partName)) foundParts.add(part);
        }

        return foundParts;
    }

    /**
     * This method looks up a product name
     * @param productName takes in a product name
     * @return returns the product if there is a match
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = getAllProducts();

        for (Product product : allProducts) {
            if(product.getName().toLowerCase().contains(productName)) foundProducts.add(product);
        }

        return foundProducts;
    }

    /**
     * This method updates a part in the inventory
     * @param index the index of the part
     * @param selectedPart the part that will be updated
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * This method updates a part in the inventory
     * @param index the index of the part
     * @param newProduct the product that will be updated
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * This method delets a part from the inventory
     * @param selectedPart the part that will be deleted
     * @return returns true if the part was successfully deleted, otherwise returns false
     */
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

    /**
     * This method delets a product from the inventory
     * @param selectedProduct the product that will be deleted
     * @return returns true if the product was successfully deleted, otherwise returns false
     */
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

    /**
     * This method gets a list of all the parts
     * @return returns an observable list of parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method gets a list of all the products
     * @return returns an observable list of products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
