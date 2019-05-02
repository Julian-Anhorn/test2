package de.hdm.softwarePraktikum.shared.bo;

import java.sql.Timestamp;
import java.util.*;

/**
 * Es wird eine Unterscheidung zwischen der Klasse BusinessObject und SharedBusinessObject vorgenommen, 
wobei letztere Klasse eine Kind Klasse von BusinessObject darstellt. SharedBusinessObject ist wiederrum die 
Elternklasse von allen Klassen der später teilbaren Objekte, d.h. Contact, Contactlist und PropertyValue. 
Ein Grund für diese Unterscheidung ist, dass auch die Klasse Participation und die Klasse Property von
einer übergeordneten BusinessObjekt Klasse abgeleitet werden sollen. Trotzdem soll eine Unterscheidung zwischen 
teilbaren und nicht teilbaren Objekten stattfinden.Die Teilhaberschaft bezieht sich direkt auf die jeweiligen Klassen 
ContactList, Contact und PropertyValue. Das Attribut „sbo“ referenziert das jeweilige SharedBusinessObject und
ist wiederum von einem begrenzten Typparameter. Die Einschränkung findet dabei auf den Typ
SharedBusinessObject und damit auch deren Subklassen statt.
@author Tobias Moser
 */


public abstract class SharedBusinessObject extends BusinessObject {

    private static final long serialVersionUID = 1L;

    private Timestamp creationDate;

	private Timestamp changeDate;

    private User owner;

    
    /**
     * Auslesen des Erstellungsdatums
     */
    
    public Timestamp getCreationDate() {
		return creationDate;
	}
    
    /**
     * Setzen des Erstellungsdatums
     * @param creationDate
     */

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate=creationDate;

	}

    /**
     * Auslesen des Änderungsdatums
     */
    
    public Timestamp getChangeDate() {
        return changeDate;
    }

    /**
     * Setzen des Änderungsdatums
     * @param changeDate 
     */
    
    public void setChangeDate(Timestamp changeDate) {
      this.changeDate = changeDate;
    }

    /**
     * Auslesen des Owners
     */
    
    public User getOwner() {
        return owner;
    }

    /**
     * Setzen des Owners
     * @param user 
     */
    
    public void setOwner(User user) {
        this.owner = user;
    }

 

}