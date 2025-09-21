/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package loginpage;
import java.util.Scanner;

public class AdminDashboardd {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create simple storage for customer data
        String[] customerNames = new String[10];
        String[] customerMeters = new String[10];
        int[] customerUnits = new int[10];
        double[] customerBills = new double[10];
         String[] customerbillmonth = new String [10];
        int customerCount = 0; 

        // Display menu and perform operations
        while (true) {
            System.out.println("\n1. Add Customer");
            System.out.println("2. Generate Bill");
            System.out.println("3. Update Customer");
            System.out.println("4. Search Customer");
            System.out.println("5. Delete Customer");
            System.out.println("6. View All Customers");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice == 1) {
                // Add customer
                if (customerCount < 10) {
                    System.out.print("Enter customer name: ");
                    customerNames[customerCount] = scanner.nextLine();

                    System.out.print("Enter meter number: ");
                    customerMeters[customerCount] = scanner.nextLine();

                    System.out.print("Enter units consumed: ");
                    customerUnits[customerCount] = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    System.out.print("Enter month of bill: ");
                    customerbillmonth[customerCount] = scanner.nextLine();

                    System.out.println("Customer added successfully!");
                    customerCount++;
                } else {
                    System.out.println("Maximum number of customers reached.");
                }
            } else if (choice == 2) {
                // Generate bill for a customer
                System.out.print("Enter meter number of customer to generate bill: ");
                String meterNumber = scanner.nextLine();
                boolean found = false;
                for (int i = 0; i < customerCount; i++) {
                    if (customerMeters[i].equals(meterNumber)) {
                        // Calculate bill
                        int units = customerUnits[i];
                        double billAmount = 0;

                        if (units <= 100) {
                            billAmount = units * 5;
                        } else if (units <= 300) {
                            billAmount = 100 * 5 + (units - 100) * 7;
                        } else {
                            billAmount = 100 * 5 + 200 * 7 + (units - 300) * 10;
                        }

                        // Store the bill amount
                        customerBills[i] = billAmount;

                        // Display the bill
                        System.out.println("\n---- Bill Generated ----");
                        System.out.println("Customer Name: " + customerNames[i]);
                        System.out.println("Meter Number: " + customerMeters[i]);
                        System.out.println("Bill Month: " + customerbillmonth[i]);
                        System.out.println("Units Consumed: " + customerUnits[i]);
                        System.out.println("Bill Amount: Rs. " + billAmount);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Customer with Meter Number " + meterNumber + " not found.");
                }
            } else if (choice == 3) {
                // Update customer
                System.out.print("Enter meter number of customer to update: ");
                String meterNumber = scanner.nextLine();
                boolean found = false;
                for (int i = 0; i < customerCount; i++) {
                    if (customerMeters[i].equals(meterNumber)) {
                        
                        System.out.print("Enter new customer name: ");
                        customerNames[i] = scanner.nextLine();

                        System.out.print("Enter new units consumed: ");
                        customerUnits[i] = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        System.out.println("Customer updated successfully!");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Customer with Meter Number " + meterNumber + " not found.");
                }
            } else if (choice == 4) {
                // Search customer by Meter Number
                System.out.print("Enter meter number to search: ");
                String meterNumber = scanner.nextLine();
                boolean found = false;
                for (int i = 0; i < customerCount; i++) {
                    if (customerMeters[i].equals(meterNumber)) {
                        System.out.println("\n--- Customer Found ---");
                        System.out.println("Customer Name: " + customerNames[i]);
                        System.out.println("Meter Number: " + customerMeters[i]);
                        System.out.println("Bill Month: " + customerbillmonth[i]);
                        System.out.println("Units Consumed: " + customerUnits[i]);
                        System.out.println("Bill Amount: Rs. " + customerBills[i]);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Customer with Meter Number " + meterNumber + " not found.");
                }
            } else if (choice == 5) {
                // Delete customer
                System.out.print("Enter meter number of customer to delete: ");
                String meterNumber = scanner.nextLine();
                boolean found = false;
                for (int i = 0; i < customerCount; i++) {
                    if (customerMeters[i].equals(meterNumber)) {
                        
                        for (int j = i; j < customerCount - 1; j++) {
                            customerNames[j] = customerNames[j + 1];
                            customerMeters[j] = customerMeters[j + 1];
                            customerbillmonth[j] = customerbillmonth[j + 1];
                            customerUnits[j] = customerUnits[j + 1];
                            customerBills[j] = customerBills[j + 1];
                        }
                        customerCount--; 
                        System.out.println("Customer deleted successfully!");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Customer with Meter Number " + meterNumber + " not found.");
                }
            } else if (choice == 6) {
                // View all customers
                if (customerCount == 0) {
                    System.out.println("No customers to display.");
                } else {
                    System.out.println("\n--- All Customers ---");
                    for (int i = 0; i < customerCount; i++) {
                        System.out.println("Customer Name: " + customerNames[i] +
                                           ", Meter Number: " + customerMeters[i] +
                                            ",Bill Month: " + customerbillmonth[i] +
                                           ", Units Consumed: " + customerUnits[i] +
                                           ", Bill Amount: Rs. " + customerBills[i]);
                    }
                }
            } else if (choice == 7) {
                // Exit the program
                System.out.println("Exiting the program...");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
