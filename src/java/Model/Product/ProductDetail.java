/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Product;

import java.util.Date;
import java.util.Objects;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ProductDetail {
    int productDetailID;
    Product product;
    ProductUnit unit;
    int stock;
    double importPrice, soldPrice;
    Date manufactureDate, expiredDate;
    boolean isActive;
    int batchNo;

    public ProductDetail() {
    }

    public ProductDetail(int productDetailID, Product product, ProductUnit productUnit, int stock, double importPrice, double soldPrice, Date manufactureDate, Date expiredDate, boolean isActive) {
        this.productDetailID = productDetailID;
        this.product = product;
        this.unit = productUnit;
        this.stock = stock;
        this.importPrice = importPrice;
        this.soldPrice = soldPrice;
        this.manufactureDate = manufactureDate;
        this.expiredDate = expiredDate;
        this.isActive = isActive;
    }

    public ProductDetail(int productDetailID, Product product, ProductUnit productUnit, int volume, int stock, int price, int batchNo) {
        this.productDetailID = productDetailID;
        this.product = product;
        this.stock = stock;
        this.batchNo = batchNo;
    }

    public ProductDetail(int batchNo) {
        this.batchNo = batchNo;
    }
    
    private static final Logger LOG = Logger.getLogger(ProductDetail.class.getName());

    public int getProductDetailID() {
        return productDetailID;
    }

    public void setProductDetailID(int productDetailID) {
        this.productDetailID = productDetailID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductUnit getUnit() {
        return unit;
    }

    public void setUnit(ProductUnit unit) {
        this.unit = unit;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(int batchNo) {
        this.batchNo = batchNo;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.productDetailID;
        hash = 41 * hash + Objects.hashCode(this.product);
        hash = 41 * hash + Objects.hashCode(this.unit);
        hash = 41 * hash + this.stock;
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.importPrice) ^ (Double.doubleToLongBits(this.importPrice) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.soldPrice) ^ (Double.doubleToLongBits(this.soldPrice) >>> 32));
        hash = 41 * hash + Objects.hashCode(this.manufactureDate);
        hash = 41 * hash + Objects.hashCode(this.expiredDate);
        hash = 41 * hash + (this.isActive ? 1 : 0);
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
        final ProductDetail other = (ProductDetail) obj;
        if (this.productDetailID != other.productDetailID) {
            return false;
        }
        if (this.stock != other.stock) {
            return false;
        }
        if (Double.doubleToLongBits(this.importPrice) != Double.doubleToLongBits(other.importPrice)) {
            return false;
        }
        if (Double.doubleToLongBits(this.soldPrice) != Double.doubleToLongBits(other.soldPrice)) {
            return false;
        }
        if (this.isActive != other.isActive) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.unit, other.unit)) {
            return false;
        }
        if (!Objects.equals(this.manufactureDate, other.manufactureDate)) {
            return false;
        }
        return Objects.equals(this.expiredDate, other.expiredDate);
    }

    @Override
    public String toString() {
        return "ProductDetail{" + "productDetailID=" + productDetailID + ", product=" + product + ", productUnit=" + unit + ", stock=" + stock + ", importPrice=" + importPrice + ", soldPrice=" + soldPrice + ", manufactureDate=" + manufactureDate + ", expiredDate=" + expiredDate + ", isActive=" + isActive + '}';
    }
}
