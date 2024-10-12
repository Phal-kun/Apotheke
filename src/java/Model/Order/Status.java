/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Order;

import java.util.Objects;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Status {
    private int statusID;
    private String statusName, description;

    public Status() {
    }
    private static final Logger LOG = Logger.getLogger(Status.class.getName());

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Logger getLOG() {
        return LOG;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.statusID;
        hash = 47 * hash + Objects.hashCode(this.statusName);
        hash = 47 * hash + Objects.hashCode(this.description);
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
        final Status other = (Status) obj;
        if (this.statusID != other.statusID) {
            return false;
        }
        if (!Objects.equals(this.statusName, other.statusName)) {
            return false;
        }
        return Objects.equals(this.description, other.description);
    }

    @Override
    public String toString() {
        return "Status{" + "statusID=" + statusID + ", statusName=" + statusName + ", description=" + description + '}';
    }
    
    
}
