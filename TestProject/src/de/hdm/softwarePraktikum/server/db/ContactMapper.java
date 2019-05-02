package de.hdm.softwarePraktikum.server.db;

import java.sql.*;

import java.util.*;

import de.hdm.softwarePraktikum.shared.bo.Contact;
import de.hdm.softwarePraktikum.shared.bo.ContactList;
import de.hdm.softwarePraktikum.shared.bo.Property;
import de.hdm.softwarePraktikum.shared.bo.PropertyValue;
import de.hdm.softwarePraktikum.shared.bo.User;

/**
 * @author Luca Holzwarth
 */
public class ContactMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse
	 */
	private static ContactMapper contactMapper = null;

	/**
	 * geschützter Konstrukter verhindert weitere Instanzierungen von ContactMapper
	 */

	protected ContactMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse
	 */

	public static ContactMapper contactMapper() {
		if (contactMapper == null) {
			contactMapper = new ContactMapper();
		}

		return contactMapper;
	}

	/**
	 * Suchen eines Contacts mit eindeutiger Contakt_BO_ID
	 * 
	 * @param int
	 *            id
	 * @return Liefert den Contact mit der übergebenen Contact_BO_ID zurück
	 */
	
	
	public Contact findById(int id) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

				

					"SELECT Contact.Contact_BO_ID, BusinessObject.CreationDate, BusinessObject.ChangeDate"
							+ " FROM Contact" + " JOIN BusinessObject ON Contact.Contact_BO_ID = BusinessObject.BO_ID"
							+ " WHERE Contact_BO_Id = " + id);


			if (rs.next()) {

				Contact contact = new Contact();
				contact.setBO_ID(rs.getInt("Contact_BO_Id"));
				contact.setCreationDate(rs.getTimestamp("CreationDate"));
				contact.setChangeDate(rs.getTimestamp("ChangeDate"));

				contact.setOwner(UserMapper.userMapper().findOwnerById(id));
				//contact.addPropertyValues(PropertyValueMapper.propertyValueMapper().findForContact(contact));

				return contact;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;

	}

	/**
	 * Liefert eine Auflistung aller Contacts
	 * 
	 * @return ArrayList<Contact>
	 */
	public ArrayList<Contact> findAllContacts() {
		Connection con = DBConnection.connection();

		ArrayList<Contact> contacts = new ArrayList<Contact>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT Contact. Contact_BO_ID, BusinessObject.CreationDate, BusinessObject.ChangeDate"
							+ " FROM Contact" + " JOiN BusinessObject ON Contact.Contact_BO_ID = BusinessObject.BO_ID"
							+ " WHERE  IsUser = 1");

			while (rs.next()) {

				Contact contact = new Contact();
				contact.setBO_ID(rs.getInt("Contact_BO_Id"));
				contact.setCreationDate(rs.getTimestamp("CreationDate"));
				contact.setChangeDate(rs.getTimestamp("ChangeDate"));

	
				contact.setOwner(UserMapper.userMapper().findOwnerById(contact.getBO_ID()));
			//	contact.addPropertyValues(PropertyValueMapper.propertyValueMapper().findForContact(contact));


				contacts.add(contact);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return contacts;
	}

	/**
	 * Ausgabe aller Contacts, welche die entsprechende Property besitzen
	 * 
	 * @param Contact
	 *            und Property
	 * @return ArrayList<Contact>
	 */
	public ArrayList<Contact> findAllContactsFor(Property property) {

		Connection con = DBConnection.connection();
		ArrayList<Contact> contacts = new ArrayList<Contact>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT Contact.Contact_BO_ID, BusinessObject.CreationDate, BusinessObject.ChangeDate"
							+ " FROM Contact" + " JOIN BusinessObject ON Contact.Contact_BO_ID = BusinessObject.BO_ID"
							+ " JOIN PropertyValue ON Contact.Contact_BO_ID = FK_Contact_BO_ID"
							+ " JOIN Property ON Property.Property_ID = FK_Property_ID" + " WHERE Property.Property_ID = "
							+ property.getBO_ID());

			if (rs.next()) {

				Contact c = new Contact();
				c.setBO_ID(rs.getInt("Contact_BO_Id"));
				c.setCreationDate(rs.getTimestamp("CreationDate"));
				c.setChangeDate(rs.getTimestamp("ChangeDate"));

				c.setOwner(UserMapper.userMapper().findOwnerById(c.getBO_ID()));

				contacts.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return contacts;

	}
	
	
	/**
	 * Liefert eine Auflistung aller Contacts einer ContactList
	 * 
	 * @return ArrayList<Contact>
	 */
	
	public ArrayList<Contact> findAllContactsFor(ContactList contactList) {
		Connection con = DBConnection.connection();

		ArrayList<Contact> contacts = new ArrayList<Contact>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT Contact. Contact_BO_ID, Contact.GMail, Contact.IsUSer, BusinessObject.CreationDate, BusinessObject.ChangeDate"
							+ " FROM Contact" + " JOIN BusinessObject ON Contact.Contact_BO_ID = BusinessObject.BO_ID"
							+ " JOIN ListStructure ON Contact.Contact_BO_ID = ListStructure.Contact_BO_ID"
							+ " WHERE ListStructure.ContactList_BO_ID= " + contactList.getBO_ID());

			while (rs.next()) {

				Contact contact = this.findById(rs.getInt("ListStructure.Contact_BO_ID"));
				contacts.add(contact);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return contacts;
	}
	
	/**
	 * Methode um eine Liste von Contacts mit der entsprechenden Participations eines Users zu suchen
	 * @param user
	 * @return ArrayList<Contact>
	 */

	public ArrayList<Contact> findAllContactsFor(User user) {

		Connection con = DBConnection.connection();
		ArrayList<Contact> contacts = new ArrayList<Contact>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Participation "
					+ " JOIN BusinessObject ON Participation.BusinessObject_BO_ID = BusinessObject.BO_ID"
					+ " JOIN User ON BusinessObject.BO_ID = User.User_ID"
					+ " WHERE Participation.User_ID = " + user.getBO_ID());

			while (rs.next()) {

				Contact contact = this.findById(rs.getInt("Participation.BusinessObject_BO_ID"));
	//			contact.addPropertyValues(ParticipationMapper.participationMapper.findAllParticipations(user, contact));
				contacts.add(contact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contacts;

	}
	
	
	/**
	 * Methode um eine Liste von Contacts mit den entsprechenden Participations
	 * anhand eines Contacts in der Rolle User und einer ausgewählte property zu
	 * suchen
	 * 
	 * @param int
	 *            id
	 * @return ArrayList<Contact>
	 */

	public ArrayList<Contact> findAllContactsFor(User user, Property property) {

		Connection con = DBConnection.connection();
		ArrayList<Contact> contacts = new ArrayList<Contact>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Participation JOIN BusinessObject ON"
					+ " Participation.BusinessObject_BO_ID = BusinessObject.BO_ID"
					+ " JOIN Contact ON BusinessObject.BO_ID = Contact.Contact_BO_ID"
					+ " JOIN PropertyValue ON Contact.Contact_BO_ID = FK_Contact_BO_ID"
					+ " JOIN Property ON Property.Property_ID = FK_Property_ID"
					+ " WHERE Participation.User_ID = " + user.getBO_ID() + " AND Property.Property_ID = "
					+ property.getBO_ID());

			while (rs.next()) {

				Contact contact = ContactMapper.contactMapper().findById(rs.getInt("Participation.BusinessObject_BO_ID"));
//				contact.addPropertyValues(
//						ParticipationMapper.participationMapper.findAllParticipations(user, contact));
				contacts.add(contact);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contacts;

	}

	
	/**
	 * Methode um nach einer Participation zwischen User und Contact zu suchen 
	 * @param user, contact
	 * @return Contact des Users
	 */

	public Contact findContactFor(Contact user, Contact contact) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Participation JOIN BusinessObject ON"
					+ " Participation.BusinessObject_BO_ID = BusinessObject.BO_ID"
					+ " JOIN User ON BusinessObject.BO_ID = User.User_ID"
					+ " WHERE Participation.User_ID = " + user.getBO_ID()
					+ " AND Participation.BusinessObject_BO_ID =" + contact.getBO_ID());

			if (rs.next()) {

				contact = ContactMapper.contactMapper().findById(rs.getInt("Participation.BusinessObject_BO_ID"));
		//		contact.addPropertyValues(ParticipationMapper.participationMapper.findAllParticipations(user, contact));

				return contact;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	

	/**
	 * @param contact
	 * @return Liefert den Contact entsprechend dem übergebenen zurück
	 */
	public Contact findByObject(Contact contact) {

		return this.findById(contact.getBO_ID());
	}


	/**
	 * @param contact
	 * @return Liefert den Owner des BusinessObjects zurück
	 */
	public Contact findOwnerById(int id) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * " + " FROM BusinessObject"
					+ " JOIN Contact ON Contact.Contact_BO_ID = BusinessObject.Owner" + " WHERE BusinessObject.BO_ID ="
					+ id + " AND BusinessObject.Owner = Contact.Contact_Bo_ID");

			if (rs.next()) {
				Contact user = new Contact();

				user.setBO_ID(rs.getInt("Contact_BO_Id"));
			
				user.setCreationDate(rs.getTimestamp("CreationDate"));
				user.setChangeDate(rs.getTimestamp("ChangeDate"));
			

				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Ausgabe einer Liste aller Kontakte in der Rolle User
	 * 
	 * @return ArrayList<Contact>
	 */
	public ArrayList<Contact> findAllUsers() {

		Connection con = DBConnection.connection();
		ArrayList<Contact> contacts = new ArrayList<Contact>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT Contact.Contact_BO_ID, BusinessObject.CreationDate, BusinessObject.ChangeDate "
							+ " FROM Contact" + " JOIN BusinessObject ON Contact.Contact_BO_ID = BusinessObject.BO_ID");
					

			if (rs.next()) {

				Contact contact = new Contact();
				contact.setBO_ID(rs.getInt("Contact_BO_Id"));
			
				contact.setCreationDate(rs.getTimestamp("CreationDate"));
				contact.setChangeDate(rs.getTimestamp("ChangeDate"));
			//	contact.setOwner(rs.getInt("Owner"));
			//	contact.addPropertyValues(PropertyValueMapper.propertyValueMapper().findForContact(contact));
				contacts.add(contact);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return contacts;

	}


	/**
	 * Delete Methode, um Contact-Datensätze aus der Datenbank zu entfernen
	 * 
	 * @param contact
	 * @return void
	 */
	public void delete(Contact contact) {

		Connection con = DBConnection.connection();

		try {
			con.setAutoCommit(false);
			Statement stmt = con.createStatement();
			stmt.executeQuery("DELETE FROM Contact WHERE Contact_BO_ID =" + contact.getBO_ID());

			Statement stmt2 = con.createStatement();
			stmt2.executeQuery("DELETE FROM BusinessObject WHERE BO_ID =" + contact.getBO_ID());

			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank
	 * 
	 * @param contact
	 * @return übergebener Contact
	 */

	public Contact update(Contact contact) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			con.setAutoCommit(false);


			Statement stmt2 = con.createStatement();
			stmt2.executeUpdate("UPDATE BusinessObject SET ChangeDate=" + contact.getChangeDate()
					+ " WHERE BusinessObject_BO_ID =" + contact.getBO_ID());

			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contact;
	}



	/**
	 * Insert Methode, um eine neun Contact der Datenbank hinzuzufügen
	 * 
	 * @param contact
	 * @return contact
	 */
	public Contact insert(Contact contact) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(BO_ID) AS maxid " + "FROM BusinessObject ");

			if (rs.next()) {

				contact.setBO_ID(rs.getInt("maxid") + 1);

			}
			con.setAutoCommit(false);

			PreparedStatement stmt2 = con.prepareStatement(
					"INSERT INTO BusinessObject (BO_ID, CreationDate, ChangeDate, Owner VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			stmt2.setInt(1, contact.getBO_ID());
			stmt2.setTimestamp(2, contact.getCreationDate());
			stmt2.setTimestamp(3, contact.getChangeDate());
			stmt2.setInt(4, contact.getOwner().getBO_ID());
			stmt2.executeUpdate();

			PreparedStatement stmt3 = con.prepareStatement("INSERT INTO Contact (Contact_BO_ID) VALUES (?,)",
					Statement.RETURN_GENERATED_KEYS);

			stmt3.setInt(1, contact.getBO_ID());
			stmt3.executeUpdate();

			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return contact;

	

	}

}