package de.hdm.softwarePraktikum.server.db;

import java.util.*;

import java.sql.*;

import de.hdm.softwarePraktikum.shared.bo.Contact;
import de.hdm.softwarePraktikum.shared.bo.ContactList;
import de.hdm.softwarePraktikum.shared.bo.Property;
import de.hdm.softwarePraktikum.shared.bo.PropertyValue;
import de.hdm.softwarePraktikum.shared.bo.User;


/**
 * @author Luca Holzwarth
 */
public class ContactListMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse
	 */

	private static ContactListMapper contactListMapper = null;

	/**
	 * geschützter Konstrukter verhindert weitere Instanzierungen von
	 * ContactListMapper
	 */

	protected ContactListMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse
	 */

	public static ContactListMapper contactListMapper() {
		if (contactListMapper == null) {
			contactListMapper = new ContactListMapper();
		}

		return contactListMapper;
	}

	/**
	 * Kontaktliste mittels id finden * @param id
	 * 
	 * @return die ContactList mit der jeweiligen id
	 */
	public ContactList findById(int id) {
		Connection con = DBConnection.connection();

		ContactList contactList = new ContactList();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT ContactList.ContactList_BO_ID, ContactList.Title, BusinessObject.CreationDate, BusinessObject.ChangeDate"
							+ "FROM ContactList"
							+ "JOIN BusinessObject ON ContactList.ContactList_BO_ID = BusinessObject.BO_ID"
							+ "WHERE ContactList_BO_Id=" + id);

			if (rs.next()) {

				contactList.setBO_ID(rs.getInt("BO_ID_ContactList"));
				contactList.setTitle(rs.getString("Title"));
				contactList.setCreationDate(rs.getTimestamp("CreationDate"));
				contactList.setChangeDate(rs.getTimestamp("ChangeDate"));

				contactList.addContacts(ListStructureMapper.listStructureMapper().findAllContactsForContactList(contactList));

				contactList.setOwner(UserMapper.userMapper().findOwnerById(id));
				//contactList.addContacts(ListStructureMapper.listStructureMapper().findAllContactsForContactList(contactList));

			}
		} catch (

		SQLException e) {
			e.printStackTrace();
			return null;
		}

		return contactList;
	}

	/**
	 * @param contactList
	 * @return Liefert die ContactList entsprechend der übergebenen zurück
	 */
	public ContactList findByObject(ContactList contactList) {

		return this.findById(contactList.getBO_ID());
	}

	/**
	 * Methode um alle Kontaktlisten anzuzeigen
	 * 
	 * @return eine Liste mit allen Contactlists
	 */
	public ArrayList<ContactList> findAllContactlists() {
		Connection con = DBConnection.connection();
		ArrayList<ContactList> contactLists = new ArrayList<ContactList>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT ContactList.ContactList_BO_ID, ContactList.Title, BusinessObject.CreationDate, BusinessObject.ChangeDate"
							+ "FROM ContactList"
							+ "JOIN BusinessObject ON ContactList.ContactList_BO_ID = BusinessObject.BO_ID");

			while (rs.next()) {
				ContactList contactList = new ContactList();
				contactList.setBO_ID(rs.getInt("BO_ID_ContactList"));
				contactList.setTitle(rs.getString("Title"));
				contactList.setCreationDate(rs.getTimestamp("CreationDate"));
				contactList.setChangeDate(rs.getTimestamp("ChangeDate"));

				contactList.addContacts(ListStructureMapper.listStructureMapper().findAllContactsForContactList(contactList));

				contactList.setOwner(UserMapper.userMapper().findOwnerById(contactList.getBO_ID()));
				//contactList.addContacts(ListStructureMapper.listStructureMapper().findAllContactsForContactList(contactList));

				contactLists.add(contactList);

			}
		} catch (

		SQLException e) {
			e.printStackTrace();
			return null;
		}

		return contactLists;
	}
	
	
	/**
	 * Methode um eine Liste von ContactListen mit den entsprechenden Participations des jeweiligen Users zu suchen.
	 * @param user            
	 * @return ArrayList<ContactList>
	 */

	public ArrayList<ContactList> findAllContactListsFor(User user) {

		Connection con = DBConnection.connection();
		ArrayList<ContactList> contactLists = new ArrayList<ContactList>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Participation "
					+ " JOIN BusinessObject ON Participation.BusinessObject_BO_ID = BusinessObject.BO_ID"
					+ " JOIN ContactList ON BusinessObject.BO_ID = ContactList.ContactList_BO_ID"
					+ " WHERE Participation.User_BO_ID = " + user.getBO_ID());
			while (rs.next()) {

				contactLists.add(
						ContactListMapper.contactListMapper().findById(rs.getInt("Participation.BusinessObject_BO_ID")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contactLists;

	}
	
	

	/**
	 * Löschen einer ContactList aus der Datenbank
	 * 
	 * @param contact
	 * @return void
	 */

	public void delete(ContactList contactList) {

		Connection con = DBConnection.connection();

		try {
			con.setAutoCommit(false);
			Statement stmt = con.createStatement();
			stmt.executeQuery("DELETE FROM ContactList WHERE ContactList_BO_ID =" + contactList.getBO_ID());

			Statement stmt2 = con.createStatement();
			stmt2.executeQuery("DELETE FROM BusinessObject WHERE BO_ID =" + contactList.getBO_ID());

			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank
	 * 
	 * @param contactList
	 * @return übergebene ContactList
	 */

	public ContactList update(ContactList contactList) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			con.setAutoCommit(false);

			stmt.executeUpdate("UPDATE ContactList SET Title= " + contactList.getTitle() + " WHERE ContactList_BO_ID = "
					+ contactList.getBO_ID());

			Statement stmt2 = con.createStatement();
			stmt2.executeUpdate("UPDATE BusinessObject SET ChangeDate=" + contactList.getChangeDate()
					+ "WHERE WHERE BusinessObject_BO_ID =" + contactList.getBO_ID());

			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contactList;
	}

	/**
	 * Insert Methode, um eine neue Entiät der Datenbank hinzuzufügen
	 * 
	 * @param contactList
	 * @return Contact
	 */
	public ContactList insert(ContactList contactList) {
		Connection con = DBConnection.connection();

		try {
			 
			Statement stmt = con.createStatement();
			 
		      ResultSet rs = stmt.executeQuery("SELECT MAX(BO_ID) AS maxid "
		          + "FROM BusinessObject ");
	   
		      if (rs.next()) {
		      
		        contactList.setBO_ID(rs.getInt("maxid") + 1);
		        
		      }

			con.setAutoCommit(false);

			PreparedStatement stmt2 = con.prepareStatement(
					"INSERT INTO BusinessObject (BO_ID, CreationDate, ChangeDate, Owner VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			stmt2.setInt(1, contactList.getBO_ID());
			stmt2.setTimestamp(2, contactList.getCreationDate());
			stmt2.setTimestamp(3, contactList.getChangeDate());
			stmt2.setInt(4, contactList.getOwner().getBO_ID());
			stmt2.executeUpdate();

			PreparedStatement stmt3 = con.prepareStatement(
					"INSERT INTO ContactList (ContactList_BO_ID, Title) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt3.setInt(1, contactList.getBO_ID());
			stmt3.setString(2, contactList.getTitle());

			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return contactList;

	}
}
