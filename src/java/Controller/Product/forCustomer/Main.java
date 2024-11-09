///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package Controller.Product.forCustomer;
//
//import DAL.OrderDao;
//import DAL.UserDao;
//import Model.Order.Order;
//import Model.Order.Status;
//import Model.Product.Component;
//import Model.Product.Product;
//import Model.Product.ProductDetail;
//import Model.Product.ProductUnit;
//import Model.User.User;
//import java.math.BigDecimal;
//import java.security.Timestamp;
//import java.util.ArrayList;
//import java.util.Comparator;
//
//import java.util.List;
//import java.sql.Date;
//
///**
// *
// * @author Dell
// */
//
//public class Main {
//
//    public static void main(String[] args) throws Exception {
//         
//           
//           List<Item> listItems = CartControl.displayProductList2(2);
//             for (Item item : listItems) {
//                    System.out.println(item.toString()); // In thông tin của từng item
//             }
//            List<Item> mylist =  
//    }  
//    
//     private static List<Item> getItemsByIndex(int index) {
//        //List<Item> listItems = CartControl.displayProductList2();
//
//        // Chia danh sách thành các nhóm 12 item
//        List<List<Item>> groupedItems = CartControl.splitItemsIntoGroups(listItems, 12);
//        // size
//        int groupCount = groupedItems.size();
//        List<Item> itemsAtIndex1 = groupedItems.get(index); 
//        
//        return itemsAtIndex1;
//    }
//        
//}
//
