/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Product.forCustomer;

import Model.Product.Component;
import Model.Product.Product;
import Model.Product.ProductUnit;
import java.util.ArrayList;
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
          ArrayList<Component> allComponents = new ArrayList<>();
            if (products != null && !products.isEmpty()) {
                System.out.println("Danh sách sản phẩm:");
                for (Product product : products) {
                     ArrayList<Component> components = product.getComponent();
                    if (components != null && !components.isEmpty()) {
                System.out.println("Component đã được tìm thấy cho sản phẩm: " + product.getProductName());
                // Thêm tất cả các components của sản phẩm vào danh sách allComponents
                allComponents.addAll(components);
    } else {
        System.out.println("Không có component cho sản phẩm: " + product.getProductName());
    }
                }
            } else {
                System.out.println("Không có sản phẩm nào trong cơ sở dữ liệu.");
            }
            
            for (Component component : allComponents) {
                System.out.println("Component Name: " + component.getComponentName());
                System.out.println("Measure Unit: " + component.getComponentMeasureUnit());
                System.out.println("Quantity: " + component.getQuantity());
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
