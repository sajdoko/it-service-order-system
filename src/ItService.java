import Interfaces.Item;

import java.util.*;

/**
 * This class represents an IT service that implements the Interfaces.Item interface.
 * It contains a name and a price, and provides methods to get the price,
 * convert the service to a string, check equality with another service,
 * and calculate the hash code.
 */
class ITService implements Item {
    private final String name;
    private final int price;

    /**
     * Constructor for the ITService class.
     * Initializes the service with a name and a price.
     *
     * @param name  The name of the service.
     * @param price The price of the service.
     */
    public ITService(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Retrieves the price of the service.
     *
     * @return The price of the service.
     */
    @Override
    public int getPrice() {
        return price;
    }

    /**
     * Converts the service to a string in the format "name - $price".
     *
     * @return The string representation of the service.
     */
    @Override
    public String toString() {
        return name + " - $" + price;
    }

    /**
     * Checks if this service is equal to another object.
     * They are equal if the other object is an ITService and they have the same name and price.
     *
     * @param o The object to compare with.
     * @return True if they are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ITService itService = (ITService) o;
        return price == itService.price &&
               Objects.equals(name, itService.name);
    }

    /**
     * Calculates the hash code of the service based on its name and price.
     *
     * @return The hash code of the service.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}

