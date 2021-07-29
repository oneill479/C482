package Model;

/**
 * Supplied class Product.java
 */

/**
 *
 * @author Caleb O'Neill
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This method sets the id
     * @param id The product id to set
     */
    public void setId(int id) {this.id = id;}

    /**
     * This method sets the name
     * @param name The product name to set
     */
    public void setName(String name) {this.name = name;}

    /**
     * This method sets the price
     * @param price The product price
     */
    public void setPrice(double price) {this.price = price;}

    /**
     * This method sets the stock amount
     * @param stock The amount of product in stock
     */
    public void setStock(int stock) {this.stock = stock;}

    /**
     * This method sets the min
     * @param min The minimum amount of product
     */
    public void setMin(int min) {this.min = min;}

    /**
     * This method sets the max
     * @param max The maximum amount of product
     */
    public void setMax(int max) {this.max = max;}

    /**
     * This method gets the id
     * @return The id of the product
     */
    public int getId() {return id;}

    /**
     * This method gets the name
     * @return The name of the product
     */
    public String getName() {return name;}

    /**
     * This method gets the price
     * @return The price of the product
     */
    public double getPrice() {return price;}

    /**
     * This method gets the stock
     * @return The amount in stock for the product
     */
    public int getStock() {return stock;}

    /**
     * This method gets the min
     * @return The minimum amount of the product
     */
    public int getMin() {return min;}

    /**
     * This method gets the max
     * @return The maximum amount of the product
     */
    public int getMax() {return max;}

    /**
     * This method adds an associated part to the product
     * @param part the part to be added
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * This method deletes an associated part of the product
     * @param selectedAssociatedPart the part to be deleted
     * @return returns true if the part is removed, otherwise returns false
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * This method gets all the associated parts of the product
     * @return returns the list of parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
