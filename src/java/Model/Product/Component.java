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
    private int componentID;
    private String componentName;
    private String componentMeasureUnit;
    private int quantity;

    public Component() {
    }
    //component without quantity for component list
    public Component(int componentID, String componentName, String componentMeasureUnit) {
        this.componentID = componentID;
        this.componentName = componentName;
        this.componentMeasureUnit = componentMeasureUnit;
    }
    //component with quantity for product component

    public Component(int componentID, String componentName, String componentMeasureUnit, int quantity) {
        this.componentID = componentID;
        this.componentName = componentName;
        this.componentMeasureUnit = componentMeasureUnit;
        this.quantity = quantity;
    }

    private static final Logger LOG = Logger.getLogger(Component.class.getName());

    public int getComponentID() {
        return componentID;
    }

    public void setComponentID(int componentID) {
        this.componentID = componentID;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentMeasureUnit() {
        return componentMeasureUnit;
    }

    public void setComponentMeasureUnit(String componentMeasureUnit) {
        this.componentMeasureUnit = componentMeasureUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.componentID;
        hash = 29 * hash + Objects.hashCode(this.componentName);
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
        if (this.componentID != other.componentID) {
            return false;
        }
        return Objects.equals(this.componentName, other.componentName);
    }

    @Override
    public String toString() {
        return "Component{" + "componentID=" + componentID + ", componentName=" + componentName + ", componentMeasureUnit=" + componentMeasureUnit + ", quantity=" + quantity + '}';
    }

}
