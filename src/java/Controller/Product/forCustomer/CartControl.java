/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Product.forCustomer;

import Model.Product.Product;
import Model.Product.ProductDetail;
import Model.Product.ProductUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Dell
 */
public class CartControl {
    // list item 
     public static List<Item> displayProductList() {
        List<Item> listItems = new ArrayList<>();
        try {
            // Tạo đối tượng DAO
            showProductDAO productDAO = new showProductDAO();
            // Gọi phương thức list để lấy danh sách sản phẩm
            List<Product> listProducts = productDAO.list();

            if (listProducts != null && !listProducts.isEmpty()) {
                System.out.println("Danh sách sản phẩm:");
                for (Product product : listProducts) {
                    
                    // list product Unit
                    List<ProductUnit> productUnits = productDAO.myListProductUnit(product.getProductID());
                    productUnits.sort(Comparator.comparingDouble(ProductUnit::getUnitToBaseConvertRate));

                    // get name price for list item 
                    Item newItem = new Item();
                    
                    // product unit nho nhat
                    if (productUnits != null && !productUnits.isEmpty()) {
                        ProductUnit baseUnit = productUnits.get(0);
                        ProductDetail newproductDetail = productDAO.getProductDetailByUnitId(baseUnit.getProductUnitID());
                        String nameShow = "";

                        // Thêm giá vào item
                        for (ProductUnit productUnit : productUnits) {
                            newItem.addPrice(productUnit.getProductUnitName(), newproductDetail.getPrice() * (productUnit.getUnitToBaseConvertRate() / baseUnit.getUnitToBaseConvertRate()));
                        }

                        // Duyệt từ cuối đến đầu để xây dựng nameShow
                        for (int i = productUnits.size() - 1; i >= 0; i--) {
                            if (i == 0) {
                                nameShow += productUnits.get(i).getProductUnitName();
                            } else {
                                nameShow += productUnits.get(i).getProductUnitName() + " ";
                                nameShow += productUnits.get(i).getUnitToBaseConvertRate() / productUnits.get(i - 1).getUnitToBaseConvertRate() + " ";
                            }
                        }
                      
                        // add infor for item 
                        newItem.setProductID(product.getProductID());
                        newItem.setProductName(product.getProductName());
                        newItem.setDescription(product.getDescription());
                        newItem.setNameShow(nameShow);

                        // Thêm newItem vào danh sách items
                        listItems.add(newItem);
                    } else {
                        System.out.println("Không có sản phẩm đơn vị nào.");
                    }
                }
            } else {
                System.out.println("Không có sản phẩm nào trong cơ sở dữ liệu.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listItems; // Trả về danh sách Item
    }
     
    // phan trang 12 sản phẩm trên 1 trang 
     
     public static List<List<Item>> splitItemsIntoGroups(List<Item> items, int groupSize) {
        List<List<Item>> groupedItems = new ArrayList<>();

        for (int i = 0; i < items.size(); i += groupSize) {
            // Tính toán chỉ số kết thúc cho nhóm hiện tại
            int end = Math.min(i + groupSize, items.size());
            // Lấy ra danh sách con từ chỉ số i đến end
            List<Item> group = new ArrayList<>(items.subList(i, end));
            groupedItems.add(group); // Thêm nhóm vào danh sách kết quả
        }

        return groupedItems; // Trả về danh sách các nhóm
    } 
}
