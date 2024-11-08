/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Product.forCustomer;

import Model.Product.Product;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.util.Map;

/**
 *
 * @author Dell
 */
public class Item {
    private int productID ; 
    private String productName;
    private String description;
    // 1 list đối tượng listPrice<name, price>
    List<Map<String, Object>> listPrice;
    private String nameShow;
    private int quantity; 
    private float price;
    
    public Item() {
        this.listPrice = new ArrayList<>(); // Khởi tạo listPrice để tránh NullPointerException
    }
    public Item(int productID, String productName, String description, List<Map<String, Object>> listPrice,
                 String nameShow, int quantity, float price) {
         this.productID = productID;
         this.productName = productName;
         this.description = description;
         this.listPrice = listPrice;
         this.nameShow = nameShow;
         this.quantity = quantity;
         this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Map<String, Object>> getListPrice() {
        return listPrice;
    }

    public void setListPrice(List<Map<String, Object>> listPrice) {
        this.listPrice = listPrice;
    }

    public String getNameShow() {
        return nameShow;
    }

    public void setNameShow(String nameShow) {
        this.nameShow = nameShow;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    // Phương thức để thêm giá trị vào listPrice
    public void addPrice(String name, double price) {
        Map<String, Object> priceEntry = Map.of("name", name, "price", price);
        listPrice.add(priceEntry);
    }

    @Override
    public String toString() {
        return "Item{" + "productID=" + productID + ", productName=" + productName + ", description=" + description + ", listPrice=" + listPrice + ", nameShow=" + nameShow + ", quantity=" + quantity + ", price=" + price + '}';
    }
    public String getFormattedTotalPrice() {
        
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(price);
    }

    
}
