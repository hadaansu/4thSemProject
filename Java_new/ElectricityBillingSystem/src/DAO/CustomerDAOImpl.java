package DAO;

import CONFIG.ConnectionFactory;
import Model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDao {

    @Override
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (name, meter_number, address, password) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getMeterNumber());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPassword());  // ✅ password added
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET name=?, address=?, password=? WHERE meter_number=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPassword());  // ✅ password updatable
            ps.setString(4, customer.getMeterNumber());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(String meterNumber) {
        String sql = "DELETE FROM customers WHERE meter_number=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, meterNumber);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Customer getCustomerByMeterNumber(String meterNumber) {
        String sql = "SELECT * FROM customers WHERE meter_number=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, meterNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("meter_number"),
                        rs.getString("address"),
                        rs.getInt("units_consumed"),
                        rs.getDouble("bill_amount"),
                        rs.getString("password")   // ✅ include password
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Connection con = ConnectionFactory.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("meter_number"),
                        rs.getString("address"),
                        rs.getInt("units_consumed"),
                        rs.getDouble("bill_amount"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean generateBill(String meterNumber, int units, String month) {
        double amount = calculateBill(units);
        String sql = "UPDATE customers SET units_consumed=?, bill_amount=?, bill_month=? WHERE meter_number=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, units);
            ps.setDouble(2, amount);
            ps.setString(3, month);
            ps.setString(4, meterNumber);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private double calculateBill(int units) {
        if (units <= 100) return units * 5;
        if (units <= 300) return 100 * 5 + (units - 100) * 7;
        return 100 * 5 + 200 * 7 + (units - 300) * 10;
    }

    @Override
    public boolean payBill(String meterNumber) {
        String sql = "UPDATE customers SET bill_amount=0 WHERE meter_number=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, meterNumber);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ New login method
    @Override
    public Customer loginCustomer(String username, String password) {
        String sql = "SELECT * FROM customers WHERE name=? AND password=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("meter_number"),
                        rs.getString("address"),
                        rs.getInt("units_consumed"),
                        rs.getDouble("bill_amount"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
