/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model;

public class Customer {
    private String name;
    private String meterNumber;
    private int unitsConsumed;
    private double billAmount;

    // Constructor
    public Customer(String name, String meterNumber, int unitsConsumed, double billAmount) {
        this.name = name;
        this.meterNumber = meterNumber;
        this.unitsConsumed = unitsConsumed;
        this.billAmount = billAmount;
    }

    // Getters and Setters
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

    // toString method to print customer details
    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", meterNumber='" + meterNumber + '\'' +
                ", unitsConsumed=" + unitsConsumed +
                ", billAmount=" + billAmount +
                '}';
    }
}
