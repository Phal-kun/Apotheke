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
    private String componentName;
    private String componentMeasureUnit;
    private int quantity;

    public Component() {
    }
 
    public Component(String componentName, String componentMeasureUnit, int quantity) {
        this.componentName = componentName;
        this.componentMeasureUnit = componentMeasureUnit;
        this.quantity = quantity;
    }

 

    private static final Logger LOG = Logger.getLogger(Component.class.getName());

  

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

        return Objects.equals(this.componentName, other.componentName);
    }

    @Override
    public String toString() {
        return "Component{componentName=" + componentName + ", componentMeasureUnit=" + componentMeasureUnit + ", quantity=" + quantity + '}';
    }

}
