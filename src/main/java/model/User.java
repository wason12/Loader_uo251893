package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Location localizacion;
    private String email;
    private String identificador; // Es unico y es el nombre de usuario
    private String password;
    private String tipo;

    /**
     * Constructor vacio
     */
    User() {
    }

    /**
     * Constructor
     * 
     * @param nombre
     * @param localizacion
     * @param email
     * @param identificador
     * @param tipo
     */
    public User(String nombre, String localizacion, String email, String identificador, String tipo) {
	setNombre(nombre);
	setLocalizacion(obtenerLocalizacion(localizacion));
	setEmail(email);
	setIdentificador(identificador);
	setTipo(tipo);
	generarPassword();
    }

    public String getNombre() {
	return nombre;
    }

    private void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public Location getLocalizacion() {
	return localizacion;
    }

    private void setLocalizacion(Location localizacion) {
	this.localizacion = localizacion;
    }

    public String getEmail() {
	return email;
    }

    private void setEmail(String email) {
	this.email = email;
    }

    public String getIdentificador() {
	return identificador;
    }

    private void setIdentificador(String identificador) {
	this.identificador = identificador;
    }

    public String getPassword() {
	return password;
    }

    private void setPassword(String password) {
	this.password = password;
    }

    public String getTipo() {
	return tipo;
    }

    private void setTipo(String tipo) {
	this.tipo = tipo;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((identificador == null) ? 0 : identificador.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	User other = (User) obj;
	if (identificador == null) {
	    if (other.identificador != null)
		return false;
	} else if (!identificador.equals(other.identificador))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "User [id =" + id + ", nombre=" + nombre + ", localizacion=" + localizacion.toString() + ", email="
		+ email + ", identificador=" + identificador + ", tipo=" + tipo + "]";
    }

    /**
     * Método para generar una contraseña aleatoria
     */
    private void generarPassword() {
	StringBuffer pass = new StringBuffer();
	int low = 65;
	int top = 90;
	for (int i = 0; i < 9; i++) {
	    int numAleatorio = (int) Math.floor(Math.random() * (top - low) + low);
	    pass.append((char) numAleatorio);
	}
	for (int i = 0; i < 3; i++) {
	    int numAleatorio = (int) Math.floor(Math.random() * (9 - 0) + 0);
	    pass.append(numAleatorio);
	}
	setPassword(pass.toString());
    }

    /**
     * Método para obtener la localizacion del usuario a partir del string que se
     * pasa por parámetro
     * 
     * @param loc
     * @return localizacion del usuario
     */
    private Location obtenerLocalizacion(String loc) {
	if (loc.equals("")) {
	    Location l = new Location(0, 0);
	    l.setExist(false);
	    return l;
	}
	String[] trozos = loc.split("&");
	double latitud = Double.parseDouble(trozos[0]);
	double longitud = Double.parseDouble(trozos[1]);
	return new Location(latitud, longitud);
    }
}
