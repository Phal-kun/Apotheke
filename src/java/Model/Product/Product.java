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
    String manufacturer;
    String componentDescription;
    String description;
    ProductUnit baseUnit;
    ArrayList<ProductUnit> unit;
    ArrayList<Component> component;
    boolean isActive;

    public Product() {
    }

    public Product(int productID, String productName, Category category, Origin origin, String manufacturer, String componentDescription, String description, ProductUnit baseUnit, ArrayList<ProductUnit> unit, ArrayList<Component> component, boolean isActive) {
        this.productID = productID;
        this.productName = productName;
        this.category = category;
        this.origin = origin;
        this.manufacturer = manufacturer;
        this.componentDescription = componentDescription;
        this.description = description;
        this.baseUnit = baseUnit;
        this.unit = unit;
        this.component = component;
        this.isActive = isActive;
    }

    public ProductUnit getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(ProductUnit baseUnit) {
        this.baseUnit = baseUnit;
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList getComponent() {
        return component;
    }

    public void setComponent(ArrayList component) {
        this.component = component;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.productID;
        hash = 71 * hash + Objects.hashCode(this.productName);
        hash = 71 * hash + Objects.hashCode(this.category);
        hash = 71 * hash + Objects.hashCode(this.origin);
        hash = 71 * hash + Objects.hashCode(this.manufacturer);
        hash = 71 * hash + Objects.hashCode(this.description);
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

        return Objects.equals(this.component, other.component);
    }

    @Override
    public String toString() {
        return "Product{" + "productID=" + productID + ", productName=" + productName + ", category=" + category + ", origin=" + origin + ", manufacturer=" + manufacturer + ", componentDescription=" + componentDescription + ", description=" + description + ", baseUnit=" + baseUnit + ", unit=" + unit + ", component=" + component + ", isActive=" + isActive + '}';
    }

}
