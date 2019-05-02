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
// * ContactAdministation. In der Klasse ist neben ReportGeneratorImpl s�mtliche
// * Applikationslogik vorhanden.
// * 
// * @author Tobias Moser
// */
//
//public class ContactAdministrationImpl  {
//
//    /**
//     * Referenz auf den Eigenschaftsauspr�gungsMapper, welcher Eigenschaftsauspr�gungs
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
//	 * Um mit der Datenbank kommunizieren zu k�nnen ben�tigt die Klasse ProjektmarktplatzVerwaltung
//	 * einen vollst�ndigen Satz von Mappern.
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
//     * Anlegen eines Eigenschaftsauspr�gungsobjekts
//     * @param owner, c, value, p
//     * @return Das Eigenschaftsauspr�gungsobjekt wird in die Datenbank geschrieben
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
//    	 * Setzen einer vorl�ufigen ID. Der Insert Aufruf liefert dann ein Objekt,
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
//    	 * Setzen einer vorl�ufigen ID. Der Insert Aufruf liefert dann ein Objekt,
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
//    	 * Setzen einer vorl�ufigen ID. Der Insert Aufruf liefert dann ein Objekt,
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
//     * L�schen einer Eigenschaftsauspr�gung
//     * @param pv 
//     */
//    public void deletePropertyValue(PropertyValue pv)
//    throws IllegalArgumentException {
//    	/* Aufruf der MapperKlasse und L�schung der �bergebenen Eigenschaft*/
//    	this.propertyValueMapper.delete(pv);
//    }
//
//    /**
//     * L�schen einer Eigenschaft
//     * @param p
//     */
//    public void deleteProperty(Property p)
//    throws IllegalArgumentException {
//    	/* Aufruf der MapperKlasse und L�schung der �bergebenen Eigenschaft*/
//    	this.propertyMapper.delete(p);
//    }
//
//    /**
//     * L�schen eines Kontakts
//     * @param c 
//     */
//    public void deleteContact(Contact c) 
//    throws IllegalArgumentException {
//    	/* Aufruf der MapperKlasse und L�schung des �bergebenen Kontakts*/	
//    	this.contactMapper.delete(c);
//    }
//
//    /**
//     * L�schen einer Kontaktliste
//     * @param cl
//     */
//    public void deleteContactList(ContactList cl)
//    throws IllegalArgumentException {
//    	/* Aufruf der MapperKlasse und L�schung der �bergebenen Kontaktliste*/	
//    	this.contactListMapper.delete(cl);
//    }
//
//    /**
//     * L�schen einer Teilhaberschaft
//     * @param pp
//     */
//    public void deleteParticipation(Participation pp) 
//    throws IllegalArgumentException {
//    	/* Aufruf der MapperKlasse und L�schung der �bergebenen Teilhaberschaft*/	
//       this.participationMapper.delete(pp);
//    }
//
//    /**
//	 * Schreibt �nderungen zu dem �bergebenen Eigenschaftsauspr�gungs-Objekts in die Datenbank.
//	 * @param: Ein Eigenschaftsauspr�gungs Objekt pv.
//	 */
//    public void savePropertyValue(PropertyValue pv) 
//    throws IllegalArgumentException {
//        this.propertyValueMapper.update(pv);
//    }
//
//    /**
// 	 * Schreibt �nderungen zu dem �bergebenen Kontakt-Objekts in die Datenbank.
// 	 * @param: Ein Kontakt Objekt c.
// 	 */
//    public void saveContact(Contact c)
//    throws IllegalArgumentException {
//    	this.contactMapper.update(c);
//    }
//
//    /**
// 	 * Schreibt �nderungen zu dem �bergebenen Kontaktlisten-Objekts in die Datenbank.
// 	 * @param: Ein Kontaktlisten Objekt cl.
// 	 */
//    public void saveContactList(ContactList cl) 
//    throws IllegalArgumentException {
//    	this.contactListMapper.update(cl);
//    }
//
//    /**
// 	 * Schreibt �nderungen zu dem �bergebenen Teilhaberschafts-Objekts in die Datenbank.
// 	 * @param: Ein Teilhaberschaft Objekt p.
// 	 */
//    public void saveProperty(Property p) 
//    throws IllegalArgumentException {
//    	this.propertyMapper.update(p);
//    }
//
//	/**
//	 * Gibt alle Kontakte zur�ck
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
//	 * Gibt alle Kontaktlisten zur�ck
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
//     * Gibt alle Kontakte eines Nutzers zur�ck
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
//     * Gibt alle Kontakte einer Kontaktliste zur�ck
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
//     * Setzen des Eigent�mers eines Kontakts
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
////     * Auslesen von Eigenschaftsauspr�gungen einer Eigenschaft
////     * @param p 
////     * @return Eine ArrayList mit allen Eigenschaftsauspr�gungen einer Eigenschaft
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
//     * Auslesen von Kontakten einer Eigenschaftsauspr�gung
//     * @param pv 
//     * @return Eine ArrayList mit allen Kontakten derselben Eigenschaftsauspr�gung
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
//     * Hinzuf�gen eines Kontaktes zu einer Kontaktliste
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
//     * Einem Kontakt eine Eigenschaftsauspr�gung hinzuf�gen
//     * @param c, pv
//     */
//    public void addPropertyValue(Contact c, PropertyValue pv) 
//    throws IllegalArgumentException {
//    	
//        c.addPropertyValue(pv);
//    }
//
//    /**
//     * Entfernen einer Eigenschaftsauspr�gung eines Kontakts
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
//    	 * Setzen einer vorl�ufigen ID. Der Insert Aufruf liefert dann ein Objekt,
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
//     * �ndern einer Eigenschaftsauspr�gung
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
//     * Den Besitzer einer Eigenschaftsauspr�gung setzen
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
//    	 * Setzen einer vorl�ufigen ID. Der Insert Aufruf liefert dann ein Objekt,
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