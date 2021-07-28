package Model;

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
     *
     * @param id The product id to set
     */
    public void setId(int id) {this.id = id;}

    /**
     *
     * @param name The product name to set
     */
    public void setName(String name) {this.name = name;}

    /**
     *
     * @param price The product price
     */
    public void setPrice(double price) {this.price = price;}

    /**
     *
     * @param stock The amount of product in stock
     */
    public void setStock(int stock) {this.stock = stock;}

    public void setMin(int min) {this.min = min;}

    public void setMax(int max) {this.max = max;}

    /**
     *
     * @return The Id of the product
     */
    public int getId() {return id;}

    /**
     *
     * @return The name of the product
     */
    public String getName() {return name;}

    /**
     *
     * @return The price of the product
     */
    public double getPrice() {return price;}

    /**
     *
     * @return The amount in stock for the product
     */
    public int getStock() {return stock;}

    /**
     *
     * @return The minimum amount of the product
     */
    public int getMin() {return min;}

    /**
     *
     * @return The maximum amount of the product
     */
    public int getMax() {return max;}

    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        boolean deleted = associatedParts.remove(selectedAssociatedPart);
        return deleted;
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
