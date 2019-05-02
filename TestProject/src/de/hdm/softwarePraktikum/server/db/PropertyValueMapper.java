package de.hdm.softwarePraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import de.hdm.softwarePraktikum.shared.bo.Contact;
import de.hdm.softwarePraktikum.shared.bo.Property;
import de.hdm.softwarePraktikum.shared.bo.PropertyValue;
import de.hdm.softwarePraktikum.shared.bo.User;

/**
 * Mapper Klasse für </code>PropertyValue</code> Objekte. Diese umfasst Methoden
 * um PropertyValue Objekte zu erstellen, zu suchen, zu modifizieren und zu
 * löschen. Das Mapping funktioniert dabei bidirektional. Es können Objekte in
 * DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @autor Julian Anhorn
 */
public class PropertyValueMapper {

	private static PropertyValueMapper propertyValueMapper = null;

	/**
	 * // Geschützter Konstrukter verhindert weitere Instanzierungen von Property
	 * 
	 */

	protected PropertyValueMapper() {

	}

	/**
	 * @PropertyValueMapper Sicherstellung der Singleton Eigenschaft
	 */
	public static PropertyValueMapper propertyValueMapper() {
		if (propertyValueMapper == null) {
			propertyValueMapper = new PropertyValueMapper();
		}

		return propertyValueMapper;
	}

