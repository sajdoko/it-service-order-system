package Exceptions;

/**
 * This class represents a custom exception for when an order is locked.
 */
public class OrderLockedException extends Exception {
    /**
     * Constructor for the OrderLockedException class.
     * Initializes the exception with a custom message.
     */
    public OrderLockedException() {
        super("The order is locked.");
    }
}
