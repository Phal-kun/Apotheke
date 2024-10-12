/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Order;

import Model.Product.Product;
import Model.Product.ProductDetail;
import Model.Product.ProductUnit;

/**
 *
 * @author ASUS
 */
public class OrderDetail {
    int orderDetailID;
    Product product;
    ProductDetail productDetail;
    ProductUnit unit;
    double soldPrice;
    int quantity;
    double totalProductPrice;
}
