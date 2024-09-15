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
public class Component {
    private int originID;
    private String originName;

    public Component() {
    }

    public Component(int originID, String originName) {
        this.originID = originID;
        this.originName = originName;
    }
    private static final Logger LOG = Logger.getLogger(Component.class.getName());

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
        int hash = 7;
        hash = 29 * hash + this.originID;
        hash = 29 * hash + Objects.hashCode(this.originName);
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
        final Component other = (Component) obj;
        if (this.originID != other.originID) {
            return false;
        }
        return Objects.equals(this.originName, other.originName);
    }

    @Override
    public String toString() {
        return "Component{" + "originID=" + originID + ", originName=" + originName + '}';
    }
    
    
}
