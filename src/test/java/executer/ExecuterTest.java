package executer;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;

import com.lowagie.text.DocumentException;

import model.User;
import persistence.UserFinder;
import persistence.util.Jpa;

public class ExecuterTest {

    @Test
    public void testActionSingleton() throws FileNotFoundException, DocumentException, IOException {
	ActionSingleton aS = ActionSingleton.getInstance();
	ActionSingleton aS2 = ActionSingleton.getInstance();

	assertEquals(aS, aS2);

	User user = new User("Fernando Perez Menendez", "", "ferpm@gmail.com", "87654321P", "Person");
	;

	aS.getAF().saveData(user);

	EntityManager mapper = Jpa.createEntityManager();
	EntityTransaction trx = mapper.getTransaction();
	trx.begin();

	User user2 = UserFinder.findByEmail("ferpm@gmail.com").get(0);

	assertEquals(user, user2);

	trx.commit();

    }

}
