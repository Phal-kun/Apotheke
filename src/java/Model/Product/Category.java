/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Product;

import java.util.Objects;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Category {
    private int categoryID;
    private Category parentCategory;
    private String categoryName;    
    private String description;
    private boolean status;

    public Category() {
    }
    
    public Category(int categoryID, Category parentCategory, String categoryName, String description, boolean status) {
        this.categoryID = categoryID;
        this.parentCategory = parentCategory;
        this.categoryName = categoryName;
        this.description = description;
        this.status = status;
    }

    public Category(int categoryID, Category parentCategory, String categoryName, String description) {
        this.categoryID = categoryID;
        this.parentCategory = parentCategory;
        this.categoryName = categoryName;
        this.description = description;
    }
    
    

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Category{" + "categoryID=" + categoryID + ", parentCategory=" + parentCategory + ", categoryName=" + categoryName + ", description=" + description + ", status=" + status + '}';
    }
    
    
}