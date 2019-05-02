package de.hdm.softwarePraktikum.server.db;

import java.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.softwarePraktikum.shared.bo.Property;


/**
 * Mapper Klasse für </code>Property</code> Objekte.
 * Diese umfasst  Methoden um Property Objekte zu erstellen, zu suchen, zu  modifizieren und zu löschen.
 * Das Mapping funktioniert dabei bidirektional. Es können Objekte in DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @autor Julian Anhorn
 */
public class PropertyMapper {


	/**
	 * Speicherung der Instanz dieser Mapperklasse
	 */
	private static PropertyMapper propertyMapper = null;

     /** 
     * @return PropertyMapper
     * Durch diese Methode wird Singleton-Eigenschaft der Mapperklasse sichergestellt
     */
    
  

    /**
	// Geschützter Konstrukter verhindert weitere Instanzierungen von Property
	 * 
     */
	protected PropertyMapper() {
	}

	  public static PropertyMapper propertyMapper() {
      	
		   	 if (propertyMapper == null) {
		   		propertyMapper = new PropertyMapper();
			    }
		    	    	
		        return propertyMapper;
		    }
    /**
     * @param int id 
     * @return Property Objekt
     * 
     * Methode um ein einzelnes Property Objekt anhand einer ID  suchen.
     * 
     */
    public Property findById(int id) {
       Connection con = DBConnection.connection();
       try {
    	      Statement stmt = con.createStatement();
    	      ResultSet rs = stmt.executeQuery("SELECT * FROM Property"
    	                                      + " WHERE Property_ID=" + id);
    	      
    	      if(rs.next()){
    		Property p = new Property();
    		p.setBO_ID(rs.getInt("Property_ID"));
    		p.setTerm(rs.getString("Term"));
    		 return p;
    	      }
    	     
       }
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
        return null;
    }



    /** Methode um alle in der Datenbank vorhandene Property Datensätze abzurufen.
     * Diese werden als einzelene Property Objekte innerhalb einer ArrayList pl zurückgegeben.
     * 
     * @return ArrayList aller Properties
     */
    public ArrayList<Property> findAllProperties() {
  
    	Connection con = DBConnection.connection();
    	//PropertyListe
    	ArrayList<Property> plist = new ArrayList<Property>();
    	  try {
      	    
    		 
    	      Statement stmt = con.createStatement();
    	      ResultSet rs = stmt.executeQuery("SELECT * FROM Property");
    	      
    	      while(rs.next()) {
    	    		Property p = new Property();
    	    	    
    	    		p.setBO_ID(rs.getInt("Property_ID"));
    	    		p.setTerm(rs.getString("Term"));
    	    		plist.add(p);
    	    	}
    	
    	  }
    	  catch(SQLException e) {
      		e.printStackTrace();
      	}
		return plist;
 
    }



    /**Methode um ein Property Datensatz in der Datenbank zu löschen
     * @param p 
     */
    public void delete(Property p) {
      
    	Connection con = DBConnection.connection();
    	
    	  try {
      	    Statement stmt = con.createStatement();
    	    stmt.executeQuery("DELETE FROM Property WHERE Property_ID ="+p.getBO_ID());
    }
    	  catch(SQLException e) {
        		e.printStackTrace();
        	}
    }

    /**
     * Methode um ein Property Datensatz in der Datenbank zu ändern
     * @param Property p 
     * @return Property
     */
    public Property update(Property p) {
    
    	Connection con = DBConnection.connection();
    	
  	  try {
    	Statement stmt = con.createStatement();
    	stmt.executeUpdate("UPDATE Property SET Term = '"+ p.getTerm()+"' WHERE Property_ID = "+p.getBO_ID());
  	  
  }
  	  catch(SQLException e) {
      		e.printStackTrace();
      	}
        return p;
    }

	/**
	 * @param p
	 * @return
	 */
	public Property findByObject(Property p) {
		// TODO implement here
		return null;
	}

    /**
     * Methode um ein Property Objekt in der Datenbank zu speichern.
     * 
     * @param Property p 
     * @return Property
     */
    public Property insert(Property p) {
    	Connection con = DBConnection.connection();
    	
    	try {
			 
			Statement stmt = con.createStatement();
		      ResultSet rs = stmt.executeQuery("SELECT MAX(Property_ID) AS maxid "
		          + "FROM Property");
		    

		      if (rs.next()) {
		      
		      p.setBO_ID(rs.getInt("maxid") + 1);

		      }
    		  	  
 
    	PreparedStatement stmt2 = con.prepareStatement(
				"INSERT INTO Property (Property_ID, Term) VALUES (?, ?)",
				Statement.RETURN_GENERATED_KEYS);

				stmt2.setInt(1, p.getBO_ID());
				stmt2.setString(2, p.getTerm());
				stmt2.executeUpdate();
    	  }
    	  catch(SQLException e) {
        		e.printStackTrace();
        	}	
				
				
        return p;
    }

}