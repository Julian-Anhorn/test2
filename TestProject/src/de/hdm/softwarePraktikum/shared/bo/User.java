package de.hdm.softwarePraktikum.shared.bo;

public class User extends BusinessObject {
	

    /**
     * 
     */
    private String gmail;

    /**
     * @return Gibt die Gmail zurück
     */
    public String getGmail() {
        return gmail;
    }

    /**
     * Setzt Gmail 
     */
    public void setGmail(String value) {
        this.gmail = value;
    }
	
}
