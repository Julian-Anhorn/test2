package de.hdm.softwarePraktikum.shared.bo;


import java.util.*;


public class ContactList extends SharedBusinessObject {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Fremdschlüsselbeziehung zum Inhaber des Kontos.
     */
    private String title;

    /**
     * 
     */
    private ArrayList<Contact> contacts;

    /**
     * 
     */
    public Set<Contact> Contact;

    /**
     * Gibt Titel der Namensliste zur�ck
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setzt den Titel der Kontaktliste  
     */ 
    public void setTitle(String value) {
    	this.title = value;
    }

    /**
     * Gibt Kontaktliste zur�ck
     */
    public ArrayList<Contact> getContacts() {
           return contacts;
    }

    /**
     * F�gt den Kontakt der Liste hinzu
     */
    public void addContact(Contact c) {
    	contacts.add(c);
    }
    
    /**
     * F�gt alle Kontakte der Liste hinzu
     */
    public void addContacts(ArrayList<Contact> contacts) {
    	this.contacts = contacts;
    }

}