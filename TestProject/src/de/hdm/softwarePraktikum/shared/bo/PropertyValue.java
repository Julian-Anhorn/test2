package de.hdm.softwarePraktikum.shared.bo;

import java.util.*;

/**
 * 
 */

public class PropertyValue extends SharedBusinessObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    private String value;

    private Property property;

    /**
     * Eigenschaftsausprägung ausgeben lassen
     */
    
    public String getValue() {
        return value;
    }

    /**
     * Inhalt einer Eingeschaftsausprägung setzen
     * @param value 
     */
    
    public void setValue(String value) {
    	this.value = value;
    }

    /**
     * Ausgeben einer Eigenschaft
     */
    
    public Property getProperty() {
        return property;
    }

    /**
     * Setzen einer Eigenschaft
     * @param p 
     */
    
    public void setProperty(Property p) {
    	this.property = p;
    }

}