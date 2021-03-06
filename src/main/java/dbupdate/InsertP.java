package dbupdate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import com.lowagie.text.DocumentException;

import model.User;
import parser.cartas.Letter;
import parser.cartas.PdfLetter;
import parser.cartas.TxtLetter;
import parser.cartas.WordLetter;
import persistence.UserFinder;
import persistence.util.Jpa;
import reportwriter.ReportWriter;

public class InsertP implements Insert {

    @Override
    public User save(User user) throws FileNotFoundException, DocumentException, IOException {
	EntityManager mapper = Jpa.createEntityManager();
	EntityTransaction trx = mapper.getTransaction();
	trx.begin();
	try {
	    if (!UserFinder.findByIdentificador(user.getIdentificador()).isEmpty()) {
		ReportWriter.getInstance().getWriteReport().log(Level.WARNING, "El usuario con el identificador "
			+ user.getIdentificador() + " ya existe en la base de datos");
		trx.rollback();
	    } else if (!UserFinder.findByEmail(user.getEmail()).isEmpty()) {
		ReportWriter.getInstance().getWriteReport().log(Level.WARNING,
			"Ya existe un usuario con el email " + user.getEmail() + " en la base de datos");
		trx.rollback();
	    } else {
		Jpa.getManager().persist(user);
		trx.commit();
		Letter letter = new PdfLetter();
		letter.createLetter(user);
		letter = new TxtLetter();
		letter.createLetter(user);
		letter = new WordLetter();
		letter.createLetter(user);
	    }
	} catch (PersistenceException ex) {
	    ReportWriter.getInstance().getWriteReport().log(Level.WARNING, "Error de la BBDD");
	    if (trx.isActive())
		trx.rollback();
	} finally {
	    if (mapper.isOpen())
		mapper.close();
	}
	return user;
    }

    @Override
    public List<User> findByIdentificador(String identificador) {
	return UserFinder.findByIdentificador(identificador);
    }

    @Override
    public List<User> findByEmail(String email) {
	return UserFinder.findByEmail(email);
    }
}
