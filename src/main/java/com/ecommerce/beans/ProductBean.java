package com.ecommerce.beans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.Product;

@ManagedBean
@SessionScoped
public class ProductBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private ProductDAO productDAO = new ProductDAO();
    private String searchTerm;
    
    private Product currentProduct = new Product();
    private transient Part imageFile;
    private boolean editMode = false;
    
    private boolean lowStockFilter = false;
    private Product selectedProduct;
    private boolean showForm = false; 
    
    private String selectedCategoryFilter = "הכל";
    private List<Product> cachedProducts;

    @PostConstruct
    public void init() {
        refreshProducts();
    }

    public void refreshProducts() {
        if (lowStockFilter) {
            this.cachedProducts = productDAO.getLowStockProducts();
        } else {
            this.cachedProducts = productDAO.getFilteredProducts(selectedCategoryFilter, searchTerm);
        }

        boolean isManagementPage = false;
        try {
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            if (viewId != null && viewId.contains("admin")) {
                isManagementPage = true;
            }
        } catch (Exception e) {}

        if (!isManagementPage && this.cachedProducts != null) {
            List<Product> availableOnly = new ArrayList<>();
            for (Product p : this.cachedProducts) {
                if (p.getQuantity() > 0) {
                    availableOnly.add(p);
                }
            }
            this.cachedProducts = availableOnly;
        }
    }

    public List<String> getCategoriesList() {
        return Arrays.asList("פטיפורים", "עוגות ויטרינה", "עוגות פס", "קינוחים", "עיצובים");
    }

    public boolean isShowForm() { return showForm; }

    public String openAddMode() {
        clearForm();
        this.showForm = true; 
        this.editMode = false;
        this.lowStockFilter = false; 
        return "admin?faces-redirect=true"; 
    }

    public void prepareEdit(Product p) {
        this.currentProduct = p;
        this.editMode = true;
        this.showForm = true; 
    }
    
    public void closeForm() {
        this.showForm = false;
        clearForm();
    }

    public Product getCurrentProduct() { return currentProduct; }
    public void setCurrentProduct(Product currentProduct) { this.currentProduct = currentProduct; }
    public Part getImageFile() { return imageFile; }
    public void setImageFile(Part imageFile) { this.imageFile = imageFile; }
    public boolean isEditMode() { return editMode; }
    public String getSearchTerm() { return searchTerm; }
    
    public void setSearchTerm(String searchTerm) { 
        this.searchTerm = searchTerm; 
        refreshProducts(); 
    }
    
    public Product getSelectedProduct() { return selectedProduct; }
    public void setSelectedProduct(Product selectedProduct) { this.selectedProduct = selectedProduct; }
    
    public String getSelectedCategoryFilter() { return selectedCategoryFilter; }
    public void setSelectedCategoryFilter(String selectedCategoryFilter) { 
        this.selectedCategoryFilter = selectedCategoryFilter; 
        this.searchTerm = null; // כשלוחצים על כפתור קטגוריה, מנקים את שורת החיפוש
        this.lowStockFilter = false;
        refreshProducts();
    }

    public List<Product> getProducts() {
        if (cachedProducts == null) {
            refreshProducts();
        }
        return cachedProducts;
    }
    
    public void filterByCategory(String category) {
        this.selectedCategoryFilter = category;
        this.searchTerm = null; 
        this.lowStockFilter = false;
        refreshProducts();
    }
    
    public void toggleLowStockFilter() {
        this.lowStockFilter = !this.lowStockFilter;
        this.searchTerm = null;
        this.selectedCategoryFilter = "הכל";
        this.showForm = false; 
        refreshProducts(); 
    }
    
    public int getLowStockCount() { return productDAO.getLowStockCount(); }

    public void clearForm() {
        this.currentProduct = new Product();
        this.editMode = false;
        this.imageFile = null;
    }

    public String saveProduct() {
        if (imageFile != null) {
            String fileName = saveImageFile();
            if (fileName != null) {
                currentProduct.setImageUrl(fileName);
            }
        }
        
        if (editMode) {
            productDAO.updateProduct(currentProduct);
        } else {
            if (currentProduct.getImageUrl() == null || currentProduct.getImageUrl().isEmpty()) {
                currentProduct.setImageUrl("https://via.placeholder.com/300");
            }
            productDAO.addProduct(currentProduct);
        }

        clearForm();
        this.showForm = false; 
        refreshProducts(); 
        return "admin?faces-redirect=true";
    }

    public void deleteProduct(int id) {
        productDAO.deleteProduct(id);
        refreshProducts(); 
    }
    
    public void loadProduct() {
        String idParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                this.selectedProduct = productDAO.getProductById(id);
            } catch (NumberFormatException e) { e.printStackTrace(); }
        }
    }
    
    private String saveImageFile() {
        try (InputStream input = imageFile.getInputStream()) {
            String fileName = getFileNameFromPart(imageFile);
            String folderPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "resources/images/";
            File folder = new File(folderPath);
            if (!folder.exists()) { folder.mkdirs(); }
            File file = new File(folderPath + fileName);
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return "resources/images/" + fileName;
        } catch (IOException e) { e.printStackTrace(); return null; }
    }

    private String getFileNameFromPart(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "unknown.jpg";
    }
}