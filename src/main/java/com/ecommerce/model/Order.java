package com.ecommerce.model;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String customerName; 
    private double totalPrice;
    private String itemsSummary;
    private Date orderDate;
    private String status; 
    
    // ---> השדה החדש <---
    private int confirmationNumber; 

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getItemsSummary() { return itemsSummary; }
    public void setItemsSummary(String itemsSummary) { this.itemsSummary = itemsSummary; }
    
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public int getConfirmationNumber() { return confirmationNumber; }
    public void setConfirmationNumber(int confirmationNumber) { this.confirmationNumber = confirmationNumber; }
}