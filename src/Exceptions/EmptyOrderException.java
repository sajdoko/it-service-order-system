package Exceptions;

/**
 * This class represents a custom exception for when an order is empty.
 */
public class EmptyOrderException extends Exception {
    /**
     * Constructor for the EmptyOrderException class.
     * Initializes the exception with a custom message.
     */
    public EmptyOrderException() {
        super("The order is empty.");
    }
}
