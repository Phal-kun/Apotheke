/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Order;

import Model.User.User;
import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class Order {
    int orderID;
    User user;
    Date orderDate, orderCompleted;
    Status status;
    double totalPrice;
    String shipName, shipAddress, shipPhone, shipNote, rejectReason;
    
}
