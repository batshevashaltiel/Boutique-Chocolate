package com.ecommerce.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import com.ecommerce.dao.CartDAO;
import com.ecommerce.dao.OrderDAO;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;

@ManagedBean
@SessionScoped
public class CartBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private ProductDAO productDAO = new ProductDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private CartDAO cartDAO = new CartDAO(); 
    
    private List<CartItem> cartItems = new ArrayList<>();
    private Order selectedOrderForView; 
    private int confirmationNumber;

    public Order getSelectedOrderForView() { 
        return selectedOrderForView; 
    }
    
    public void setSelectedOrderForView(Order selectedOrderForView) { 
        this.selectedOrderForView = selectedOrderForView; 
    }
    
    public int getConfirmationNumber() { 
        return confirmationNumber; 
    }

    public void viewOrderDetails(Order order) {
        this.selectedOrderForView = order;
    }
    
    public void closeOrderDetails() {
        this.selectedOrderForView = null;
    }
    
    public String getFormattedItemsSummary() {
        if (selectedOrderForView == null || selectedOrderForView.getItemsSummary() == null) return "";
        return selectedOrderForView.getItemsSummary().replace(", ", "<br/>• ");
    }

    public void setOrderStatus(Order order, String newStatus) {
        orderDAO.updateStatus(order.getId(), newStatus);
        order.setStatus(newStatus); 
    }

    public List<CartItem> getCartItems() {
        Integer userId = getUserId();
        if (userId != null && cartItems.isEmpty()) {
            cartItems = cartDAO.getUserCart(userId);
        }
        return cartItems;
    }

    public void addToCart(int productId) {
        System.out.println(">>> מנסה להוסיף מוצר מספר: " + productId);
        getCartItems(); 

        Product p = productDAO.getProductById(productId);
        if (p == null) return;

        boolean found = false;
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == productId) {
                if (item.getQuantity() < p.getQuantity()) {
                    item.setQuantity(item.getQuantity() + 1);
                    updateDbIfLogged(item); 
                }
                found = true;
                break;
            }
        }
        
        if (!found) {
            CartItem newItem = new CartItem(p, 1);
            cartItems.add(newItem);
            updateDbIfLogged(newItem); 
        }
    }
    
    public void updateQuantity(CartItem item) {
        if (item.getQuantity() > item.getProduct().getQuantity()) {
            item.setQuantity(item.getProduct().getQuantity());
        }
        if (item.getQuantity() < 1) {
            item.setQuantity(1);
        }
        updateDbIfLogged(item); 
    }

    public void removeProduct(int productId) {
        Iterator<CartItem> iter = cartItems.iterator();
        while (iter.hasNext()) {
            if (iter.next().getProduct().getId() == productId) {
                iter.remove();
                Integer userId = getUserId();
                if (userId != null) {
                    cartDAO.removeItem(userId, productId);
                }
                break;
            }
        }
    }

    public void saveLocalCartToDb(int userId) {
        if (cartItems == null || cartItems.isEmpty()) return;
        for (CartItem item : cartItems) {
            cartDAO.upsertItem(userId, item.getProduct().getId(), item.getQuantity());
        }
    }

    private void updateDbIfLogged(CartItem item) {
        Integer userId = getUserId();
        if (userId != null) {
            cartDAO.upsertItem(userId, item.getProduct().getId(), item.getQuantity());
        }
    }

    private Integer getUserId() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            return (Integer) session.getAttribute("userId");
        }
        return null;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }
    
    public String checkout() {
        if (cartItems.isEmpty()) return null;
        Integer userId = getUserId();
        
        StringBuilder summary = new StringBuilder();
        for (CartItem item : cartItems) {
            summary.append(item.getProduct().getName())
                   .append(" (x").append(item.getQuantity()).append("), ");
            
            for(int i=0; i<item.getQuantity(); i++) {
                productDAO.purchaseProduct(item.getProduct().getId());
            }
        }
        
        Order order = new Order();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        
        String customerEmail = (session != null && session.getAttribute("userEmail") != null) 
                              ? (String) session.getAttribute("userEmail") 
                              : "אורח (לא רשום)";
        
        this.confirmationNumber = (int) (Math.random() * 900000) + 100000;
        
        order.setCustomerName(customerEmail);
        order.setTotalPrice(getTotalPrice());
        order.setItemsSummary(summary.toString());
        order.setConfirmationNumber(this.confirmationNumber); 
        orderDAO.saveOrder(order); 
        
        cartItems.clear();
        if (userId != null) {
            cartDAO.clearUserCart(userId);
        }
        
        try {
            ProductBean productBean = (ProductBean) FacesContext.getCurrentInstance()
                .getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{productBean}", ProductBean.class);
            if (productBean != null) {
                productBean.refreshProducts();
            }
        } catch (Exception e) {
            System.out.println(">>> שגיאה ברענון הקטלוג לאחר רכישה: " + e.getMessage());
        }
        
        return "confirmation?faces-redirect=true";
    }
    
    public List<Order> getAllOrders() { return orderDAO.getAllOrders(); }
    public double getTotalRevenue() { return orderDAO.getTotalRevenue(); }
}