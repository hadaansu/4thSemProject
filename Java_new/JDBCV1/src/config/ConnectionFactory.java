/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Customer; // Change the model to 'Customer' (which should be created for customer data)

/**
 *
 * @author your_name
 */
public class ConnectionFactory {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // Load MySQL driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Database connection (localhost:3306, with 'billing_system' as the database)
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/billing_system",  // Database URL
                "root",  // MySQL username (root by default)
                ""  // Password (leave empty by default)
        );
    }

    // Insert customer into database
    public static void insert(Customer customer) {
        // Query -> Insert customer details into the customers table
        String query = "INSERT INTO customers (name, meter_number, address, units_consumed, bill_amount) VALUES (?, ?, ?, ?, ?)";

        try {
            // Establish connection
            Connection connection = getConnection();

            // Prepare the query and bind values
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getMeterNumber());
            ps.setString(3, customer.getAddress());
            ps.setInt(4, customer.getUnitsConsumed());
            ps.setDouble(5, customer.getBillAmount());

            // Execute the query
            int row = ps.executeUpdate();
            System.out.println(row + " customer(s) inserted.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all customers from the database
    public static void findAll() {
        String query = "SELECT * FROM customers";

        try {
            // Establish connection
            Connection connection = getConnection();

            // Prepare the query
            PreparedStatement ps = connection.prepareStatement(query);

            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Get customer details
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String meterNumber = rs.getString("meter_number");
                String address = rs.getString("address");
                int unitsConsumed = rs.getInt("units_consumed");
                double billAmount = rs.getDouble("bill_amount");

                // Create a Customer object and print it
                Customer customer = new Customer(id, name, meterNumber, address, unitsConsumed, billAmount);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Testing the methods
    public static void main(String[] args) {
        // Example: Insert a customer
        // Uncomment this line to insert a customer
        // insert(new Customer("John Doe", "123456", "123 Elm St", 250, 1750.0));

        // Find all customers and display them
        findAll();
    }
}
