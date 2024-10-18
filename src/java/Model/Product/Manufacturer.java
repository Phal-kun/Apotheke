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
public class Manufacturer {
    private int manufacturerID;
    private String manufacturerName;

    public Manufacturer() {
    }

    public Manufacturer(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public Manufacturer(int manufacturerID, String manufacturerName) {
        this.manufacturerID = manufacturerID;
        this.manufacturerName = manufacturerName;
    }
    private static final Logger LOG = Logger.getLogger(Manufacturer.class.getName());

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.manufacturerID;
        hash = 59 * hash + Objects.hashCode(this.manufacturerName);
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
        final Manufacturer other = (Manufacturer) obj;
        if (this.manufacturerID != other.manufacturerID) {
            return false;
        }
        return Objects.equals(this.manufacturerName, other.manufacturerName);
    }

    @Override
    public String toString() {
        return "Manufacturer{" + "manufacturerID=" + manufacturerID + ", manufacturerName=" + manufacturerName + '}';
    }
    
    
}

