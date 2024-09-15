/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Product;

import java.util.Objects;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Origin {
    int originID;
    String originName;

    public Origin() {
    }

    public Origin(int originID, String originName) {
        this.originID = originID;
        this.originName = originName;
    }
    
    private static final Logger LOG = Logger.getLogger(Origin.class.getName());

    public int getOriginID() {
        return originID;
    }

    public void setOriginID(int originID) {
        this.originID = originID;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.originID;
        hash = 97 * hash + Objects.hashCode(this.originName);
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
        final Origin other = (Origin) obj;
        if (this.originID != other.originID) {
            return false;
        }
        return Objects.equals(this.originName, other.originName);
    }

    @Override
    public String toString() {
        return "Origin{" + "originID=" + originID + ", originName=" + originName + '}';
    }
       
}
