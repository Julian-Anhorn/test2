package de.hdm.softwarePraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarePraktikum.shared.bo.Contact;
import de.hdm.softwarePraktikum.shared.bo.ContactList;


/**
 * @author Luca Holzwarth 
 */

public class ListStructureMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse
	 */

	private static ListStructureMapper listStructureMapper = null;

	/**
	 * geschützter Konstrukter verhindert weitere Instanzierungen von
	 * ListStructureMapper
	 */

	protected ListStructureMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse
	 */

	public static ListStructureMapper listStructureMapper() {
		if (listStructureMapper == null) {
			listStructureMapper = new ListStructureMapper();
		}

		return listStructureMapper;
	}

	/**
	 * Liefert eine Auflistung aller Contacts einer ContactList
	 * 
	 * @return ArrayList<Contact>
	 */
	public ArrayList<Contact> findAllContactsForContactList(ContactList contactList) {
		Connection con = DBConnection.connection();

		ArrayList<Contact> contacts = new ArrayList<Contact>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT Contact. Contact_BO_ID,  BusinessObject.CreationDate, BusinessObject.ChangeDate"
							+ " FROM Contact" + " JOIN BusinessObject ON Contact.Contact_BO_ID = BusinessObject.BO_ID"
							+ " JOIN ListStructure ON Contact.Contact_BO_ID = ListStructure.Contact_BO_ID"
							+ " WHERE ListStructure.ContactList_BO_ID= " + contactList.getBO_ID());

			while (rs.next()) {

				Contact contact = new Contact();
				contact.setBO_ID(rs.getInt("Contact_BO_Id"));
			
				contact.setCreationDate(rs.getTimestamp("CreationDate"));
				contact.setChangeDate(rs.getTimestamp("ChangeDate"));
				//contact.setOwner(ContactMapper.contactMapper().findOwnerById(contact.getBO_ID()));
				

				contacts.add(contact);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return contacts;
	}
	
	


	
	/**
	 * Löschen eines ListStructure Eintrags aus der Datenbank
	 * 
	 * @param ListStructureMapper
	 * @return void
	 */

	public void delete(ContactList contactList, Contact contact) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeQuery("DELETE FROM ListStructure" 
			+ "WHERE ContactList_BO_ID = " + contactList.getBO_ID()
			+ "AND Contact_BO_ID = " + contact.getBO_ID());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Insert Methode, um eine neue Entiät der Datenbank hinzuzufügen
	 * 
	 * @param listStructure
	 */
	public void insert(ContactList contactList, Contact contact) {
		Connection con = DBConnection.connection();

		try {
			
			PreparedStatement stmt2 = con.prepareStatement(
					"INSERT INTO ListStructure (ContactList_BO_ID, Contact_BO_ID) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			stmt2.setInt(1, contactList.getBO_ID());
			stmt2.setInt(2, contact.getBO_ID());

			stmt2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return;

		}

}
