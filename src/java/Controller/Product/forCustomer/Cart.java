/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Product.forCustomer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dell
 */
public class Cart {
    private List<Item> listItems;

    public Cart() {
        listItems = new ArrayList<>();    
    }

    public Cart(List<Item> listItems) {
        this.listItems = listItems;
    }

    public List<Item> getListItems() {
        return listItems;
    }

    public void setListItems(List<Item> listItems) {
        this.listItems = listItems;
    }
    
//    private Item getItemById(int id){
//        for (Item item : listItems) {
//            if (item.getProduct().()==id) {
//                return item;               
//            }           
//        }
//        return null;
//    }
    
//    public int getQuantityById(int id){           
//        return getItemById(id).getQuantity();
//        
//    }
//    
    
//    add item 
   public void addItem(Item item) {
    boolean itemExists = false; // Biến kiểm tra xem item đã tồn tại hay chưa

    for (Item existingItem : listItems) {
        // Nếu productID đã tồn tại trong giỏ hàng
        if (existingItem.getProductID() == item.getProductID()) {
            itemExists = true; // Đánh dấu item đã tồn tại

            // Duyệt qua listPrice của existingItem
            for (Map<String, Object> existingPriceInfo : existingItem.getListPrice()) {
                String existingName = (String) existingPriceInfo.get("name");
                float existingPrice = ((Number) existingPriceInfo.get("price")).floatValue();

                // Kiểm tra xem giá trong listPrice có trùng với item không
                if (existingName.equals(item.getListPrice().get(0).get("name")) && existingPrice == ((Number) item.getListPrice().get(0).get("price")).floatValue()) {
                    // Nếu trùng, tăng quantity lên 1
                    existingItem.setQuantity(existingItem.getQuantity() + 1);
                    // Cập nhật giá tổng của existingItem
                    existingItem.setPrice(existingItem.getPrice() + existingPrice);
                    return; // Kết thúc hàm sau khi đã xử lý xong
                }
            }

            // Nếu không tìm thấy giá trong danh sách listPrice, thêm item mới vào giỏ hàng
            listItems.add(item);
            return; // Kết thúc hàm sau khi thêm
        }
    }

    // Nếu item chưa tồn tại trong giỏ hàng, thêm mới
    if (!itemExists) {
        listItems.add(item);
    }
}
   
   public void consolidateCart() {
    List<Item> consolidatedItems = new ArrayList<>(); // Danh sách mới để lưu các mục đã gộp

    for (Item item : listItems) {
        boolean found = false; // Biến kiểm tra xem đã tìm thấy item trong danh sách gộp chưa

        for (Item consolidatedItem : consolidatedItems) {
            // Kiểm tra nếu sản phẩm đã tồn tại trong danh sách gộp
            if (item.getProductID() == consolidatedItem.getProductID() &&
                item.getListPrice().equals(consolidatedItem.getListPrice())) { // So sánh danh sách giá
                // Nếu trùng, tăng quantity và cập nhật price
                consolidatedItem.setQuantity(consolidatedItem.getQuantity() + item.getQuantity());
                consolidatedItem.setPrice(consolidatedItem.getPrice() + item.getPrice());
                found = true; // Đánh dấu là đã tìm thấy
                break; // Thoát khỏi vòng lặp vì đã xử lý xong
            }
        }

        // Nếu chưa tìm thấy, thêm item vào danh sách gộp
        if (!found) {
            consolidatedItems.add(item);
        }
    }

    // Cập nhật lại danh sách gốc với danh sách đã gộp
    listItems = consolidatedItems;
}

    
// Phương thức để in ra các Item trong Cart
    public void printItems() {
        if (listItems.isEmpty()) {
            System.out.println("Giỏ hàng đang trống.");
            return;
        }
        System.out.println("Các sản phẩm trong giỏ hàng:");
        for (Item item : listItems) {
            System.out.println(item);
        }
    }    
    public int coutn(){
        int n=0;
        for (Item item : listItems) {
            n+=1;
        }
        return n;
    }
//    public void removeItem(int id){
//        if (getItemById(id)!= null) {
//            listItems.remove(getItemById(id));
//        }  
//    }
    
//    public float getTotalMoney(){
//        float total = 0;
//        for (Item item : listItems) {
//            total += (item.getQuantity() * item.getProduct().);  
//        }
//        return total;
//    }
//    
    
    
}
