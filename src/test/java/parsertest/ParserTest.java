package parsertest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import parser.*;
import persistence.UserFinder;
import persistence.util.Jpa;

import static org.junit.Assert.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.junit.After;
import org.junit.Test;

import com.lowagie.text.DocumentException;
import model.User;

public class ParserTest {

    @Test
    public void testLoadExcelExito() throws FileNotFoundException, DocumentException {
	RList ex = new RList();
	ex.load("src/test/resources/test2.xlsx");

	assertEquals(ex.getAllUsers().size(), 3);

	List<XSSFCell> list1 = ex.getAllUsers().get(0);
	List<XSSFCell> list2 = ex.getAllUsers().get(1);
	List<XSSFCell> list3 = ex.getAllUsers().get(2);
	StringBuilder st = new StringBuilder();

	for (int i = 0; i < list1.size(); i++) {
	    st.append(list1.get(i).toString() + " ");
	}

	assertEquals(st.toString(), "Juan Torres Pardo  juan@example.com 90500084Y 1.0 ");

	st = new StringBuilder();

	for (int i = 0; i < list2.size(); i++) {
	    st.append(list2.get(i).toString() + " ");
	}

	assertEquals(st.toString(), "Sensor temperatura S1 43.36&-5.85 luis@example.com ST1Oviedo 3.0 ");

	st = new StringBuilder();

	for (int i = 0; i < list3.size(); i++) {
	    st.append(list3.get(i).toString() + " ");
	}

	assertEquals(st.toString(), "Universidad de Oviedo 43.24&-5.78 uniovi@example.com Q3318001I 2.0 ");
    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadExcelFicheroNoEncontrado() throws FileNotFoundException, DocumentException {
	RList ex = new RList();
	ex.load("src/test/resources/fallo.xlsx");

	assertEquals(ex.getAllUsers().size(), 3);

	List<XSSFCell> list1 = ex.getAllUsers().get(0);
	List<XSSFCell> list2 = ex.getAllUsers().get(1);
	List<XSSFCell> list3 = ex.getAllUsers().get(2);
	StringBuilder st = new StringBuilder();

	for (int i = 0; i < list1.size(); i++) {
	    st.append(list1.get(i).toString() + " ");
	}

	assertEquals(st.toString(), "Juan Torres Pardo   juan@example.com 90500084Y	1 ");

	st = new StringBuilder();

	for (int i = 0; i < list2.size(); i++) {
	    st.append(list2.get(i).toString() + " ");
	}

	assertEquals(st.toString(), "Sensor temperatura S1 43,36&-5,85 luis@example.com ST1Oviedo 3 ");

	st = new StringBuilder();

	for (int i = 0; i < list3.size(); i++) {
	    st.append(list3.get(i).toString() + " ");
	}

	assertEquals(st.toString(), "Universidad de Oviedo 43,24&-5,78 uniovi@example.com Q3318001I 2 ");
    }

    @Test(expected = IOException.class)
    public void testLoadExcelErrorExcel() throws IOException, DocumentException {
	RList ex = new RList();
	ex.load("src/test/resources/vacio.xlsx");

	assertEquals(ex.getAllUsers().size(), 3);

	List<XSSFCell> list1 = ex.getAllUsers().get(0);
	List<XSSFCell> list2 = ex.getAllUsers().get(1);
	List<XSSFCell> list3 = ex.getAllUsers().get(2);
	StringBuilder st = new StringBuilder();

	for (int i = 0; i < list1.size(); i++) {
	    st.append(list1.get(i).toString() + " ");
	}

	assertEquals(st.toString(), "Juan Torres Pardo   juan@example.com 90500084Y 1 ");

	st = new StringBuilder();

	for (int i = 0; i < list2.size(); i++) {
	    st.append(list2.get(i).toString() + " ");
	}

	assertEquals(st.toString(), "Sensor temperatura S1 43,36&-5,85 luis@example.com ST1Oviedo 3 ");

	st = new StringBuilder();

	for (int i = 0; i < list3.size(); i++) {
	    st.append(list3.get(i).toString() + " ");
	}

	assertEquals(st.toString(), "Universidad de Oviedo 43,24&-5,78 uniovi@example.com Q3318001I 2 ");
    }

    @Test
    public void testReaderSingleton() throws DocumentException {
	ReaderSingleton rS = ReaderSingleton.getInstance();
	rS.loadFile("cadenaIncorrecta");
	rS.loadFile("test.xlsx");
	ReaderSingleton rS1 = ReaderSingleton.getInstance();
	rS1.loadFile("cadenaIncorrecta");
	rS1.loadFile("test.xlsx");
	assertEquals(rS, rS1);
    }

    @After
    public void deleting() {
	EntityManager mapper = Jpa.createEntityManager();
	EntityTransaction trx = mapper.getTransaction();
	trx.begin();
	List<User> aBorrar = UserFinder.findByIdentificador("Q3318001I");
	if (!aBorrar.isEmpty())
	    Jpa.getManager().remove(aBorrar.get(0));

	aBorrar = UserFinder.findByIdentificador("ST1Oviedo");
	if (!aBorrar.isEmpty())
	    Jpa.getManager().remove(aBorrar.get(0));

	aBorrar = UserFinder.findByIdentificador("90500084Y");
	if (!aBorrar.isEmpty())
	    Jpa.getManager().remove(aBorrar.get(0));

	trx.commit();
	mapper.close();

    }
}
