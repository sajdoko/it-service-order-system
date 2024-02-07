package Exceptions;
import Interfaces.Item;

/**
 * This class represents a custom exception for when an item is out of stock.
 */
public class ItemOutOfStockException extends Exception {
    /**
     * Constructor for the ItemOutOfStockException class.
     * Initializes the exception with a custom message that includes the name of the out-of-stock item.
     *
     * @param item The item that is out of stock.
     */
    public ItemOutOfStockException(Item item) {
        super("Interfaces.Item '" + item.getClass().getSimpleName() + "' is out of stock.");
    }
}
