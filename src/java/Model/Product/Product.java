/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Product;

import java.util.ArrayList;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author ASUS
 */
public class Product {
    int productID;
    String productName;
    Category category;
    Origin origin;
    Manufacturer manufacturer;
    Form form;
    String description;
    Date importDate;
    Date exportDate;
    ArrayList<Component> component;

    public Product() {
    }

    public Product(int productID, String productName, Category category, Origin origin, Manufacturer manufacturer, Form form, String description, Date importDate, Date exportDate, ArrayList component) {
        this.productID = productID;
        this.productName = productName;
        this.category = category;
        this.origin = origin;
        this.manufacturer = manufacturer;
        this.form = form;
        this.description = description;
        this.importDate = importDate;
        this.exportDate = exportDate;
        this.component = component;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public Date getExportDate() {
        return exportDate;
    }

    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }

    public ArrayList getComponent() {
        return component;
    }

    public void setComponent(ArrayList component) {
        this.component = component;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.productID;
        hash = 71 * hash + Objects.hashCode(this.productName);
        hash = 71 * hash + Objects.hashCode(this.category);
        hash = 71 * hash + Objects.hashCode(this.origin);
        hash = 71 * hash + Objects.hashCode(this.manufacturer);
        hash = 71 * hash + Objects.hashCode(this.form);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + Objects.hashCode(this.importDate);
        hash = 71 * hash + Objects.hashCode(this.exportDate);
        hash = 71 * hash + Objects.hashCode(this.component);
        return hash;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.productID != other.productID) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        if (!Objects.equals(this.origin, other.origin)) {
            return false;
        }
        if (!Objects.equals(this.manufacturer, other.manufacturer)) {
            return false;
        }
        if (!Objects.equals(this.form, other.form)) {
            return false;
        }
        if (!Objects.equals(this.importDate, other.importDate)) {
            return false;
        }
        if (!Objects.equals(this.exportDate, other.exportDate)) {
            return false;
        }
        return Objects.equals(this.component, other.component);
    }

    @Override
    public String toString() {
        return "Product{" + "productID=" + productID + ", productName=" + productName + ", category=" + category + ", origin=" + origin + ", manufacturer=" + manufacturer + ", form=" + form + ", description=" + description + ", importDate=" + importDate + ", exportDate=" + exportDate + ", component=" + component + '}';
    }

    
    
}
