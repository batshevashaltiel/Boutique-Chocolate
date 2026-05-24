package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ecommerce.model.Product;
import com.ecommerce.util.Database;

public class ProductDAO {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return products;
    }
    
    public List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return products;
    }

    public List<Product> searchProducts(String searchTerm) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ? OR description LIKE ? OR category LIKE ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String queryTerm = "%" + searchTerm + "%";
            stmt.setString(1, queryTerm);
            stmt.setString(2, queryTerm);
            stmt.setString(3, queryTerm);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return products;
    }

   
    public List<Product> getFilteredProducts(String category, String searchTerm) {
        List<Product> products = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");
        
        boolean hasCategory = (category != null && !category.isEmpty() && !category.equals("הכל"));
        boolean hasSearch = (searchTerm != null && !searchTerm.trim().isEmpty());
        
        if (hasCategory) {
            sql.append(" AND category = ?");
        }
        
        if (hasSearch) {
            sql.append(" AND (name LIKE ? OR description LIKE ?)");
        }
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            
            if (hasCategory) {
                stmt.setString(paramIndex++, category);
            }
            
            if (hasSearch) {
                String queryTerm = "%" + searchTerm + "%";
                stmt.setString(paramIndex++, queryTerm);
                stmt.setString(paramIndex++, queryTerm);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return products;
    }

    public Product getProductById(int id) {
        Product p = null;
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    p = mapProduct(rs);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return p;
    }

    public int getLowStockCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM products WHERE quantity < 10";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return count;
    }

    public List<Product> getLowStockProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE quantity < 10";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return products;
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO products (name, description, price, image_url, quantity, category) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getImageUrl());
            stmt.setInt(5, product.getQuantity());
            stmt.setString(6, product.getCategory());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name=?, description=?, price=?, image_url=?, quantity=?, category=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getImageUrl());
            stmt.setInt(5, product.getQuantity());
            stmt.setString(6, product.getCategory());
            stmt.setInt(7, product.getId());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public void purchaseProduct(int productId) {
        String sql = "UPDATE products SET quantity = quantity - 1 WHERE id = ? AND quantity > 0";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private Product mapProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getDouble("price"));
        p.setImageUrl(rs.getString("image_url"));
        p.setQuantity(rs.getInt("quantity"));
        
        String cat = rs.getString("category");
        p.setCategory(cat != null ? cat : "כללי");
        
        return p;
    }
}