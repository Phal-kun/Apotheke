/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Order;

import Model.Product.Product;
import Model.Product.ProductDetail;
import Model.Product.ProductUnit;
import java.util.Objects;

/**
 *
 * @author ASUS
 */
public class OrderDetail {
    private int orderDetailID;
    private Product product;
    private ProductDetail productDetail;
    private ProductUnit unit;
    private double soldPrice;
    private int quantity;
    private double totalProductPrice;

    public OrderDetail() {
    }

    public OrderDetail(int orderDetailID, Product product, ProductDetail productDetail, ProductUnit unit, double soldPrice, int quantity, double totalProductPrice) {
        this.orderDetailID = orderDetailID;
        this.product = product;
        this.productDetail = productDetail;
        this.unit = unit;
        this.soldPrice = soldPrice;
        this.quantity = quantity;
        this.totalProductPrice = totalProductPrice;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public ProductUnit getUnit() {
        return unit;
    }

    public void setUnit(ProductUnit unit) {
        this.unit = unit;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(double totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.orderDetailID;
        hash = 17 * hash + Objects.hashCode(this.product);
        hash = 17 * hash + Objects.hashCode(this.productDetail);
        hash = 17 * hash + Objects.hashCode(this.unit);
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.soldPrice) ^ (Double.doubleToLongBits(this.soldPrice) >>> 32));
        hash = 17 * hash + this.quantity;
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.totalProductPrice) ^ (Double.doubleToLongBits(this.totalProductPrice) >>> 32));
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
        final OrderDetail other = (OrderDetail) obj;
        if (this.orderDetailID != other.orderDetailID) {
            return false;
        }
        if (Double.doubleToLongBits(this.soldPrice) != Double.doubleToLongBits(other.soldPrice)) {
            return false;
        }
        if (this.quantity != other.quantity) {
            return false;
        }
        if (Double.doubleToLongBits(this.totalProductPrice) != Double.doubleToLongBits(other.totalProductPrice)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.productDetail, other.productDetail)) {
            return false;
        }
        return Objects.equals(this.unit, other.unit);
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "orderDetailID=" + orderDetailID + ", product=" + product + ", productDetail=" + productDetail + ", unit=" + unit + ", soldPrice=" + soldPrice + ", quantity=" + quantity + ", totalProductPrice=" + totalProductPrice + '}';
    }
    
    
}
