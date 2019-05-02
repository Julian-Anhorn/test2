package de.hdm.softwarePraktikum.shared.bo;


import java.util.*;


public class ContactList extends SharedBusinessObject {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * FremdschlÃ¼sselbeziehung zum Inhaber des Kontos.
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
     * Gibt Titel der Namensliste zurück
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
     * Gibt Kontaktliste zurück
     */
    public ArrayList<Contact> getContacts() {
           return contacts;
    }

    /**
     * Fügt den Kontakt der Liste hinzu
     */
    public void addContact(Contact c) {
    	contacts.add(c);
    }
    
    /**
     * Fügt alle Kontakte der Liste hinzu
     */
    public void addContacts(ArrayList<Contact> contacts) {
    	this.contacts = contacts;
    }

}