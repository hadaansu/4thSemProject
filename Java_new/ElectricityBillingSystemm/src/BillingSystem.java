import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;
import config.ConnectionFactory; 
public class BillingSystem {

    public static void main(String[] args) {
        // Scanner to take input from the user
        Scanner scanner = new Scanner(System.in);

        // Taking customer details from the user
        System.out.println("Enter Customer Name:");
        String name = scanner.nextLine();

        System.out.println("Enter Meter Number:");
        String meter = scanner.nextLine();

        System.out.println("Enter Address:");
        String address = scanner.nextLine();

        System.out.println("Enter Units Consumed:");
        int units = scanner.nextInt();
        scanner.nextLine(); // Consume newline left by nextInt()

        // Calculate Bill based on units consumed
        double amount = 0;
        if (units <= 100) {
            amount = units * 5;
        } else if (units <= 300) {
            amount = 100 * 5 + (units - 100) * 7;
        } else {
            amount = 100 * 5 + 200 * 7 + (units - 300) * 10;
        }

        // Display calculated bill
        System.out.println("\n---- Customer Bill ----");
        System.out.println("Customer Name: " + name);
        System.out.println("Meter No: " + meter);
        System.out.println("Address: " + address);
        System.out.println("Units Consumed: " + units);
        System.out.println("Bill Amount: Rs. " + amount);

        // Insert the customer data into the database
        try {
            // Get a connection to the database
            Connection connection = ConnectionFactory.getConnection();

            // SQL query to insert data into the 'customers' table
            String query = "INSERT INTO customers (name, meter_number, address, units_consumed, bill_month, bill_amount) VALUES (?, ?, ?, ?, ?, ?)";
            
            // Prepare the SQL statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, meter);
            preparedStatement.setString(3, address);
            preparedStatement.setInt(4, units);
            preparedStatement.setString(5, "October");  // You can dynamically set the month if needed
            preparedStatement.setDouble(6, amount);

            // Execute the SQL query
            int rowsAffected = preparedStatement.executeUpdate();

            // If the insertion is successful, print a success message
            if (rowsAffected > 0) {
                System.out.println("Customer data saved successfully.");
            } else {
                System.out.println("Failed to save customer data.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}
