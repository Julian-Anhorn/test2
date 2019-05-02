
package de.hdm.softwarePraktikum.server.db;

import java.sql.Connection; 

import java.sql.DriverManager;



/**
 * Verwalten einer Verbindung zur Datenbank.
 * @author Thies
 */
public class DBConnection {

    /**
     * Die Klasse DBConnection wird nur einmal instantiiert. Man spricht hierbei
     * von einem sogenannten <b>Singleton</b>.
     * <p>
     * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
     * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
     * speichert die einzige Instanz dieser Klasse.
     * 

     */
    private static Connection con = null;

    private static String googleUrl = "jdbc:google:mysql://itotemic-phoenix-200816:europe-west3:itproject2018";
    private static String localUrl = "jdbc:mysql://127.0.0.1:3306/it_project_db_SS18?user=root&password=&serverTimezone=UTC";
   

   
    /**
     * Diese statische Methode kann aufgrufen werden durch
     * <code>DBConnection.connection()</code>. Sie stellt die
     * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
     * einzige Instanz von <code>DBConnection</code> existiert.
     * <p>
     * 
     * 
     * @return DAS <code>DBConncetion</code>-Objekt.
     * @see con
     */
    
    public static Connection connection() {
        // Wenn es bisher keine Conncetion zur DB gab, ...
        if (con == null) {
            String url = null;
            try {
               
                    // Local MySQL instance to use during development.
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    url = localUrl;
                
                /*
                 * Dann erst kann uns der DriverManager eine Verbindung mit den
                 * oben in der Variable url angegebenen Verbindungsinformationen
                 * aufbauen.
                 * 
                 * Diese Verbindung wird dann in der statischen Variable con
                 * abgespeichert und fortan verwendet.
                 */
                con = DriverManager.getConnection(url);
            } catch (Exception e) {
                con = null;
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        // Zurückgeben der Verbindung
        return con;
    }

}
