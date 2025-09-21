package LoginPage;

import DAO.CustomerDao;
import DAO.CustomerDAOImpl;
import Model.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDashboard extends JFrame {

    private final CustomerDao customerDao = new CustomerDAOImpl();
    private String meterNumber;

    private JLabel nameLabel;
    private JLabel addressLabel;
    private JLabel unitsLabel;
    private JLabel billLabel;

    private JButton payButton;
    private JButton logoutButton;

    public UserDashboard(String meterNumber) {
        this.meterNumber = meterNumber;

        setTitle("User Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        // Initialize labels
        nameLabel = new JLabel();
        addressLabel = new JLabel();
        unitsLabel = new JLabel();
        billLabel = new JLabel();

        // Initialize buttons
        payButton = new JButton("Pay Bill");
        logoutButton = new JButton("Logout");

        add(nameLabel);
        add(addressLabel);
        add(unitsLabel);
        add(billLabel);
        add(payButton);
        add(logoutButton);

        // Load customer data
        loadCustomerDetails();

        // Button actions
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payBill();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
    }

    private void loadCustomerDetails() {
        Customer customer = customerDao.getCustomerByMeterNumber(meterNumber);
        if (customer != null) {
            nameLabel.setText("Name: " + customer.getName());
            addressLabel.setText("Address: " + customer.getAddress());
            unitsLabel.setText("Units Consumed: " + customer.getUnitsConsumed());
            billLabel.setText("Bill Amount: Rs. " + customer.getBillAmount());
        } else {
            JOptionPane.showMessageDialog(this, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void payBill() {
        int confirm = JOptionPane.showConfirmDialog(this, "Do you want to pay the bill?", "Confirm Payment", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (customerDao.payBill(meterNumber)) {
                JOptionPane.showMessageDialog(this, "Bill paid successfully.");
                loadCustomerDetails(); // Refresh after payment
            } else {
                JOptionPane.showMessageDialog(this, "Payment failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void logout() {
        dispose();
        LoginPage.main(null);  // Return to login screen (adjust if needed)
    }
}
