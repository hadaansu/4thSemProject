/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package config;

import java.sql.*;
import model.Customer; // Import the Customer model

public class Jdbc {

    // Establish connection to MySQL database
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // Load the MySQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Establish and return the connection to the 'electricity_billing_system' database
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/electricity_billing_system", // Change database name to 'electricity_billing_system'
                "root", // Your MySQL username
                "" // Your MySQL password (empty for XAMPP)
        );
    }

    // Insert customer data into the customers table
    public static void insertCustomer(Customer customer) {
        String query = "INSERT INTO customers (name, meter_number, units_consumed, bill_amount) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getMeterNumber());
            ps.setInt(3, customer.getUnitsConsumed());
            ps.setDouble(4, customer.getBillAmount());
            
            int row = ps.executeUpdate();
            System.out.println(row + " customer added.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fetch bill history for all customers
    public static void findAllBillHistory() {
        String query = "SELECT c.name, c.meter_number, bh.bill_date, bh.bill_amount FROM customers c " +
                       "JOIN bill_history bh ON c.meter_number = bh.meter_number";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                String meterNumber = rs.getString("meter_number");
                Date billDate = rs.getDate("bill_date");
                double billAmount = rs.getDouble("bill_amount");

                // Print the bill history for each customer
                System.out.println("Name: " + name + ", Meter: " + meterNumber + ", Bill Date: " + billDate + ", Bill Amount: Rs. " + billAmount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main method to test insertion and retrieval
    public static void main(String[] args) {
        // Test insertion of a new customer into the customers table
        //insertCustomer(new Customer("John Doe", "AB12345", 100, 500.0));

        // Test fetching bill history for all customers
        findAllBillHistory();
    }
}
