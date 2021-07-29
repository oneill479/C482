package Model;

/**
 * Class Outsourced.java
 */

/**
 *
 * @author Caleb O'Neill
 */
public class Outsourced extends Part {
    private String companyName;

    public Outsourced (int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * This method sets the company name of the part
     * @param companyName The company name
     */
    public void setCompanyName(String companyName) {this.companyName = companyName;}

    /**
     * This method gets the company name of the part
     * @return returns the company name
     */
    public String getCompanyName() {return companyName;}
}
