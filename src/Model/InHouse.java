package Model;

/**
 * Class InHouse.java
 */

/**
 *
 * @author Caleb O'Neill
 */
public class InHouse extends Part {
     private int machineId;
     public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
         super(id, name, price, stock, min, max);
         this.machineId = machineId;

    }

    /**
     * This method sets the machine id
     * @param machineId The machine Id for part
     */
    public void setMachineId(int machineId) {this.machineId = machineId;}

    /**
     * This method returns the machine id
     * @return the machine id
     */
    public int getMachineId() {return machineId;}
}
