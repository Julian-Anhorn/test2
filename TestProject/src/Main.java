import de.hdm.softwarePraktikum.server.db.ContactMapper;
import de.hdm.softwarePraktikum.server.db.DBConnection;
import de.hdm.softwarePraktikum.server.db.ListStructureMapper;
import de.hdm.softwarePraktikum.server.db.ParticipationMapper;
import de.hdm.softwarePraktikum.server.db.PropertyMapper;
import de.hdm.softwarePraktikum.server.db.PropertyValueMapper;
import de.hdm.softwarePraktikum.shared.bo.Contact;
import de.hdm.softwarePraktikum.shared.bo.ContactList;
import de.hdm.softwarePraktikum.shared.bo.Property;
import de.hdm.softwarePraktikum.shared.bo.PropertyValue;
import de.hdm.softwarePraktikum.shared.bo.User;

public class Main {
public static void main(String[] args) {
	
	
	DBConnection con = new DBConnection();
	con.connection();
	
	//1. Nutzer Objekt erstellen über Scanner
	
	
	Contact c = new Contact();
	User u = new User();
	u.setBO_ID(1);
	c.setBO_ID(4);
	PropertyValue pv = new PropertyValue();
	pv.setValue("Neeeu");
	pv.setOwner(u);
	
	ContactList cl = new ContactList();
	cl.setBO_ID(8);
	Property p = new Property();
	p.setBO_ID(1);
	
System.out.println(ListStructureMapper.listStructureMapper().findAllContactsForContactList(cl));

}
}
