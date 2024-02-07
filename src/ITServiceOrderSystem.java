import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ITServiceOrderSystem extends JFrame {
    private JComboBox<ITService> serviceComboBox;
    private JTextField quantityField;
    private JTextArea orderSummaryArea;
    private final Order order = new Order();
    private final List<ITService> availableServices = new ArrayList<>();

    public ITServiceOrderSystem() {
        createUI();
    }

    private void createUI() {
        setTitle("IT Service Order System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        availableServices.add(new ITService("Software Development", 100));
        availableServices.add(new ITService("Network Setup", 80));
        availableServices.add(new ITService("Database Administration", 90));

        serviceComboBox = new JComboBox<>(availableServices.toArray(new ITService[0]));
        add(serviceComboBox);

        add(new JLabel("Quantity:"));
        quantityField = new JTextField(5);
        add(quantityField);

        JButton addButton = new JButton("Add to Order");
        addButton.addActionListener(this::addToOrder);
        add(addButton);

        JButton lockButton = new JButton("Lock Order");
        lockButton.addActionListener(this::lockOrder);
        add(lockButton);

        orderSummaryArea = new JTextArea(10, 30);
        add(new JScrollPane(orderSummaryArea));

        setVisible(true);
    }

    private void addToOrder(ActionEvent e) {
        try {
            ITService selectedService = (ITService) serviceComboBox.getSelectedItem();
            int quantity = Integer.parseInt(quantityField.getText());
            order.addItem(selectedService, quantity);
            orderSummaryArea.append(selectedService + " x" + quantity + "\n");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding to order: " + ex.getMessage());
        }
    }

    private void lockOrder(ActionEvent e) {
        try {
            order.lock();
            saveOrderToFile();
            JOptionPane.showMessageDialog(this, "Order locked and saved.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error locking order: " + ex.getMessage());
        }
    }

    private void saveOrderToFile() {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", true)))) {
            out.println("Order Summary:");
            out.println(orderSummaryArea.getText());
            out.println("Total: $" + order.getPrice());
            out.println("-----");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to save order: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ITServiceOrderSystem::new);
    }
}
