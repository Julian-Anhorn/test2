package de.hdm.softwarePraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarePraktikum.shared.bo.Contact;
import de.hdm.softwarePraktikum.shared.bo.ContactList;
import de.hdm.softwarePraktikum.shared.bo.Property;
import de.hdm.softwarePraktikum.shared.bo.PropertyValue;
import de.hdm.softwarePraktikum.shared.bo.User;

/**
 *  @author Luca Holzwarth 
 */
public class ParticipationMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse
	 */
	private static ParticipationMapper participationMapper = null;

	/**
	 * geschützter Konstrukter verhindert weitere Instanzierungen von
	 * ParticipationMapper
	 */

	protected ParticipationMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse
	 */

	public static ParticipationMapper participationMapper() {
		if (participationMapper == null) {
			participationMapper = new ParticipationMapper();
		}

		return participationMapper;
	}

	/**
	 * Methode um eine Liste von Contacts mit den entsprechenden Participations anhand eines Contacts in der Rolle User zu
	 * suchen.
	 * 
	 * @param int
	 *            id
	 * @return ArrayList<Contact>
	 */

	public ArrayList<Contact> findContactsBy(User user) {

		Connection con = DBConnection.connection();
		ArrayList<Contact> contacts = new ArrayList<Contact>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Participation JOIN BusinessObject ON"
					+ " Participation.BusinessObject_BO_ID = BusinessObject.BO_ID"
					+ " JOIN Contact ON BusinessObject.BO_ID = Contact.Contact_BO_ID"
					+ " WHERE Participation.User_User_ID = " + user.getBO_ID());

			while (rs.next()) {

				Contact contact = ContactMapper.contactMapper().findById(rs.getInt("Participation.BusinessObject_BO_ID"));
				contacts.add(contact);
				

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contacts;

	}
	
	/**
	 * Methode um einen Contact mit der entsprechenden Participation anhand eines Contacts in der Rolle User zu
	 * suchen.
	 * @param int
	 *            id
	 * @return Contact mit den für den Contact in der Rolle User angezeigten PropertyValues
	 */

	public Contact findAllContacts(Contact user, Contact contact) {

		Connection con = DBConnection.connection();
	

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Participation JOIN BusinessObject ON"
					+ " Participation.BusinessObject_BO_ID = BusinessObject.BO_ID"
					+ " JOIN Contact ON BusinessObject.BO_ID = Contact.Contact_BO_ID"
					+ " WHERE Participation.Contact_BO_ID = " + user.getBO_ID()
					+ " AND Participation.BusinessObject_BO_ID =" + contact.getBO_ID());

			if (rs.next()) {

				contact = ContactMapper.contactMapper().findById(rs.getInt("Participation.BusinessObjekc_BO_ID"));
			
				
				return contact;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	
	
	/**
	 * Methode um eine Liste von Contacts mit den entsprechenden Participations anhand eines Contacts in der Rolle User und
	 * einer ausgewählte property zu suchen
	 * @param int
	 *            id
	 * @return ArrayList<Contact>
	 */

	public ArrayList<Contact> findAllContacts(User user, Property property) {

		Connection con = DBConnection.connection();
		ArrayList<Contact> contacts = new ArrayList<Contact>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Participation JOIN BusinessObject ON"
					+ " Participation.BusinessObject_BO_ID = BusinessObject.BO_ID"
					+ " JOIN Contact ON BusinessObject.BO_ID = Contact.Contact_BO_ID"
					+ " JOIN PropertyValue ON Contact.Contact_BO_ID = FK_Contact_BO_ID"
					+ " JOIN Property ON Property.Property_ID = FK_Property_ID" 
					+ " WHERE Participation.User_User_ID = " + user.getBO_ID()
					+ " AND Property.Property_ID = " + property.getBO_ID());

			while (rs.next()) {

				Contact contact = ContactMapper.contactMapper().findById(rs.getInt("Participation.BusinessObject_BO_ID"));
				//contact.addPropertyValues(
						//ParticipationMapper.participationMapper.findParticipationFromPropertyValues(user, contact));
				contacts.add(contact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contacts;

	}
	
	

	/**
	 * Methode um eine Liste von ContactList-Participations anhand einer User-ID zu
	 * suchen.
	 * 
	 * @param int
	 *            id
	 * @return ArrayList<ContactList>
	 */

	public ArrayList<ContactList> findAllContactLists(int id) {

		Connection con = DBConnection.connection();
		ArrayList<ContactList> contactLists = new ArrayList<ContactList>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Participation JOIN BusinessObject ON"
					+ " Participation.BusinessObject_BO_ID = BusinessObject.BO_ID"
					+ " JOIN ContactList ON BusinessObject.BO_ID = ContactList.ContactList_BO_ID"
					+ " WHERE Participation.User_User_ID = " + id);
			while (rs.next()) {

				contactLists.add(
						ContactListMapper.contactListMapper().findById(rs.getInt("BusinessObject_BO_ID")));
					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contactLists;

	}

	/**
	 * Methode um eine Liste von PropertyValues eines Contacts unter
	 * Berücksichtigung der User-Berechtigungen auszugeben
	 * @param Contact
	 *            user, Contact contact
	 * @return ArrayList<PropertyValue>
	 */

	public ArrayList<PropertyValue> findAllParticipations(User user, Contact contact) {

		Connection con = DBConnection.connection();
		ArrayList<PropertyValue> PropertyValueParticipations = new ArrayList<PropertyValue>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Participation JOIN BusinessObject ON"
					+ " Participation.BusinessObject_BO_ID = BusinessObject.BO_ID"
					+ " JOIN PropertyValue ON BusinessObject.BO_ID = PropertyValue.PropertyValue_BO_ID"
					+ " WHERE Participation.Contact_BO_ID = " + user.getBO_ID()
					+ " AND PropertyValue.FK_Contact_BO_ID = " + contact.getBO_ID());

			while (rs.next()) {

				PropertyValueParticipations.add(PropertyValueMapper.propertyValueMapper()
						.findById(rs.getInt("Participation.Business_Objekt_ID")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return PropertyValueParticipations;

	}


	public void delete(int userId, int boId) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM Participation " + "WHERE Participation.Contact_Bo_ID = " + userId
					+ "AND Participation.BusinessObject_BO_ID= " + boId);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * Insert Methode, um eine neue Entiät der Datenbank hinzuzufügen
	 * 
	 * @param contact
	 * @return übergebene Participation
	 */
	public void insert(int userId, int boId) {
		Connection con = DBConnection.connection();

		try {
			

			PreparedStatement stmt2 = con.prepareStatement(

					"INSERT INTO Participation (Contact_BO_ID, BusinessObject_BO_ID) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			stmt2.setInt(1, userId);
			stmt2.setInt(2, boId);
			stmt2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	// /**
	// * Methode um eine Liste von PropertyValue-Participations anhand einer User-ID
	// * zu suchen.
	// *
	// * @param int
	// * id
	// * @return Participation Objekt
	// */
	//
	// public ArrayList<PropertyValue> findPropertyValueParticipationsById(int id) {
	//
	// Connection con = DBConnection.connection();
	// ArrayList<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
	//
	// try {
	// Statement stmt = con.createStatement();
	// ResultSet rs = stmt.executeQuery("SELECT * FROM Participation JOIN
	// BusinessObject ON "
	// + "Participation.BusinessObject_BO_ID = BusinessObject.BO_ID "
	// + "JOIN PropertyValues ON BusinessObject.BO_ID =
	// PropertyValues.PropertyValues_BO_ID"
	// + "WHERE Participation.BusinessObject_BO_ID = " + id);
	//
	// while (rs.next()) {
	//
	// propertyValues.add(PropertyValueMapper.propertyValueMapper()
	// .findById(rs.getInt("Participation.Business_Objekt_ID")));
	//
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// return propertyValues;
	//
	// }

	//
	// /**
	// * @return
	// */
	// public ArrayList<Participation> findAllParticipations() {
	//
	// Connection con = DBConnection.connection();
	//
	// ArrayList<Participation> participations = new ArrayList<Participation>();
	//
	// try {
	// Statement stmt = con.createStatement();
	// ResultSet rs = stmt.executeQuery("SELECT * " + " FROM Participation");
	//
	// while (rs.next()) {
	// Participation participation = new Participation();
	//
	// participation.setBO_ID(rs.getInt("Participation_BO_Id"));
	// participation.setUser(ContactMapper.contactMapper().findById(rs.getInt("Contact_BO_ID")));
	// participation
	// .setSbo(SharedBusinessObjectMapper.sharedBusinessObjectMapper().findById(rs.getInt("BO_ID")));
	//
	// participations.add(participation);
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return participations;
	// }
	//
	// /**
	// * @param participation
	// * @return Liefert die Participation entsprechend der übergebenen zurück
	// */
	// public Participation findByObject(Participation participation) {
	//
	// return this.findById(participation.getBO_ID());
	// }
	//
	// /**
	// * Delete Methode, um ein Participation-Datensätze aus der Datenbank zu
	// * entfernen
	// *
	// * @param contact
	// * @return void
	// */

	// /**
	// * Wiederholtes Schreiben eines Objekts in die Datenbank Die Es handelt sich
	// um
	// * eine Participation zwischen einem Contact in der Rolle User und einem
	// Contact
	// *
	// * @param participation
	// * @return übergebene Participation
	// */
	//
	// public Participation<Contact>
	// updateContactParticipation(Participation<Contact> participation) {
	// Connection con = DBConnection.connection();
	//
	// try {
	//
	// Statement stmt = con.createStatement();
	// stmt.executeUpdate("UPDATE Participation" + "SET Participation_BO_ID =" +
	// participation.getBO_ID() + ","
	// + "Contact_BO_ID=" + participation.getUser().getBO_ID() + "," +
	// "BusinessObject_BO_ID="
	// + participation.getSbo().getBO_ID() + "," + "WHERE Participation_BO_ID ="
	// + participation.getBO_ID());
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// return participation;
	// }
	//
	// /**
	// * Wiederholtes Schreiben eines Objekts in die Datenbank Die Es handelt sich
	// um
	// * eine Participation zwischen einem Contact in der Rolle User und einer
	// * ContactList
	// *
	// * @param participation
	// * @return übergebene Participation
	// */
	//
	// public Participation<ContactList>
	// updateContacListtParticipation(Participation<ContactList> participation) {
	// Connection con = DBConnection.connection();
	//
	// try {
	//
	// Statement stmt = con.createStatement();
	// stmt.executeUpdate("UPDATE Participation" + "SET Participation_BO_ID =" +
	// participation.getBO_ID() + ","
	// + "Contact_BO_ID=" + participation.getUser().getBO_ID() + "," +
	// "BusinessObject_BO_ID="
	// + participation.getSbo().getBO_ID() + "," + "WHERE Participation_BO_ID ="
	// + participation.getBO_ID());
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// return participation;
	// }
	//
	// /**
	// * Wiederholtes Schreiben eines Objekts in die Datenbank Die Es handelt sich
	// um
	// * eine Participation zwischen einem Contact in der Rolle User und einer
	// * PropertyValue
	// *
	// * @param participation
	// * @return übergebene Participation
	// */
	//
	// public Participation<PropertyValue>
	// updatePropertyValuetParticipation(Participation<PropertyValue> participation)
	// {
	// Connection con = DBConnection.connection();
	//
	// try {
	//
	// Statement stmt = con.createStatement();
	// stmt.executeUpdate("UPDATE Participation" + "SET Participation_BO_ID =" +
	// participation.getBO_ID() + ","
	// + "Contact_BO_ID=" + participation.getUser().getBO_ID() + "," +
	// "BusinessObject_BO_ID="
	// + participation.getSbo().getBO_ID() + "," + "WHERE Participation_BO_ID ="
	// + participation.getBO_ID());
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// return participation;
	// }

	// /**
	// * Insert Methode, um eine neue Entiät der Datenbank hinzuzufügen Es handelt
	// * sich dabei um eine Participation zwischen einem Contact in der Rolle User
	// und
	// * einer ContactList
	// *
	// * @param contact
	// * @return übergebene Participation
	// */
	// public Participation<ContactList>
	// insertContactListParticipation(Participation<ContactList> participation) {
	// Connection con = DBConnection.connection();
	//
	// try {
	//
	// PreparedStatement stmt = con.prepareStatement(
	//
	// "INSERT INTO Participation (Participation_BO_ID, Contact_BO_ID,
	// BusinessObject_BO_ID, CreationDate) VALUES (?, ?, ?, ?)",
	// Statement.RETURN_GENERATED_KEYS);
	//
	// stmt.setInt(1, participation.getBO_ID());
	// stmt.setInt(2, participation.getUser().getBO_ID());
	// stmt.setInt(3, participation.getSbo().getBO_ID());
	// stmt.executeUpdate();
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	//
	// }
	// return participation;
	// }
	//
	// /**
	// * Insert Methode, um eine neue Entiät der Datenbank hinzuzufügen
	// * Es handelt sich dabei um eine Participation zwischen einem Contact in der
	// Rolle User und einer PropertyValue
	// * @param contact
	// * @return übergebene Participation
	// */
	// public Participation<PropertyValue>
	// insertPropertyValueParticipation(Participation<PropertyValue> participation)
	// {
	// Connection con = DBConnection.connection();
	//
	// try {
	//
	// PreparedStatement stmt = con.prepareStatement(
	//
	// "INSERT INTO Participation (Participation_BO_ID, Contact_BO_ID,
	// BusinessObject_BO_ID, CreationDate) VALUES (?, ?, ?, ?)",
	// Statement.RETURN_GENERATED_KEYS);
	//
	// stmt.setInt(1, participation.getBO_ID());
	// stmt.setInt(2, participation.getUser().getBO_ID());
	// stmt.setInt(3, participation.getSbo().getBO_ID());
	//
	// stmt.executeUpdate();
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	//
	// }
	// return participation;
	// }

}