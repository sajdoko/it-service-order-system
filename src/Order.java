import Exceptions.EmptyOrderException;
import Exceptions.ItemOutOfStockException;
import Exceptions.OrderLockedException;
import Exceptions.ServiceNotAvailableException;
import Interfaces.Item;

import java.util.HashMap;
import java.util.Map;

public class Order {
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

    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Retrieves the items in the order.
     *
     * @return The items in the order.
     */
    public Map<Item, Integer> getItems() {
        return items;
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

    /**
     * Clears the current order.
     * This method removes all items from the order and unlocks it.
     */
    public void clearOrder() {
        items.clear();
        locked = false;
    }

    /**
     * Unlocks the order.
     */
    public void unlock() {
        locked = false;
    }
}
