/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Product.forCustomer;

import Model.Product.Product;
import Model.Product.ProductUnit;
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
}
