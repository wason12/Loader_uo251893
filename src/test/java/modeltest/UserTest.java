package modeltest;

import static org.junit.Assert.*;

import model.User;

import org.junit.Test;

public class UserTest {

    @Test
    public void testEquals() {
	User user1 = new User("Fernando Perez Menendez", "", "ferpm@gmail.com", "87654321P", "Person");
	User user2 = new User("Fernando Perez Menendez", "", "ferpm@gmail.com", "87654321P", "Person");
	User user3 = new User("Fernando Perez Menendez", "", "ferpm@gmail.com", "87658321P", "Person");
	User user4 = new User("Fernando Perez Menendez", "", "ferpm@gmail.com", "87654321P", "Sensor");

	assertEquals(true, user1.equals(user2));
	assertEquals(false, user4.equals(user3));
	assertNotNull(user1);
	// No entiendo este test
	assertEquals(false, user1.equals(new Integer(8)));
	assertEquals(false, user4.equals(user3));
    }

    @Test
    public void testHashCode() {
	User user1 = new User("Fernando Perez Menendez", "", "ferpm@gmail.com", "87654321P", "Person");
	User user2 = new User("Fernando Perez Menendez", "", "ferpm@gmail.com", "87654321P", "Person");
	User user3 = new User("Fernando Perez Menendez", "", "ferpm@gmail.com", "87654521P", "Person");
	User user4 = new User("Fernando Perez Menendez", "", "ferpm@gmail.com", "87654321P", "Entity");
	User user5 = new User("Fernando Perez Menendez", "", "ferpm@gmail.com", "87654321P", "Entity");
	;

	assertEquals(user1.hashCode(), user2.hashCode());
	assertEquals(user4.hashCode(), user5.hashCode());
	assertNotEquals(user2.hashCode(), user3.hashCode());

	System.out.println(user1.toString());
    }

    @Test
    public void testAll() {
	User user1 = new User("Fernando Perez Menendez", "", "ferpm@gmail.com", "87654321P", "Person");
	;

	String password = user1.getPassword();
	String userName = user1.getIdentificador();
	String toString = "User [id =" + null + ", nombre=Fernando Perez Menendez, localizacion=" + " "
		+ ", email=ferpm@gmail.com" + ", identificador=87654321P, tipo=Person]";

	assertEquals("Fernando Perez Menendez", user1.getNombre());
	assertEquals("ferpm@gmail.com", user1.getEmail());
	assertEquals("87654321P", user1.getIdentificador());
	assertEquals("Person", user1.getTipo());
	assertEquals(password, user1.getPassword());
	assertEquals(userName, user1.getIdentificador());
	assertEquals(toString, user1.toString());
    }

}
