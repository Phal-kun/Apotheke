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
    int unit;
    String unitName;
    int baseUnitID;
    int convertToBaseRate;

    public Unit() {
    }

    public Unit(int unit, String productUnitName, int baseUnitID, int convertToBaseRate) {
        this.unit = unit;
        this.unitName = productUnitName;
        this.baseUnitID = baseUnitID;
        this.convertToBaseRate = convertToBaseRate;
    }
    
    private static final Logger LOG = Logger.getLogger(Unit.class.getName());

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
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
        hash = 83 * hash + this.unit;
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
        if (this.unit != other.unit) {
            return false;
        }
        return Objects.equals(this.unitName, other.unitName);
    }

    @Override
    public String toString() {
        return "ProductUnit{" + "productUnitID=" + unit + ", productUnitName=" + unitName + '}';
    }
       
}
