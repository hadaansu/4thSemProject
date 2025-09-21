/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class Customer {
    private int id;
    private String name;
    private String meterNumber;
    private String address;
    private int unitsConsumed;
    private double billAmount;
    private String password;  // ✅ Add this field

    public Customer(int id, String name, String meterNumber, String address, int unitsConsumed, double billAmount, String password) {
        this.id = id;
        this.name = name;
        this.meterNumber = meterNumber;
        this.address = address;
        this.unitsConsumed = unitsConsumed;
        this.billAmount = billAmount;
        this.password = password;
    }

    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUnitsConsumed() {
        return unitsConsumed;
    }

    public void setUnitsConsumed(int unitsConsumed) {
        this.unitsConsumed = unitsConsumed;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "Customer ID: " + id + "\nName: " + name + "\nMeter Number: " + meterNumber +
               "\nAddress: " + address + "\nUnits Consumed: " + unitsConsumed + "\nBill Amount: Rs. " + billAmount;
    }
}
