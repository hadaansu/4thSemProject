/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Scanner;
import loginpage.AdminDashboardd;
import loginpage.UserDashboardd;
public class LoginPage {

    public static void main(String[] args) {
     
        Scanner scanner = new Scanner(System.in);

        // Get username and password input
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        if (username.equals("admin") && password.equals("admin123")) {
            System.out.println("Welcome Admin!");
          
            System.out.println("Opening Admin Dashboard...");
        } else if (username.equals("user") && password.equals("user123")) {
            System.out.println("Welcome User!");
            System.out.println("Opening User Dashboard...");
        } else {
            System.out.println("Invalid. Please try again.");
        }

   
        scanner.close();
    }
}
