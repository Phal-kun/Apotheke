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
public class ProductUnit {
    int productUnitID;
    String productUnitName;

    public ProductUnit() {
    }

    public ProductUnit(int productUnitID, String productUnitName) {
        this.productUnitID = productUnitID;
        this.productUnitName = productUnitName;
    }
    
    private static final Logger LOG = Logger.getLogger(ProductUnit.class.getName());

    public int getProductUnitID() {
        return productUnitID;
    }

    public void setProductUnitID(int productUnitID) {
        this.productUnitID = productUnitID;
    }

    public String getProductUnitName() {
        return productUnitName;
    }

    public void setProductUnitName(String productUnitName) {
        this.productUnitName = productUnitName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.productUnitID;
        hash = 83 * hash + Objects.hashCode(this.productUnitName);
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
        final ProductUnit other = (ProductUnit) obj;
        if (this.productUnitID != other.productUnitID) {
            return false;
        }
        return Objects.equals(this.productUnitName, other.productUnitName);
    }

    @Override
    public String toString() {
        return "ProductUnit{" + "productUnitID=" + productUnitID + ", productUnitName=" + productUnitName + '}';
    }
       
}
