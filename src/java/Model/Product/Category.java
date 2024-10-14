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

    public Category() {
    }

    public Category(int categoryID, Category parentCategory, String categoryName, String description) {
        this.categoryID = categoryID;
        this.parentCategory = parentCategory;
        this.categoryName = categoryName;
        this.description = description;
    }
    
    private static final Logger LOG = Logger.getLogger(Category.class.getName());

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.categoryID;
        hash = 59 * hash + Objects.hashCode(this.parentCategory);
        hash = 59 * hash + Objects.hashCode(this.categoryName);
        hash = 59 * hash + Objects.hashCode(this.description);
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
        final Category other = (Category) obj;
        if (this.categoryID != other.categoryID) {
            return false;
        }
        if (!Objects.equals(this.categoryName, other.categoryName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return Objects.equals(this.parentCategory, other.parentCategory);
    }

    @Override
    public String toString() {
        return "Category{" + "categoryID=" + categoryID + ", parentCategory=" + (parentCategory != null ? parentCategory.categoryID : "null")  + ", categoryName=" + categoryName + ", description=" + description + '}';
    }
    
    
    
}
