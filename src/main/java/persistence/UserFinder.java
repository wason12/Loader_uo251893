package persistence;

import java.util.List;
import model.User;
import persistence.util.Jpa;

public class UserFinder {

    /**
     * Método que encuentra el usuario por el identificador.
     * 
     * @param identificador
     * @return Devuelve usuarios que se corresponden con el identificador.
     */
    public static List<User> findByIdentificador(String identificador) {
	return Jpa.getManager().createNamedQuery("User.findByIdentificador", User.class).setParameter(1, identificador)
		.getResultList();
    }

    public static List<User> findByEmail(String email) {
	return Jpa.getManager().createNamedQuery("User.findByEmail", User.class).setParameter(1, email).getResultList();
    }

}
