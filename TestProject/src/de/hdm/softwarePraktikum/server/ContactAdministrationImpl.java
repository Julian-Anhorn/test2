//package de.hdm.softwarePraktikum.server;
//
//import java.sql.Timestamp;
//import java.util.*;
//
//
//
//import de.hdm.softwarePraktikum.server.db.ContactListMapper;
//import de.hdm.softwarePraktikum.server.db.ContactMapper;
//import de.hdm.softwarePraktikum.server.db.ParticipationMapper;
//import de.hdm.softwarePraktikum.server.db.PropertyMapper;
//import de.hdm.softwarePraktikum.server.db.PropertyValueMapper;
//
//import de.hdm.softwarePraktikum.shared.bo.Contact;
//import de.hdm.softwarePraktikum.shared.bo.ContactList;
//import de.hdm.softwarePraktikum.shared.bo.Participation;
//import de.hdm.softwarePraktikum.shared.bo.Property;
//import de.hdm.softwarePraktikum.shared.bo.PropertyValue;
//import de.hdm.softwarePraktikum.shared.bo.SharedBusinessObject;
//
///**
// * Die Klasse <code>ContactAdministrationImpl</code> implementiert das Interface
// * ContactAdministation. In der Klasse ist neben ReportGeneratorImpl sämtliche
// * Applikationslogik vorhanden.
// * 
// * @author Tobias Moser
// */
//
//public class ContactAdministrationImpl  {
//
//    /**
//     * Referenz auf den EigenschaftsausprägungsMapper, welcher Eigenschaftsausprägungs
//     * Objekte mit der Datenbank abgleicht.
//     */
//    private PropertyValueMapper propertyValueMapper = null;
//
//    /**
//     * Referenz auf den KontaktMapper, welcher Kontakt
//     * Objekte mit der Datenbank abgleicht.
//     */
//    private ContactMapper contactMapper = null;
//
//    /**
//     * Referenz auf den TeilhaberschaftsMapper, welcher Teilhaberschafts
//     * Objekte mit der Datenbank abgleicht.
//     */
//    private ParticipationMapper participationMapper = null;
//
//    /**
//     * Referenz auf den KontaktlistenMapper, welcher Kontaktlisten
//     * Objekte mit der Datenbank abgleicht.
//     */
//    private ContactListMapper contactListMapper = null;
//
//    /**
//     * Referenz auf den EigenschaftMapper, welcher Eigenschaft
//     * Objekte mit der Datenbank abgleicht.
//     */
//    private PropertyMapper propertyMapper = null;
//
//    /**
//     * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
//     * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
//     * ist ein solcher No-Argument-Konstruktor anzulegen.
//     * 
//     * Es bietet sich also an, eine separate Instanzenmethode zu erstellen, die
//     * Client-seitig direkt nach <code>GWT.create(Klassenname.class)</code>
//     * aufgerufen wird, um eine Initialisierung der Instanz vorzunehmen.
//     * @see #init()
//     * @throws IllegalArgumentException
//     */
//    
//    public ContactAdministrationImpl() throws IllegalArgumentException{
//    }
//
//    /**
//     * Initialisiert alle Mapper in der Klasse.
//     */
//    public void init() throws IllegalArgumentException {
//    	
//   	/**
//	 * Um mit der Datenbank kommunizieren zu können benötigt die Klasse ProjektmarktplatzVerwaltung
//	 * einen vollständigen Satz von Mappern.
//	 */
//       
//    	this.propertyValueMapper = PropertyValueMapper.propertyValueMapper();
//    	this.contactMapper = ContactMapper.contactMapper();
//    	this.participationMapper = ParticipationMapper.participationMapper();
//    	this.contactListMapper = ContactListMapper.contactListMapper();
//    	this.propertyMapper = PropertyMapper.propertyMapper();
//    }
//
//    /**
//     * Anlegen eines Eigenschaftsausprägungsobjekts
//     * @param owner, c, value, p
//     * @return Das Eigenschaftsausprägungsobjekt wird in die Datenbank geschrieben
//     */
//    public PropertyValue createPropertyValue(Contact owner, Contact c, String value, Property p) 
//    	throws IllegalArgumentException {
//    	
//    	PropertyValue pv = new PropertyValue();
//    	pv.setOwner(owner);
//    	pv.setValue(value);
//    	pv.setProperty(p);
//    	
//    	/**
//    	 * Setzen einer vorläufigen ID. Der Insert Aufruf liefert dann ein Objekt,
//    	 * dessen Nummer mit der Datenbank konsistent ist.
//    	 */
//    	pv.setBO_ID(1);
//    	
//    	c.addPropertyValue(pv);
//    	
//    	/**
//    	 * Objekt in Datenbank speichern
//    	 */
//    	return this.propertyValueMapper.insert(pv);
//    }
//    
//    /**
//     * Anlegen eines Kontaktobjekts
//     * @param owner, creationDate, propertyValues
//     * @return Das Kontaktobjekt wird in die Datenbank gespeichert
//     */
//    public Contact createContact(Contact owner, Timestamp creationDate, ArrayList <PropertyValue> propertyValues)
//    throws IllegalArgumentException {
//       
//    	Contact c = new Contact();
//    	c.setOwner(owner);
//    	c.setCreationDate(creationDate);
//    	c.addPropertyValues(propertyValues);
//    	c.addPropertyValues(propertyValues);
//    	
//    	/**
//    	 * Setzen einer vorläufigen ID. Der Insert Aufruf liefert dann ein Objekt,
//    	 * dessen Nummer mit der Datenbank konsistent ist.
//    	 */
//    	c.setBO_ID(1);
//    	
//    	/**
//    	 * Objekt in Datenbank speichern
//    	 */
//    	return this.contactMapper.insert(c);
//    }
//
//    /**
//     * Anlegen einer Kontaktliste
//     * @param owner, creationDate, title
//     * @return Das Kontaktlistenobjekt wird in die Datenbank gespeichert
//     */
//    public ContactList createContactList(Contact owner, Timestamp creationDate, String title)
//    throws IllegalArgumentException {
//        
//    	ContactList cl = new ContactList();
//    	cl.setOwner(owner);
//    	cl.setCreationDate(creationDate);
//    	cl.setTitle(title);
//    	
//    	/**
//    	 * Setzen einer vorläufigen ID. Der Insert Aufruf liefert dann ein Objekt,
//    	 * dessen Nummer mit der Datenbank konsistent ist.
//    	 */
//    	cl.setBO_ID(1);
//    	
//    	/**
//    	 * Objekt in Datenbank speichern
//    	 */
//        return this.contactListMapper.insert(cl);
//    }
//
//    /**
//     * Anlegen einer Teilhaberschaft
//     * @param participant, element
//     * @return Das Teilhaberschaftsobjekt wird in die Datenbank gespeichert
//     */
//    public Participation createParticipation(Contact participant, SharedBusinessObject element)
//    throws IllegalArgumentException {
//       
//    	
//    	
//    	
//    	
//    	
//    	
//        return null;
//    }
//
//    /**
//     * Löschen einer Eigenschaftsausprägung
//     * @param pv 
//     */
//    public void deletePropertyValue(PropertyValue pv)
//    throws IllegalArgumentException {
//    	/* Aufruf der MapperKlasse und Löschung der übergebenen Eigenschaft*/
//    	this.propertyValueMapper.delete(pv);
//    }
//
//    /**
//     * Löschen einer Eigenschaft
//     * @param p
//     */
//    public void deleteProperty(Property p)
//    throws IllegalArgumentException {
//    	/* Aufruf der MapperKlasse und Löschung der übergebenen Eigenschaft*/
//    	this.propertyMapper.delete(p);
//    }
//
//    /**
//     * Löschen eines Kontakts
//     * @param c 
//     */
//    public void deleteContact(Contact c) 
//    throws IllegalArgumentException {
//    	/* Aufruf der MapperKlasse und Löschung des übergebenen Kontakts*/	
//    	this.contactMapper.delete(c);
//    }
//
//    /**
//     * Löschen einer Kontaktliste
//     * @param cl
//     */
//    public void deleteContactList(ContactList cl)
//    throws IllegalArgumentException {
//    	/* Aufruf der MapperKlasse und Löschung der übergebenen Kontaktliste*/	
//    	this.contactListMapper.delete(cl);
//    }
//
//    /**
//     * Löschen einer Teilhaberschaft
//     * @param pp
//     */
//    public void deleteParticipation(Participation pp) 
//    throws IllegalArgumentException {
//    	/* Aufruf der MapperKlasse und Löschung der übergebenen Teilhaberschaft*/	
//       this.participationMapper.delete(pp);
//    }
//
//    /**
//	 * Schreibt Änderungen zu dem übergebenen Eigenschaftsausprägungs-Objekts in die Datenbank.
//	 * @param: Ein Eigenschaftsausprägungs Objekt pv.
//	 */
//    public void savePropertyValue(PropertyValue pv) 
//    throws IllegalArgumentException {
//        this.propertyValueMapper.update(pv);
//    }
//
//    /**
// 	 * Schreibt Änderungen zu dem übergebenen Kontakt-Objekts in die Datenbank.
// 	 * @param: Ein Kontakt Objekt c.
// 	 */
//    public void saveContact(Contact c)
//    throws IllegalArgumentException {
//    	this.contactMapper.update(c);
//    }
//
//    /**
// 	 * Schreibt Änderungen zu dem übergebenen Kontaktlisten-Objekts in die Datenbank.
// 	 * @param: Ein Kontaktlisten Objekt cl.
// 	 */
//    public void saveContactList(ContactList cl) 
//    throws IllegalArgumentException {
//    	this.contactListMapper.update(cl);
//    }
//
//    /**
// 	 * Schreibt Änderungen zu dem übergebenen Teilhaberschafts-Objekts in die Datenbank.
// 	 * @param: Ein Teilhaberschaft Objekt p.
// 	 */
//    public void saveProperty(Property p) 
//    throws IllegalArgumentException {
//    	this.propertyMapper.update(p);
//    }
//
//	/**
//	 * Gibt alle Kontakte zurück
//	 * @return: Eine ArrayList mit allen Kontakten die es in der Datenbank gibt.
//	 */
//    public ArrayList <Contact> getAllContacts() 
//    throws IllegalArgumentException {
//    	
//    	ArrayList<Contact> contacts = new ArrayList <Contact>();
//    	ArrayList<Contact> c = contactMapper.findAllContacts();
//    	
//    	contacts.addAll(c);
//    	
//        return contacts;
//    }
//
//    /**
//	 * Gibt alle Kontaktlisten zurück
//	 * @return: Eine ArrayList mit allen Kontaktenlisten die es in der Datenbank gibt
//	 */
//    public ArrayList <ContactList> getAllContactLists()
//    throws IllegalArgumentException {
//   
//    	ArrayList<ContactList> contactlists = new ArrayList<ContactList>();
//    	ArrayList<ContactList> cl = contactListMapper.findAllContactlists();
//    	
//    	contactlists.addAll(cl);
//    	
//        return contactlists;
//    }
//
//    /**
//     * Gibt alle Kontakte eines Nutzers zurück
//     * @return Eine ArrayList mit allen Kontakten eines Nutzers
//     */
//    public ArrayList <Contact> getParticipant(Contact user) 
//    throws IllegalArgumentException {
//      
//    	ArrayList<Contact> contacts = new ArrayList<Contact>();
//    	ArrayList<Contact> c = contactMapper.findParticipationOfContact(user);
//   
//    	contacts.addAll(c);
//    	
//        return contacts;
//    }
//
//    /**
//     * Gibt alle Kontakte einer Kontaktliste zurück
//     * @return Eine ArrayList mit allen Kontakten einer Kontaktliste
//     */
//    public ArrayList <Contact> getAllContacts(ContactList cl) 
//    throws IllegalArgumentException {
//    	
//    	ArrayList<Contact> contacts = new ArrayList<Contact>();
//    	Collection<? extends Contact> ctl = contactListMapper.findAllByObject(cl);
//    	
//    	contacts.addAll(ctl);
//    	
//        return contacts;
//    }
//
//    /**
//     * Setzen des Eigentümers eines Kontakts
//     * @param user,c1
//     */
//    public void setOwner(Contact user, Contact c1) 
//    throws IllegalArgumentException {
//        user.setOwner(c1);
//    }
//
//    /**
//     * Auslesen eines Kontakts anhand seiner ID
//     * @param id
//     */
//    public Contact getContact(int id)
//    throws IllegalArgumentException {
//    	Contact c = contactMapper.findById(id);
//    	
//        return c;
//    }
//
//    /**
//     * Auslesen einer Kontaktliste anhand der ID
//     * @param id
//     */
//    public ContactList getContactList(int id)
//    throws IllegalArgumentException {
//        ContactList ctl = contactListMapper.findById(id);
//    	
//        return ctl;
//    }
//
////    /**
////     * Auslesen von Eigenschaftsausprägungen einer Eigenschaft
////     * @param p 
////     * @return Eine ArrayList mit allen Eigenschaftsausprägungen einer Eigenschaft
////     */
////    public ArrayList <PropertyValue> getPropertyValue(Property p)
////    throws IllegalArgumentException {
////    	
////    	ArrayList<PropertyValue> pv = new ArrayList<PropertyValue>();
////    	
////    	
////    	
////    	
////        return pv;
////    }
//
//    /**
//     * Auslesen von Kontakten einer Eigenschaft
//     * @param p 
//     * @return Eine ArrayList mit allen Kontakten derselben Eigenschaft
//     */
//    public ArrayList <Contact> getContacts(Property p) 
//    throws IllegalArgumentException {
//      
//    	ArrayList<Contact> contacts = new ArrayList<Contact>();
//    	ArrayList<Contact> c = contactMapper.findAllContactsByProperty(p);
//    	
//    	contacts.addAll(p);
//    	
//        return contacts;
//    }
//
//    /**
//     * Auslesen von Kontakten einer Eigenschaftsausprägung
//     * @param pv 
//     * @return Eine ArrayList mit allen Kontakten derselben Eigenschaftsausprägung
//     */
//    public ArrayList <Contact> getContacts(PropertyValue pv) 
//    throws IllegalArgumentException {
//        
//    	ArrayList<Contact> contacts = new ArrayList<Contact>();
//    	
//    	
//        return null;
//    }
//
//    /**
//     * Auslesen des Besitzers einer Kontaktliste
//     * @param cl 
//     * @return Den Besitzer einer Kontaktliste
//     */
//    public Contact getOwner(ContactList cl) 
//    throws IllegalArgumentException {
//  
//        return cl.getOwner();
//    }
//
//    /**
//     * Ausgeben aller User
//     * @return Eine ArrayList mit allen Usern
//     */
//    public ArrayList <Contact> getAllUser() 
//    throws IllegalArgumentException {
//       
//    	ArrayList<Contact> contacts = new ArrayList<Contact>();
//    	ArrayList<Contact> c = contactMapper.findAllUsers();
//    	
//    	contacts.addAll(c);
//    	
//        return contacts;
//    }
//
//    /**
//     * Hinzufügen eines Kontaktes zu einer Kontaktliste
//     * @param c, cl
//     */
//    public void addContact(Contact c, ContactList cl) 
//    throws IllegalArgumentException {
//    	
//    	cl.addContact(c);
//    }
//
//    /**
//     * Entfernen eines Kontaktes aus einer Kontaktliste
//     * @param c, cl
//     */
//    public void removeContact(Contact c, ArrayList <ContactList> cl) 
//    throws IllegalArgumentException {
//   
//    	cl.remove(c);
//    }
//
//    /**
//     * Einem Kontakt eine Eigenschaftsausprägung hinzufügen
//     * @param c, pv
//     */
//    public void addPropertyValue(Contact c, PropertyValue pv) 
//    throws IllegalArgumentException {
//    	
//        c.addPropertyValue(pv);
//    }
//
//    /**
//     * Entfernen einer Eigenschaftsausprägung eines Kontakts
//     * @param pv 
//     */
//    public void removePropertyValue(PropertyValue pv) 
//    throws IllegalArgumentException {
//    	
//    	pv.setValue(null);
//    }
//
////    /**
////     * @param p 
////     * @return
////     */
////    public ArrayList <Property> selectProperties(Property p) 
////    throws IllegalArgumentException {
////       
////    	
////        return null;
////    }
//
//    /**
//     * Erstellen einer Eigenschaft
//     * @param term 
//     * @return Speichern des Eigenschaftsobjekts in die Datenbank
//     */
//    public Property createProperty(String term)
//    throws IllegalArgumentException {
//       
//    	Property pro = new Property();
//    	pro.setTerm(term);
//    	
//    	/**
//    	 * Setzen einer vorläufigen ID. Der Insert Aufruf liefert dann ein Objekt,
//    	 * dessen Nummer mit der Datenbank konsistent ist.
//    	 */
//    	pro.setBO_ID(1);
//    	
//    	/**
//    	 * Objekt in Datenbank speichern
//    	 */
//        return this.propertyMapper.insert(pro);
//    }
//
//    /**
//     * Ändern einer Eigenschaftsausprägung
//     * @param pv, value
//     */
//    public void changePropertyValue(PropertyValue pv, String value) 
//    throws IllegalArgumentException {
//    	
//    	pv.setValue(value);
//    	//pv.setChangeDate(changeDate);
//    }
//
//    /**
//     * Den Besitzer einer Kontaktliste festlegen
//     * @param cl, c
//     */
//    public void setOwner(ContactList cl, Contact c) 
//    throws IllegalArgumentException {
//    	
//    	cl.setOwner(c);
//    }
//
//    /**
//     * Den Besitzer einer Eigenschaftsausprägung setzen
//     * @param pv, c
//     */
//    public void setOwner(PropertyValue pv, Contact c) 
//    throws IllegalArgumentException {
//    	
//    	pv.setOwner(c);
//    }
//
//    /**
//     * Ausgeben aller Kontakte einer Kontaktliste
//     * @param cl 
//     * @return Eine ArrayList mit allen Kontakten einer Kontaktliste
//     */
//    public ArrayList<Contact> getContacts(ContactList cl) 
//    throws IllegalArgumentException {
//  
//        return cl.getContacts();
//    }
//
//    /**
//     * Erstellen eine Nutzers
//     * @param in creationDate, gmail, propertyValues
//     * @return Ein Nutzerobjekt wird in die Datenbank gespeichert
//     */
//    public Contact createUser(Timestamp creationDate, String gmail, ArrayList <PropertyValue> propertyValues)
//    throws IllegalArgumentException {
//    	Contact c = new Contact();
//    	c.setCreationDate(creationDate);
//    	c.setGmail(gmail);
//    	c.addPropertyValues(propertyValues);
//    	
//    	/**
//    	 * Setzen einer vorläufigen ID. Der Insert Aufruf liefert dann ein Objekt,
//    	 * dessen Nummer mit der Datenbank konsistent ist.
//    	 */
//    	c.setBO_ID(1);
//    	
//    	/**
//    	 * Objekt in Datenbank speichern
//    	 */
//        return this.contactMapper.insert(c);
//    }
//
//	@Override
//	public void ContactAdministration() 
//	throws IllegalArgumentException {
//		// TODO Auto-generated method stub
//		
//	}
//
////	@Override
////	public ArrayList<ContactList> getContactList(Property p) 
////	throws IllegalArgumentException {
////		// TODO Auto-generated method stub
////		return null;
////	}
//
//	@Override
//	public ArrayList<Property> select(Property p) 
//	throws IllegalArgumentException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}