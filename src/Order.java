import Exceptions.EmptyOrderException;
import Exceptions.ItemOutOfStockException;
import Exceptions.OrderLockedException;
import Exceptions.ServiceNotAvailableException;
import Interfaces.Item;

import java.util.HashMap;
import java.util.Map;

class Order {
    private final Map<Item, Integer> items;
    private boolean locked;

    /**
     * Constructor for the Order class.
     * Initializes an empty order that is not locked.
     */
    public Order() {
        items = new HashMap<>();
        locked = false;
    }

    /**
     * Adds an item to the order.
     * Throws an exception if the order is locked, the item is not available, or the item is out of stock.
     *
     * @param item  The item to add.
     * @param count The quantity of the item.
     * @throws ServiceNotAvailableException If the item's price is 0.
     * @throws ItemOutOfStockException      If the quantity is more than 10.
     * @throws OrderLockedException         If the order is locked.
     */
    public void addItem(Item item, int count) throws ServiceNotAvailableException, ItemOutOfStockException, OrderLockedException {
        if (locked) {
            throw new OrderLockedException();
        }

        if (item.getPrice() == 0) {
            throw new ServiceNotAvailableException("Exceptions.ServiceNotAvailableException: Service '" + item + "' is not available.");
        }

        if (count > 10) {
            throw new ItemOutOfStockException(item);
        }

        items.put(item, count);
    }

    /**
     * Calculates and returns the total price of the order.
     *
     * @return The total price.
     */
    public int getPrice() {
        int total = 0;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    /**
     * Displays the order details to the console.
     * If the order is empty, it prints a specific message.
     */
    public void displayOrder() {
        if (items.isEmpty()) {
            System.out.println("The order is empty.");
            return;
        }

        System.out.println("Order:");
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            System.out.println(entry.getKey() + " x" + entry.getValue() + " $" + entry.getKey().getPrice() * entry.getValue());
        }
        System.out.println("Total: $" + getPrice());
    }

    /**
     * Locks the order.
     * Throws an exception if the order is empty.
     *
     * @throws EmptyOrderException If the order is empty.
     */
    public void lock() throws EmptyOrderException {
        if (items.isEmpty()) {
            throw new EmptyOrderException();
        }
        locked = true;
    }
}
