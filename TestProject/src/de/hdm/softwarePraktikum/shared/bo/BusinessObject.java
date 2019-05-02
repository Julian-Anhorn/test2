package de.hdm.softwarePraktikum.shared.bo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;


/**
 *Die Klasse <code>BusinessObject</code> stellt die Basisklasse aller in diesem
 *Projekt für die Umsetzung des Fachkonzepts relevanten Klassen dar.
 * 
 *@author Tobias Moser
 */

public abstract class BusinessObject implements Serializable {
	
 	
    private static long serialVersionUID = 1L;

    /**
     * Auslesen der SerialVersionUID
     */
    
    public long getSerialVersionUID() {
        return serialVersionUID;
    }	

	/**
	 * Eindeutige ID (Identifikationsnummer) einer Instanz dieser Klasse
	 */
	
	 private int BO_ID = 0;
	 
	/**
	  * Auslesen der BO_ID
	  */
	 
	 public int getBO_ID() {
		 return BO_ID;
	    }
	 
	/**
	  * Setzen der BO_ID
	  * @param BO_ID
	  */
	 
	 public void setBO_ID(int BO_ID) {
	      this.BO_ID = BO_ID;
	 }

    /**
     * Einfache textuelle Darstellung einer Instanz dieser Klasse
     */
	 
    public String toString() {
        return this.getClass().getName() + " #" + this.BO_ID;
    }

    /**
     * Feststellen der inhaltlichen Gleichheit zweier <code>BusinessObject</code>-Objekte
     */
    
    public boolean equals(Object obj) {
    	if (obj != null && obj instanceof BusinessObject) {
			BusinessObject bo = (BusinessObject) obj;
			
			try {
				
				if (bo.getBO_ID() == this.BO_ID) {
					return true;
				}
			} catch (IllegalArgumentException e) {
				
				return false;
			}
		}
		
		return false;
    }

    /**
     * Erzeugen einer ganzen Zahl, welche für das <code>BusinessObject</code> charakteristisch ist
     */
    
    public int hashCode() {
        return this.BO_ID;
    }
    
    /**
     * Erstellungsdatum für <code>BusinessObjekt</code> Objekte
     */
    
    private Timestamp creationDate;

    /**
     * Auslesen des Erstellungsdatums
     */
    
    public Timestamp getCreationDate() {
        return creationDate;
    }

    /**
     * Setzen des Erstellungsdatums
     * @param cd 
     */
    
    public void setCreationDate(Timestamp cd) {
    	this.creationDate = cd;  
    }

}