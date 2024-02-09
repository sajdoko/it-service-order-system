import Exceptions.ItemOutOfStockException;
import Exceptions.OrderLockedException;
import Exceptions.ServiceNotAvailableException;
import Interfaces.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ITServiceOrderSystem extends JFrame {
    private JComboBox<ITService> serviceComboBox;
    private JTextField quantityField;
    private final Order order = new Order();
    private final List<ITService> availableServices = new ArrayList<>();
    private OrderTableModel tableModel;
    private JTable orderTable;

    /**
     * The constructor for the ITServiceOrderSystem class.
     * It calls the createUI method to set up the User Interface for the IT Service Order System.
     */
    public ITServiceOrderSystem() {
        createUI();
    }

    /**
     * This method is used to create the User Interface for the IT Service Order System.
     * It also loads orders from a file if it exists.
     */
    private void createUI() {
        // Set the title of the frame
        setTitle("IT Service Order System");

        // Set the icon of the frame
        ImageIcon imgIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/iconImage.png")));
        setIconImage(imgIcon.getImage());

        // Set the size of the frame
        setSize(500, 600);
        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the layout of the frame
        setLayout(new FlowLayout());

        // Add services to the availableServices list
        availableServices.add(new ITService("Software Development", 100));
        availableServices.add(new ITService("Network Setup", 80));
        availableServices.add(new ITService("Database Administration", 90));

        // Initialize the serviceComboBox and add it to the frame
        serviceComboBox = new JComboBox<>(availableServices.toArray(new ITService[0]));
        add(serviceComboBox);

        // Load orders from a file if it exists
        File orderFile = new File("orders.txt");
        if (orderFile.exists()) {
            loadOrdersFromFile(orderFile);
        }

        // Add a label and a text field for quantity to the frame
        add(new JLabel("Quantity:"));
        quantityField = new JTextField(4);
        add(quantityField);

        // Initialize the addButton, set its icon and action listener, and add it to the frame
        JButton addButton = new JButton("Add to Order");
        addButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/addToCartN.png"))));
        addButton.setRolloverIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/addToCart.png"))));
        addButton.addActionListener(this::addToOrder);
        add(addButton);

        // Initialize the lockButton, set its action listener, and add it to the frame
        JButton lockButton = new JButton("Lock Order");
        lockButton.addActionListener(this::lockOrder);
        add(lockButton);

        // Initialize the unlockButton, set its action listener, and add it to the frame
        JButton unlockButton = new JButton("Unlock Order");
        unlockButton.addActionListener(this::unlockOrder);
        add(unlockButton);

        // Initialize the tableModel and orderTable, and add the orderTable to the frame
        tableModel = new OrderTableModel(order);
        orderTable = new JTable(tableModel);
        add(new JScrollPane(orderTable));

        // Initialize the removeButton, set its icon and action listener, and add it to the frame
        JButton removeButton = new JButton("Remove from Order");
        removeButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/removeFromCart.png"))));
        removeButton.addActionListener(this::removeFromOrder);
        add(removeButton);

        // Initialize the deleteOrdersButton, set its icon and action listener, and add it to the frame
        JButton deleteOrdersButton = new JButton("Delete Orders");
        deleteOrdersButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/deleteOrder.png"))));
        deleteOrdersButton.setRolloverIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/deleteOrderO.png"))));
        deleteOrdersButton.addActionListener(this::deleteOrders);
        add(deleteOrdersButton);

        // Set the items of the tableModel
        tableModel.setItems(order.getItems());
        // Make the frame visible
        setVisible(true);
    }

    /**
     * This method is used to load orders from a given file.
     * The file is read line by line, where each line represents an order.
     * Each line is split into parts by the "-" delimiter.
     * If any exception occurs during this process, it is caught and handled appropriately.
     *
     * @param file The file from which to load the orders.
     * @throws RuntimeException if a ServiceNotAvailableException, ItemOutOfStockException, or OrderLockedException is thrown.
     */
    private void loadOrdersFromFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("-");
                if (parts.length == 3) {
                    String itemName = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    ITService service = availableServices.stream()
                            .filter(s -> s.getName().equals(itemName))
                            .findFirst()
                            .orElse(null);
                    if (service != null) {
                        // Add the item to the order
                        order.addItem(service, quantity);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading orders from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing order quantity: " + e.getMessage());
        } catch (ServiceNotAvailableException | ItemOutOfStockException | OrderLockedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * This method is used to add a service to the order.
     *
     * @param e The action event that triggered this method.
     */
    private void addToOrder(ActionEvent e) {
        try {
            ITService selectedService = (ITService) serviceComboBox.getSelectedItem();
            int quantity = Integer.parseInt(quantityField.getText());
            order.addItem(selectedService, quantity);
            tableModel.setItems(order.getItems());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding to order: " + ex.getMessage());
        }
    }

    /**
     * This method is used to remove an item from the order.
     *
     * @param e The action event that triggered this method.
     */
    private void removeFromOrder(ActionEvent e) {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            Item selectedItem = tableModel.getItemAt(selectedRow);
            order.removeItem(selectedItem);
            tableModel.setItems(order.getItems());
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.");
        }
    }

    /**
     * This method is used to delete all orders.
     *
     * @param e The action event that triggered this method.
     */
    private void deleteOrders(ActionEvent e) {
        File orderFile = new File("orders.txt");
        if (orderFile.exists()) {
            boolean deleted = orderFile.delete();
            if (deleted) {
                order.clearOrder();
                JOptionPane.showMessageDialog(this, "Orders deleted successfully.");
                tableModel.setItems(order.getItems());

            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete orders.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No orders file found to delete.");
        }
    }

    /**
     * This method is used to lock the order.
     *
     * @param e The action event that triggered this method.
     */
    private void lockOrder(ActionEvent e) {
        try {
            order.lock();
            saveOrderToFile();
            JOptionPane.showMessageDialog(this, "Order locked and saved.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error locking order: " + ex.getMessage());
        }
    }

    /**
     * This method is used to unlock the order.
     *
     * @param e The action event that triggered this method.
     */
    private void unlockOrder(ActionEvent e) {
        order.unlock();
        JOptionPane.showMessageDialog(this, "Order is now unlocked and can be modified.");
    }

    /**
     * This method is used to save the order to a file.
     * It writes each item in the order to the file in the format: "itemName-quantity-price".
     */
    private void saveOrderToFile() {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", true)))) {
            for (Item service : order.getItems().keySet()) {
                out.println(service.getName() + "-" + order.getItems().get(service) + "-" + service.getPrice());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to save order: " + e.getMessage());
        }
    }

    /**
     * The main method of the application.
     * It creates a new instance of the ITServiceOrderSystem class on the Event Dispatch Thread.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ITServiceOrderSystem::new);
    }
}
