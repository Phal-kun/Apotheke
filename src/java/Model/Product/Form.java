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
public class Form {
    private int formID;
    private String formName;

    public Form() {
    }
    
    public Form(int formID, String formName) {
        this.formID = formID;
        this.formName = formName;
    }
    
    private static final Logger LOG = Logger.getLogger(Form.class.getName());

    public int getFormID() {
        return formID;
    }

    public void setFormID(int formID) {
        this.formID = formID;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.formID;
        hash = 37 * hash + Objects.hashCode(this.formName);
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
        final Form other = (Form) obj;
        if (this.formID != other.formID) {
            return false;
        }
        return Objects.equals(this.formName, other.formName);
    }

    @Override
    public String toString() {
        return "Form{" + "formID=" + formID + ", formName=" + formName + '}';
    }
    
    
}
