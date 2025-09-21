package LoginPage;

import DAO.CustomerDao;
import DAO.CustomerDAOImpl;
import Model.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JComboBox<String> roleCombo;  // ✅ Dropdown for role

    private final CustomerDao customerDao = new CustomerDAOImpl();

    public LoginPage() {
        setTitle("Login Page");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Components
        JLabel roleLabel = new JLabel("Select Role:");
        roleCombo = new JComboBox<>(new String[]{"Admin", "User"});  // Admin/User

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");

        // Add components to frame
        add(roleLabel);
        add(roleCombo);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());  // empty cell
        add(loginButton);

        // Button listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
    }

    private void authenticateUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String role = (String) roleCombo.getSelectedItem();  // ✅ Get selected role

        if ("Admin".equals(role)) {
            if ("admin".equals(username) && "admin123".equals(password)) {
                JOptionPane.showMessageDialog(this, "Welcome Admin!");
                new AdminDashboard().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Admin credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else { // User login
            Customer customer = customerDao.loginCustomer(username, password);  // or meterNumber
            if (customer != null) {
                JOptionPane.showMessageDialog(this, "Welcome " + customer.getName() + "!");
                new UserDashboard(customer.getMeterNumber()).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid User credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
