package de.hdm.softwarePraktikum.shared.bo;

public class User extends BusinessObject {
	

    /**
     * 
     */
    private String gmail;

    /**
     * @return Gibt die Gmail zur�ck
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
