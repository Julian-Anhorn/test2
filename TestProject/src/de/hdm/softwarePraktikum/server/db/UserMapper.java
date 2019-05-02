package de.hdm.softwarePraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarePraktikum.shared.bo.User;

public class UserMapper {
	
	/**
	 * Speicherung der Instanz dieser Mapperklasse
	 */
	private static UserMapper userMapper = null;

	/**
	 * geschützter Konstrukter verhindert weitere Instanzierungen von UserMapper
	 */

	protected UserMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse
	 */

	public static UserMapper userMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}

		return userMapper;
	}


	
	
	/**
	 * User mittels id finden 
	 * @param id
	 * @return den User mit der entsprechenden id
	 */
	
	public User findById(int id) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT User_ID, Gmail"
							+ " FROM User" 
							+ " WHERE User_Id = " + id);

			if (rs.next()) {

			User user = new User();
				user.setBO_ID(rs.getInt("User_Id"));
				user.setGmail(rs.getString("Gmail"));
				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;

	}
	
	
	/**
	 * @param user
	 * @return Liefert den User entsprechend dem übergebenen zurück
	 */
	public User findByObject(User user) {

		return this.findById(user.getBO_ID());
	}

	
	/**
	 * @param User
	 * @return Liefert den Owner des BusinessObjects zurück
	 */
	public User findOwnerById(int id) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * " + " FROM BusinessObject"
					+ " JOIN User ON User.User_ID = BusinessObject.Owner" + " WHERE BusinessObject.BO_ID ="
					+ id + " AND BusinessObject.Owner = User.User_ID");

			if (rs.next()) {
				User user = new User();

				user.setBO_ID(rs.getInt("Contact_BO_Id"));
				user.setGmail(rs.getString("GMail"));

				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Ausgabe einer Liste aller User
	 * 
	 * @return ArrayList<User>
	 */
	public ArrayList<User> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<User> users = new ArrayList<User>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT User_ID, Gmail"
							+ " FROM User");
						

			while (rs.next()) {

				User user = new User();
				user.setBO_ID(rs.getInt("User_Id"));
				user.setGmail(rs.getString("GMail"));
				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return users;

	}
	
	
	/**
	 * Delete Methode, um einen User-Datensatz aus der Datenbank zu entfernen
	 * 
	 * @param user
	 * @return void
	 */
	public void delete(User user) {

		Connection con = DBConnection.connection();

		try {
	
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM User WHERE User_ID =" + user.getBO_ID());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank
	 * 
	 * @param user
	 * @return übergebener user
	 */

	public User update(User user) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE User SET GMail = '" + user.getGmail() 
			+ "' WHERE User_ID = " + user.getBO_ID());


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	/**
	 * Insert Methode, um eine neue Entiät der Datenbank hinzuzufügen
	 * 
	 * @param user
	 * @return user
	 */
	public User insert(User user) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(User_ID) AS maxid " + "FROM User ");

			if (rs.next()) {

				user.setBO_ID(rs.getInt("maxid") + 1);

			}
			
			PreparedStatement stmt2 = con.prepareStatement(
					"INSERT INTO User (User_ID, Gmail) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			stmt2.setInt(1, user.getBO_ID());
			stmt2.setString(2, user.getGmail());
			stmt2.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();

		}
		return user;

	

	}

	
}
