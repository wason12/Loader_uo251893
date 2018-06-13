package parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import reportwriter.ReportWriter;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lowagie.text.DocumentException;

import executer.*;
import model.User;

public class RList implements ReadList {
	private ActionFacade aF = new ActionFacadeClass();
	private ArrayList<List<XSSFCell>> allUsers;

	private Map<Integer, String> agents;
	private final static String AGENTSFILE = "src/main/resources/availableAgents.csv";

	/**
	 * Lee el fichero excel de la ruta pasada por parametro Si el fichero no esta en
	 * formato excel, detiene la lectura y escribe en el log la causa del error. Va
	 * leyendo linea por linea(hay un usuario en cada linea): Para cada linea crea
	 * un objeto User y se lo pasa al metodo cargarDatos del AtionFacade. Si existe
	 * algun fallo de FORMATO se ignora esa linea y se pasa a la siguiente, ademas
	 * de escribir dicho error en el log.
	 * 
	 * @param path
	 *            ruta del fichero
	 * 
	 * @exception FileNotFoundException
	 *                No se encuentra el fichero excel
	 * @throws DocumentException
	 */
	@Override
	public void load(String path) throws FileNotFoundException, DocumentException {
		loadAgents();
		InputStream excelFile = null;
		XSSFWorkbook excel = null;
		allUsers = new ArrayList<List<XSSFCell>>();
		int i = 0;
		try {
			excelFile = new FileInputStream(path);
			excel = new XSSFWorkbook(excelFile);
			XSSFSheet sheet = excel.getSheetAt(0);
			XSSFRow row;
			XSSFCell cell;
			List<XSSFCell> user;
			Iterator<Row> rows = sheet.rowIterator();

			while (rows.hasNext()) {
				user = new ArrayList<XSSFCell>();
				row = (XSSFRow) rows.next();
				if (i > 0 && row.getCell(0) != null) {
					for (int k = 0; k < 5; k++) {
						cell = (XSSFCell) row.getCell(k, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						user.add(cell);
						System.out.print(cell.toString() + " ; ");
					}

					if (!agents.containsKey((int) user.get(4).getNumericCellValue()))
						throw new IOException("El valor tipo debe estar registrado.");

					System.out.println();
					if (comprobarLocalizacion(user.get(1).getStringCellValue(),
							(int) user.get(4).getNumericCellValue())) {
						allUsers.add(user);
						crearUsuarios(user);
					}
				}
				i++;
			}
		} catch (FileNotFoundException ex) {
			throw ex;
		} catch (IOException ioe) {
			System.err.println("Problema con la lectura del excel en la linea " + i);
			ReportWriter.getInstance().getWriteReport().log(Level.WARNING,
					"Problema con la lectura del excel en la linea " + i);
		} finally {
			if (excelFile != null) {
				try {
					excelFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (excel != null) {
				try {
					excel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void loadAgents() {
		BufferedReader br = null;
		agents = new HashMap<Integer, String>();

		try {
			br = new BufferedReader(new FileReader(AGENTSFILE));
			while (br.ready()) {
				String[] linea = br.readLine().split(",");
				Integer key = Integer.parseInt(linea[0]);
				String value = linea[1];
				agents.put(key, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ActionFacade getaF() {
		return aF;
	}

	public void setaF(ActionFacade aF) {
		this.aF = aF;
	}

	/**
	 * Método del año 2016/2017. private void crearUsuarios(List<XSSFCell> list)
	 * throws FileNotFoundException, DocumentException, IOException { User user =
	 * new User(list.get(0).getStringCellValue(), list.get(1).getStringCellValue(),
	 * list.get(2).getStringCellValue(), list.get(3).getDateCellValue(),
	 * list.get(4).getStringCellValue(),list.get(5).getStringCellValue(),
	 * list.get(6).getStringCellValue()); InsertR insert = new InsertR();
	 * insert.save(user); //getaF().saveData(user); }
	 **/

	// Sustituye al metodo comentador
	private void crearUsuarios(List<XSSFCell> list) throws FileNotFoundException, DocumentException, IOException {
		User user = new User(list.get(0).getStringCellValue(), list.get(1).getStringCellValue(),
				list.get(2).getStringCellValue(), list.get(3).getStringCellValue(),
				agents.get((int) list.get(4).getNumericCellValue()));
		InsertR insert = new InsertR();
		insert.save(user);

	}

	/**
	 * Metodo que comprueba cuando recibe un sensor si este tiene localización
	 * 
	 * @param localizacion
	 *            del usuario
	 * @param tipo
	 *            del usuario
	 * @return true si se puede crear el usuario, false si no
	 */
	private boolean comprobarLocalizacion(String localizacion, int tipo) {
		// Miro que sea un sensor
		if (tipo == 3) {
			// Si no contiene '&' la localizacion no será correcta
			if (localizacion.contains("&")) {
				try {
					String[] trozos = localizacion.split("&");
					Double.parseDouble(trozos[0]);
					Double.parseDouble(trozos[1]);
					return true;
				} catch (Exception e) {
					System.err.println("La localización no es correcta.");
					ReportWriter.getInstance().getWriteReport().log(Level.WARNING,
							"No se ha establecido la localización o esta es incorrecta, el sensor no se creará");
					return false;
				}
			} else {
				System.err.println("La localización no es correcta.");
				ReportWriter.getInstance().getWriteReport().log(Level.WARNING,
						"No se ha establecido la localización o esta es incorrecta, el sensor no se creará");
				return false;
			}
		} else {
			return true;
		}
	}

	public ArrayList<List<XSSFCell>> getAllUsers() {
		return allUsers;
	}

}
