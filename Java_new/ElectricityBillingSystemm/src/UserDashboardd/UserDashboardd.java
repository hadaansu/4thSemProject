/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginpage;
import java.util.Scanner;
public class UserDashboardd {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double userBill = 500.0;

        
        while (true) {
            System.out.println("\n--- User Dashboard ---");
            System.out.println("1. View Bill");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                
                System.out.println("\nYour Bill: Rs. " + userBill);
            } else if (choice == 2) {
           
                System.out.println("Exiting the User Dashboard...");
                break;
            } else {
                System.out.println("Invalid option! Please try again.");
            }
        }

        scanner.close();
    }
}
