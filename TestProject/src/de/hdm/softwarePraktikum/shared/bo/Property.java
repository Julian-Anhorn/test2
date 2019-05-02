package de.hdm.softwarePraktikum.shared.bo;

import java.util.*;

/**
 * 
 */

public class Property extends BusinessObject {

    private static final long serialVersionUID = 1L;

    private String term;

    /**
     * Auslesen der Eigenschaft
     */
    
    public String getTerm() {
        return term;
    }

    /**
     * Setzen einer Eigenschaft
     * @param term 
     */
    public void setTerm(String term) {
    	this.term = term;
    }

}