	/**
	 * Methode um ein einzelnes PropertyValue Objekt anhand einer ID zu suchen.
	 * 
	 * @param int
	 *            Property id
	 * @return PropertyValue Objekt
	 */
	public PropertyValue findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PropertyValue JOIN BusinessObject ON PropertyValue_BO_ID= "
					+ "BusinessObject.BO_ID WHERE PropertyValue_BO_ID=" + id);

			if(rs.next()) {
			PropertyValue pv = new PropertyValue();
			pv.setBO_ID(rs.getInt("PropertyValue_BO_ID"));
			pv.setChangeDate(rs.getTimestamp("ChangeDate"));
			pv.setCreationDate(rs.getTimestamp("CreationDate"));
		//	pv.setOwner(UserMapper.userMapper().findOwnerById(id));
			pv.setValue(rs.getString("Value"));

			return pv;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Methode um alle PropertyValues eines Contacts zu suchen.
	 * 
	 * @param Contact
	 * @return ArrayList<PropertyValue>
	 */
	public ArrayList<PropertyValue> findForContact(Contact contact) {
		Connection con = DBConnection.connection();
		ArrayList<PropertyValue> propertyValues = new ArrayList<PropertyValue>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PropertyValue JOIN BusinessObject ON PropertyValue_BO_ID= "
					+ "BusinessObject.BO_ID " + "JOIN Contact ON Contact.Contact_BO_ID = PropertyValue.FK_Contact_BO_ID"
					+ " WHERE FK_Contact_BO_ID=" + contact.getBO_ID());
			while (rs.next()) {
			PropertyValue pv = new PropertyValue();
			pv.setBO_ID(rs.getInt("PropertyValue_BO_ID"));
			pv.setChangeDate(rs.getTimestamp("ChangeDate"));
			pv.setCreationDate(rs.getTimestamp("CreationDate"));
			pv.setOwner(UserMapper.userMapper().findOwnerById(pv.getBO_ID()));
			pv.setValue(rs.getString("Value"));

			propertyValues.add(pv);

			return propertyValues;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Methode um alle in der Datenbank vorhandene PropertyValue Datensätze
	 * abzurufen. Diese werden als einzelene PropertyValue Objekte innerhalb einer
	 * ArrayList pvl zurückgegeben.
	 * 
	 * @return ArrayList aller PropertyValues
	 */
	public ArrayList<PropertyValue> findAllPropertyValues() {
		Connection con = DBConnection.connection();
		// PropertyListe
		ArrayList<PropertyValue> pvlist = new ArrayList<PropertyValue>();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PropertyValue JOIN BusinessObject"
					+ " ON PropertyValue_BO_ID= BusinessObject.BO_ID");

			while (rs.next()) {
				PropertyValue pv = new PropertyValue();

				pv.setBO_ID(rs.getInt("PropertyValue_BO_ID"));
				pv.setChangeDate(rs.getTimestamp("ChangeDate"));
				pv.setCreationDate(rs.getTimestamp("CreationDate"));
				pv.setOwner(UserMapper.userMapper().findOwnerById(pv.getBO_ID()));
				pv.setValue(rs.getString("Value"));
				pvlist.add(pv);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pvlist;
	}

	/**
	 * @param PropertyValue
	 * @return Liefert das PropertyValue entsprechend dem übergebenen Objekt zurück
	 */
	public PropertyValue findByObject(PropertyValue pv) {
		return this.findById(pv.getBO_ID());

	}

	/**
	 * Delete Methode, um ein PropertyValue-Datensatz aus der Datenbank zu entfernen
	 * 
	 * @param PropertyValue
	 * @return void
	 */
	
	/**
	 * Methode um eine Liste von PropertyValues eines Contacts unter
	 * Berücksichtigung der Participations auszugeben
	 * 
	 * @param Contact
	 *            user, Contact contact
	 * @return ArrayList<PropertyValue>
	 */

	public ArrayList<PropertyValue> findAllPropertyValuesFor(User user, Contact contact) {

		Connection con = DBConnection.connection();
		ArrayList<PropertyValue> PropertyValueParticipations = new ArrayList<PropertyValue>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Participation"
					+ " JOIN BusinessObject ON Participation.BusinessObject_BO_ID = BusinessObject.BO_ID"
					+ " JOIN PropertyValue ON BusinessObject.BO_ID = PropertyValue.PropertyValue_BO_ID"
					+ " WHERE Participation.User_User_ID = " + user.getBO_ID()
					+ " AND PropertyValue.FK_Contact_BO_ID = " + contact.getBO_ID());

			while (rs.next()) {

				PropertyValueParticipations.add(PropertyValueMapper.propertyValueMapper()
						.findById(rs.getInt("Participation.BusinessObject_BO_ID")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return PropertyValueParticipations;

	}
	
	
	public void delete(PropertyValue pv) {

		Connection con = DBConnection.connection();

		try {
			con.setAutoCommit(false);
			Statement stmt = con.createStatement();
			stmt.executeQuery("DELETE FROM PropertyValue WHERE PropertyValue_BO_ID =" + pv.getBO_ID());

			Statement stmt2 = con.createStatement();
			stmt2.executeQuery("DELETE FROM BusinessObject WHERE BO_ID =" + pv.getBO_ID());

			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank
	 * 
	 * @param PropertyValue
	 * @return übergebenes PropertyValue
	 */
	public PropertyValue update(PropertyValue pv) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			con.setAutoCommit(false);

			stmt.executeUpdate("UPDATE PropertyValue SET Value='" + pv.getValue()+"'");

			Statement stmt2 = con.createStatement();
			stmt2.executeUpdate("UPDATE BusinessObject SET ChangeDate=" + pv.getChangeDate()
					+ " WHERE BusinessObject.BO_ID =" + pv.getBO_ID());

			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pv;
	}

	/**
	 * Insert Methode, um eine neue Entiät der Datenbank hinzuzufügen
	 * 
	 * @param PropertyValue
	 * @return PropertyValue
	 */
	public PropertyValue insert(PropertyValue pv) {
		Connection con = DBConnection.connection();

		try {
			
				 
				  Statement stmt = con.createStatement();
			      ResultSet rs = stmt.executeQuery("SELECT MAX(BO_ID) AS maxid "
			          + "FROM BusinessObject ");

			      if (rs.next()) {
			      
			      pv.setBO_ID(rs.getInt("maxid") + 1);

			      }

			con.setAutoCommit(false);

			PreparedStatement stmt2 = con.prepareStatement(
					"INSERT INTO BusinessObject (BO_ID, CreationDate, ChangeDate, Owner) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			stmt2.setInt(1, pv.getBO_ID());
			stmt2.setTimestamp(2, pv.getCreationDate());
			stmt2.setTimestamp(3, pv.getChangeDate());
			stmt2.setInt(4, pv.getOwner().getBO_ID());
			stmt2.executeUpdate();

			PreparedStatement stmt3 = con.prepareStatement(
					"INSERT INTO PropertyValue (PropertyValue_BO_ID, Value, FK_Property_ID, FK_Contact_BO_ID) Values (?, ?, ?,?)",
					Statement.RETURN_GENERATED_KEYS);

			stmt3.setInt(1, pv.getBO_ID());
			stmt3.setString(2, pv.getValue());
			stmt3.setInt(3, pv.getProperty().getBO_ID());
			stmt3.setInt(3, pv.getOwner().getBO_ID());
			stmt3.executeUpdate();

			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return pv;
	}

}