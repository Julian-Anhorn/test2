package de.hdm.softwarePraktikum.shared.bo;

import java.util.*;

/**
 * 
 */
public class Participation <T> extends BusinessObject {

    private static long serialVersionUID = 1L;

    private T sbo;

    public Contact user;

    /**
     * Auslesen des Users
     */
    
    public Contact getUser() {
        return user;
    }

    /**
     * Setzen des Users
     * @param c 
     */
    
    public void setUser(Contact c) {
    	this.user = c;
    }

    /**
     * 
     */
    
    public T getSbo() {
        return sbo;
    }

    /**
     * 
     * @param sbog 
     */
    
    public void setSbo(T sbog) {
    	this.sbo = sbog;
    }

}