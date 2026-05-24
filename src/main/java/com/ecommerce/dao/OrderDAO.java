package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ecommerce.model.Order;
import com.ecommerce.util.Database;

public class OrderDAO {

    public void saveOrder(Order order) {
        String sql = "INSERT INTO orders (customer_name, total_price, items_summary, status, confirmation_number) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, order.getCustomerName());
            stmt.setDouble(2, order.getTotalPrice());
            stmt.setString(3, order.getItemsSummary());
            stmt.setString(4, "התקבל"); 
            stmt.setInt(5, order.getConfirmationNumber()); // שמירת המספר
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setCustomerName(rs.getString("customer_name"));
                o.setTotalPrice(rs.getDouble("total_price"));
                o.setItemsSummary(rs.getString("items_summary"));
                o.setOrderDate(rs.getTimestamp("order_date"));
                
                String status = rs.getString("status");
                o.setStatus(status != null ? status : "התקבל");
                
                o.setConfirmationNumber(rs.getInt("confirmation_number"));
                
                orders.add(o);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return orders;
    }
    
    public void updateStatus(int orderId, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public double getTotalRevenue() {
        double total = 0;
        String sql = "SELECT SUM(total_price) FROM orders";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) total = rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return total;
    }
}