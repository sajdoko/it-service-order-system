import Interfaces.Item;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents a table model for an order. It extends the AbstractTableModel class.
 */
public class OrderTableModel extends AbstractTableModel {
    // List of items in the order
    private final List<Item> items = new ArrayList<>();
    // Names of the columns in the table
    private final String[] columnNames = {"Item Name", "Quantity", "Price", "Total Price"};
    // The order for which the table model is created
    private final Order order;

    /**
     * Constructor for the OrderTableModel class.
     *
     * @param order The order for which the table model is created
     */
    public OrderTableModel(Order order) {
        this.order = order;
    }

    /**
     * Sets the items in the table model.
     *
     * @param orderItems A map of items and their quantities
     */
    public void setItems(Map<Item, Integer> orderItems) {
        items.clear();
        items.addAll(orderItems.keySet());
        fireTableDataChanged();
    }

    /**
     * Returns the number of rows in the table, which is the size of the items list.
     *
     * @return The number of rows in the table
     */
    @Override
    public int getRowCount() {
        return items.size();
    }

    /**
     * Returns the number of columns in the table, which is the length of the columnNames array.
     *
     * @return The number of columns in the table
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Returns the item at a specific row.
     *
     * @param rowIndex The index of the row
     * @return The item at the specified row
     */
    public Item getItemAt(int rowIndex) {
        return items.get(rowIndex);
    }

    /**
     * Returns the value at a specific cell.
     *
     * @param rowIndex    The index of the row
     * @param columnIndex The index of the column
     * @return The value at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item item = items.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> item.getName(); // Item name
            case 1 -> order.getItems().get(item); // Quantity
            case 2 -> "$" + item.getPrice(); // Price
            case 3 -> "$" + item.getPrice() * order.getItems().get(item); // Total price
            default -> null;
        };
    }

    /**
     * Returns the name of a specific column.
     *
     * @param column The index of the column
     * @return The name of the specified column
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}