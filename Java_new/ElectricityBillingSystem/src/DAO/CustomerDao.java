package DAO;

import Model.Customer;
import java.util.List;

public interface CustomerDao {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(String meterNumber);
    Customer getCustomerByMeterNumber(String meterNumber);
    List<Customer> getAllCustomers();
    boolean generateBill(String meterNumber, int units, String month);
    boolean payBill(String meterNumber);

    // ✅ Add new login method
    Customer loginCustomer(String username, String password);
}
