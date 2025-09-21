package LoginPage;

import CONFIG.ConnectionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1, 10, 10));

        JButton addCustomerBtn = new JButton("Add Customer");
        JButton generateBillBtn = new JButton("Generate Bill");
        JButton viewCustomersBtn = new JButton("View All Customers");
        JButton searchCustomerBtn = new JButton("Search Customer");
        JButton updateCustomerBtn = new JButton("Update Customer");
        JButton deleteCustomerBtn = new JButton("Delete Customer");
        JButton logoutBtn = new JButton("Logout");

        add(addCustomerBtn);
        add(generateBillBtn);
        add(viewCustomersBtn);
        add(searchCustomerBtn);
        add(updateCustomerBtn);
        add(deleteCustomerBtn);
        add(logoutBtn);

        // Button Listeners

        addCustomerBtn.addActionListener(e -> addCustomer());
        generateBillBtn.addActionListener(e -> generateBill());
        viewCustomersBtn.addActionListener(e -> viewCustomers());
        searchCustomerBtn.addActionListener(e -> searchCustomer());
        updateCustomerBtn.addActionListener(e -> updateCustomer());
        deleteCustomerBtn.addActionListener(e -> deleteCustomer());
        logoutBtn.addActionListener(e -> {
            dispose();
            LoginPage.main(null);
        });
    }

    private void addCustomer() {
        JTextField nameField = new JTextField();
        JTextField meterField = new JTextField();
        JTextField addressField = new JTextField();
        Object[] message = {
                "Name:", nameField,
                "Meter Number:", meterField,
                "Address:", addressField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Customer", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String meter = meterField.getText();
            String address = addressField.getText();

            try (Connection con = ConnectionFactory.getConnection()) {
                String sql = "INSERT INTO customers (name, meter_number, address) VALUES (?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, name);
                    ps.setString(2, meter);
                    ps.setString(3, address);
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Customer added successfully.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add customer.");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void generateBill() {
        JTextField meterField = new JTextField();
        JTextField unitsField = new JTextField();
        Object[] message = {
                "Meter Number:", meterField,
                "Units Consumed:", unitsField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Generate Bill", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String meter = meterField.getText();
            int units;
            try {
                units = Integer.parseInt(unitsField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Units must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double billAmount = calculateBill(units);

            try (Connection con = ConnectionFactory.getConnection()) {
                String sql = "UPDATE customers SET units_consumed = ?, bill_amount = ?, bill_month = ? WHERE meter_number = ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, units);
                    ps.setDouble(2, billAmount);
                    ps.setString(3, "October"); // You can make this dynamic
                    ps.setString(4, meter);

                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Bill generated successfully.\nBill Amount: Rs." + billAmount);
                    } else {
                        JOptionPane.showMessageDialog(this, "Meter number not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void viewCustomers() {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM customers";
            try (Statement stmt = con.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                StringBuilder sb = new StringBuilder();
                while (rs.next()) {
                    sb.append("ID: ").append(rs.getInt("id"))
                      .append(", Name: ").append(rs.getString("name"))
                      .append(", Meter: ").append(rs.getString("meter_number"))
                      .append(", Units: ").append(rs.getInt("units_consumed"))
                      .append(", Bill: Rs.").append(rs.getDouble("bill_amount"))
                      .append("\n");
                }
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(350, 300));
                JOptionPane.showMessageDialog(this, scrollPane, "All Customers", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void searchCustomer() {
        String meter = JOptionPane.showInputDialog(this, "Enter Meter Number to Search:");
        if (meter == null || meter.trim().isEmpty()) return;

        try (Connection con = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM customers WHERE meter_number = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, meter);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String details = "ID: " + rs.getInt("id") + "\n"
                            + "Name: " + rs.getString("name") + "\n"
                            + "Address: " + rs.getString("address") + "\n"
                            + "Units: " + rs.getInt("units_consumed") + "\n"
                            + "Bill: Rs." + rs.getDouble("bill_amount");
                    JOptionPane.showMessageDialog(this, details, "Customer Found", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateCustomer() {
        String meter = JOptionPane.showInputDialog(this, "Enter Meter Number to Update:");
        if (meter == null || meter.trim().isEmpty()) return;

        try (Connection con = ConnectionFactory.getConnection()) {
            String checkSql = "SELECT * FROM customers WHERE meter_number = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                checkStmt.setString(1, meter);
                ResultSet rs = checkStmt.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String currentName = rs.getString("name");
                String currentAddress = rs.getString("address");

                JTextField nameField = new JTextField(currentName);
                JTextField addressField = new JTextField(currentAddress);
                Object[] message = {
                        "New Name (leave blank to keep current):", nameField,
                        "New Address (leave blank to keep current):", addressField
                };

                int option = JOptionPane.showConfirmDialog(this, message, "Update Customer", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String newName = nameField.getText().trim();
                    if (newName.isEmpty()) newName = currentName;

                    String newAddress = addressField.getText().trim();
                    if (newAddress.isEmpty()) newAddress = currentAddress;

                    String updateSql = "UPDATE customers SET name = ?, address = ? WHERE meter_number = ?";
                    try (PreparedStatement updateStmt = con.prepareStatement(updateSql)) {
                        updateStmt.setString(1, newName);
                        updateStmt.setString(2, newAddress);
                        updateStmt.setString(3, meter);

                        int rows = updateStmt.executeUpdate();
                        if (rows > 0) {
                            JOptionPane.showMessageDialog(this, "Customer updated successfully.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to update customer.");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteCustomer() {
        String meter = JOptionPane.showInputDialog(this, "Enter Meter Number to Delete:");
        if (meter == null || meter.trim().isEmpty()) return;

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete customer with meter number: " + meter + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection con = ConnectionFactory.getConnection()) {
                String sql = "DELETE FROM customers WHERE meter_number = ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, meter);

                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Customer deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Meter number not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private double calculateBill(int units) {
        if (units <= 100) return units * 5;
        if (units <= 300) return 100 * 5 + (units - 100) * 7;
        return 100 * 5 + 200 * 7 + (units - 300) * 10;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}
