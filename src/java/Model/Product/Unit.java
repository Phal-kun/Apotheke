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
public class Unit {
    int unitID;
    String unitName;
    int convertToBaseRate;

    public Unit() {
    }

    public Unit(int unit, String productUnitName, int convertToBaseRate) {
        this.unitID = unit;
        this.unitName = productUnitName;
        this.convertToBaseRate = convertToBaseRate;
    }
    
    private static final Logger LOG = Logger.getLogger(Unit.class.getName());

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.unitID;
        hash = 83 * hash + Objects.hashCode(this.unitName);
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
        final Unit other = (Unit) obj;
        if (this.unitID != other.unitID) {
            return false;
        }
        return Objects.equals(this.unitName, other.unitName);
    }

    @Override
    public String toString() {
        return "ProductUnit{" + "productUnitID=" + unitID + ", productUnitName=" + unitName + '}';
    }
       
}
