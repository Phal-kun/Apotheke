/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Order;

import Model.User.User;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author ASUS
 */
public class Order {
    private int orderID;
    private User user;
    private Date orderDate, orderCompleted;
    private ArrayList<OrderDetail> orderDetail; 
    private Status status;
    private double totalPrice;
    private String shipName, shipAddress, shipPhone, shipNote, rejectReason;

    public Order() {
    }

    public Order(int orderID) {
        this.orderID = orderID;
    }
    

    public Order(int orderID, User user, Date orderDate, Date orderCompleted, ArrayList<OrderDetail> orderDetail, Status status, double totalPrice, String shipName, String shipAddress, String shipPhone, String shipNote, String rejectReason) {
        this.orderID = orderID;
        this.user = user;
        this.orderDate = orderDate;
        this.orderCompleted = orderCompleted;
        this.orderDetail = orderDetail;
        this.status = status;
        this.totalPrice = totalPrice;
        this.shipName = shipName;
        this.shipAddress = shipAddress;
        this.shipPhone = shipPhone;
        this.shipNote = shipNote;
        this.rejectReason = rejectReason;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getOrderCompleted() {
        return orderCompleted;
    }

    public void setOrderCompleted(Date orderCompleted) {
        this.orderCompleted = orderCompleted;
    }

    public ArrayList<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(ArrayList<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShipPhone() {
        return shipPhone;
    }

    public void setShipPhone(String shipPhone) {
        this.shipPhone = shipPhone;
    }

    public String getShipNote() {
        return shipNote;
    }

    public void setShipNote(String shipNote) {
        this.shipNote = shipNote;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.orderID;
        hash = 29 * hash + Objects.hashCode(this.user);
        hash = 29 * hash + Objects.hashCode(this.orderDate);
        hash = 29 * hash + Objects.hashCode(this.orderCompleted);
        hash = 29 * hash + Objects.hashCode(this.orderDetail);
        hash = 29 * hash + Objects.hashCode(this.status);
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.totalPrice) ^ (Double.doubleToLongBits(this.totalPrice) >>> 32));
        hash = 29 * hash + Objects.hashCode(this.shipName);
        hash = 29 * hash + Objects.hashCode(this.shipAddress);
        hash = 29 * hash + Objects.hashCode(this.shipPhone);
        hash = 29 * hash + Objects.hashCode(this.shipNote);
        hash = 29 * hash + Objects.hashCode(this.rejectReason);
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
        final Order other = (Order) obj;
        if (this.orderID != other.orderID) {
            return false;
        }
        if (Double.doubleToLongBits(this.totalPrice) != Double.doubleToLongBits(other.totalPrice)) {
            return false;
        }
        if (!Objects.equals(this.shipName, other.shipName)) {
            return false;
        }
        if (!Objects.equals(this.shipAddress, other.shipAddress)) {
            return false;
        }
        if (!Objects.equals(this.shipPhone, other.shipPhone)) {
            return false;
        }
        if (!Objects.equals(this.shipNote, other.shipNote)) {
            return false;
        }
        if (!Objects.equals(this.rejectReason, other.rejectReason)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.orderDate, other.orderDate)) {
            return false;
        }
        if (!Objects.equals(this.orderCompleted, other.orderCompleted)) {
            return false;
        }
        if (!Objects.equals(this.orderDetail, other.orderDetail)) {
            return false;
        }
        return Objects.equals(this.status, other.status);
    }

    @Override
    public String toString() {
        return "Order{" + "orderID=" + orderID + ", user=" + user + ", orderDate=" + orderDate + ", orderCompleted=" + orderCompleted + ", orderDetail=" + orderDetail + ", status=" + status + ", totalPrice=" + totalPrice + ", shipName=" + shipName + ", shipAddress=" + shipAddress + ", shipPhone=" + shipPhone + ", shipNote=" + shipNote + ", rejectReason=" + rejectReason + '}';
    }
    
    
}
