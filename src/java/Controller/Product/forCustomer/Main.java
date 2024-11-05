/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Product.forCustomer;

import DAL.UserDao;
import Model.Product.Component;
import Model.Product.Product;
import Model.Product.ProductDetail;
import Model.Product.ProductUnit;
import Model.User.User;
import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;

/**
 *
 * @author Dell
 */

public class Main {
    public static void main(String[] args) {

        try {
            // Tạo đối tượng DAO
            showProductDAO productDAO = new showProductDAO();
            
            // Gọi phương thức list để lấy danh sách sản phẩm
            List<Product> products = productDAO.list();
            List<ProductUnit> productUnits = productDAO.myListProductUnit(1);
            // Kiểm tra nếu danh sách không rỗng, thì in ra thông tin sản phẩm
            if (products != null && !products.isEmpty()) {
                System.out.println("Danh sách sản phẩm:");
                for (Product product : products) {
                    System.out.println(product.toString());
                }
            } else {
                System.out.println("Không có sản phẩm nào trong cơ sở dữ liệu.");
            }
//            if (productUnits != null && !productUnits.isEmpty()) {
//                System.out.println("Danh sách các base unit ");
//                for (ProductUnit productUnit : productUnits) {
//                    System.out.println(productUnits.toString());
//                }
//            } else {
//                System.out.println("Không có sản phẩm nào trong cơ sở dữ liệu.");
//            }
                System.out.println(productDAO.getProductDetailByUnitId(4).toString());
                
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }    
                public static void processString(String input) {
                // Tách chuỗi thành 2 phần bằng dấu "/"
                String[] parts = input.split(" / ", 2);

                // Lấy phần trước dấu "/" và loại bỏ "Product Price: "
                String firstPart = parts[0].replace("Product Price: ", "").trim();

                // Tìm vị trí của dấu "?" và lấy phần trước nó
                int questionMarkIndex = firstPart.indexOf("?");
                if (questionMarkIndex != -1) {
                    firstPart = firstPart.substring(0, questionMarkIndex).trim();
                }

                // Phần thứ hai là chuỗi sau dấu "/"
                String secondPart = parts.length > 1 ? parts[1].trim() : "";

                // In ra kết quả
                System.out.println("Chuỗi đầu tiên: " + firstPart);
                System.out.println("Chuỗi thứ hai: " + secondPart);
            }

}
//        try {
//            // Tạo đối tượng DAO
//            showProductDAO productDAO = new showProductDAO();
//            // Gọi phương thức list để lấy danh sách sản phẩm
//            List<Product> listProducts = productDAO.list();
//            // list item de hien thi len web 
//            List<Item> listItems = new ArrayList<>();
//
//            if (listProducts != null && !listProducts.isEmpty()) {
//                System.out.println("Danh sách sản phẩm:");
//                for (Product product : listProducts) {
//                    
//                    // list product Unit
//                    List<ProductUnit> productUnits = productDAO.myListProductUnit(product.getProductID());
//                    productUnits.sort(Comparator.comparingDouble(ProductUnit::getUnitToBaseConvertRate));
//
//                    // get name price for list item 
//                    Item newItem = new Item();
//                    // product detail   lay gia 
//                    
//                    // product unit nho nhat
//                    if (productUnits != null && !productUnits.isEmpty()) {
//                        ProductUnit baseUnit = productUnits.get(0);
//                        ProductDetail newproductDetail = productDAO.getProductDetailByUnitId(baseUnit.getProductUnitID());
//                        String nameShow = "";
//
//                        // Thêm giá vào item
//                        for (ProductUnit productUnit : productUnits) {
//                            newItem.addPrice(productUnit.getProductUnitName(),newproductDetail.getPrice()*( productUnit.getUnitToBaseConvertRate() / baseUnit.getUnitToBaseConvertRate()));
//                        }
//
//                        // Duyệt từ cuối đến đầu để xây dựng nameShow
//                        for (int i = productUnits.size() - 1; i >= 0; i--) {
//                            if (i == 0) {
//                                nameShow += productUnits.get(i).getProductUnitName();
//                            } else {
//                                nameShow += productUnits.get(i).getProductUnitName() + " ";
//                                nameShow += productUnits.get(i).getUnitToBaseConvertRate() / productUnits.get(i - 1).getUnitToBaseConvertRate() + " ";
//                            }
//                        }
//                      
//                        // add infor for item 
//                        newItem.setProductID(product.getProductID());
//                        newItem.setProductName(product.getProductName());
//                        newItem.setDescription(product.getDescription());
//                        newItem.setNameShow(nameShow);
//
//                        // Thêm newItem vào danh sách items
//                        listItems.add(newItem);
//                    } else {
//                        System.out.println("Không có sản phẩm đơn vị nào.");
//                    }
//                }
//            } else {
//                System.out.println("Không có sản phẩm nào trong cơ sở dữ liệu.");
//            }
//
//            // In thông tin danh sách items
////            for (Item item : listItems) {
////                System.out.println(item.toString());
////            }
//            
//             // Chia danh sách thành các nhóm 12 item
//            List<List<Item>> groupedItems = CartControl.splitItemsIntoGroups(listItems, 12);
//            // size
//            int groupCount = groupedItems.size();
//           // Lấy danh sách items cho nhóm tương ứng
//           List<Item> itemsAtIndex1 = groupedItems.get(2); // Lấy nhóm có chỉ số 1 (tức là nhóm thứ 2)
//
//            // In ra danh sách items trong nhóm với index = 1
//            for (Item item : itemsAtIndex1) {
//                System.out.println(item.toString()); // In thông tin của từng item
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    

