package com.ecommerce.beans;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import com.ecommerce.dao.UserDAO;
import com.ecommerce.model.User;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
    private String name; 
    private boolean admin = false;
    private boolean loggedIn = false;

    // --- Getters & Setters ---
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getName() { return name; } 
    public void setName(String name) { this.name = name; } 
    
    public boolean isAdmin() { return admin; }
    public boolean isLoggedIn() { return loggedIn; }

    // --- התחברות ---
    public String validateUsernamePassword() {
        UserDAO dao = new UserDAO();
        User user = dao.validateUser(email, password);
        
        if (user != null) {
            this.loggedIn = true;
            this.admin = user.isAdmin(); 
            this.name = user.getName(); 
            
            // שמירה ב-Session
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("userEmail", email);
            session.setAttribute("userName", name);
            session.setAttribute("userId", user.getId()); // חשוב מאוד!
            
            // --- התיקון החדש: סנכרון הסל ---
            // ניגשים ל-CartBean ושומרים את המוצרים שנאספו לפני ההתחברות
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                CartBean cartBean = (CartBean) context.getApplication()
                    .evaluateExpressionGet(context, "#{cartBean}", CartBean.class);
                
                if (cartBean != null) {
                    cartBean.saveLocalCartToDb(user.getId());
                }
            } catch (Exception e) {
                e.printStackTrace(); // למקרה של תקלה בסנכרון
            }

            return "products?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, "מייל או סיסמה שגויים", ""));
            return "login";
        }
    }

    // --- הרשמה ---
    public String register() {
        UserDAO dao = new UserDAO();
        
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setName(name); 
        newUser.setAdmin(false); 

        if (dao.registerUser(newUser)) {
            // התחברות אוטומטית אחרי הרשמה (זה יקרא לפונקציה למעלה וישמור גם את ה-ID)
            return validateUsernamePassword();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "שגיאה: המייל כבר קיים במערכת", ""));
            return "register";
        }
    }

    // --- יציאה ---
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.loggedIn = false;
        this.admin = false;
        this.email = null;
        this.password = null;
        this.name = null;
        return "products?faces-redirect=true";
    }
}