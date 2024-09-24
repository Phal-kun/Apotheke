/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.User;

import java.util.Objects;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class User {
    private int userID;
    private String fullname;
    private String phone;
    private String username;
    private String password;
    private String gender;
    private boolean status;
    private Role role;
    private String address;

    public User() {
    }

    public User(String fullname, String phone, String username, String password, String gender, boolean status, Role role, String address) {
        this.fullname = fullname;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.status = status;
        this.role = role;
        this.address = address;
    }

    public User(int userID, String fullname, String phone, String username, String password, String gender, boolean status, Role role, String address) {
        this.userID = userID;
        this.fullname = fullname;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.status = status;
        this.role = role;
        this.address = address;
    }
    
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.userID;
        hash = 97 * hash + Objects.hashCode(this.fullname);
        hash = 97 * hash + Objects.hashCode(this.phone);
        hash = 97 * hash + Objects.hashCode(this.username);
        hash = 97 * hash + Objects.hashCode(this.password);
        hash = 97 * hash + Objects.hashCode(this.gender);
        hash = 97 * hash + (this.status ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.role);
        hash = 97 * hash + Objects.hashCode(this.address);
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
        final User other = (User) obj;
        if (this.userID != other.userID) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.fullname, other.fullname)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.gender, other.gender)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return Objects.equals(this.role, other.role);
    }

    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", fullname=" + fullname + ", phone=" + phone + ", username=" + username + ", password=" + password + ", gender=" + gender + ", status=" + status + ", role=" + role + ", address=" + address + '}';
    }
       
}
