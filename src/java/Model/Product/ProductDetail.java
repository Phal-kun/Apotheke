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
public class ProductDetail {
    int productDetailID;
    Product product;
    ProductUnit productUnit;
    int volume, stock, price;
    int batchNo;

    public ProductDetail() {
    }

    public ProductDetail(int productDetailID, Product product, ProductUnit productUnit, int volume, int stock, int price) {
        this.productDetailID = productDetailID;
        this.product = product;
        this.productUnit = productUnit;
        this.volume = volume;
        this.stock = stock;
        this.price = price;
    }

    public ProductDetail(int productDetailID, Product product, ProductUnit productUnit, int volume, int stock, int price, int batchNo) {
        this.productDetailID = productDetailID;
        this.product = product;
        this.productUnit = productUnit;
        this.volume = volume;
        this.stock = stock;
        this.price = price;
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

    public ProductUnit getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(ProductUnit productUnit) {
        this.productUnit = productUnit;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(int batchNo) {
        this.batchNo = batchNo;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.productDetailID;
        hash = 11 * hash + Objects.hashCode(this.product);
        hash = 11 * hash + Objects.hashCode(this.productUnit);
        hash = 11 * hash + this.volume;
        hash = 11 * hash + this.stock;
        hash = 11 * hash + this.price;
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
        if (this.volume != other.volume) {
            return false;
        }
        if (this.stock != other.stock) {
            return false;
        }
        if (this.price != other.price) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return Objects.equals(this.productUnit, other.productUnit);
    }

    @Override
    public String toString() {
        return "ProductDetail{" + "productDetailID=" + productDetailID + ", product=" + product + ", productUnit=" + productUnit + ", volume=" + volume + ", stock=" + stock + ", price=" + price + '}';
    }
       
    
}
