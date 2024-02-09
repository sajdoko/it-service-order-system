package Interfaces;

/**
 * This is the Item interface for the IT service system.
 * It provides the structure for any item in the system.
 */
public interface Item {

    /**
     * This method is used to get the price of the item.
     *
     * @return int This returns the price of the item.
     */
    int getPrice();

    /**
     * This method is used to get the name of the item.
     *
     * @return String This returns the name of the item.
     */
    String getName();
}