package Exceptions;

/**
 * This class represents a custom exception for when a service is not available.
 * It extends the Exception class and takes a message as a parameter.
 */
public class ServiceNotAvailableException extends Exception {
    /**
     * Constructor for the ServiceNotAvailableException class.
     * Initializes the exception with a custom message.
     *
     * @param message The custom message for the exception.
     */
    public ServiceNotAvailableException(String message) {
        super(message);
    }
}
