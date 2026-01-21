package Person;

/**
 * Clase con los atributos de una persona
 */
public abstract class Person {

    public String name="", birthDate ="", password="";
    public boolean active=true;


    protected String id;

    /**
     * Constructor con parámetros
     * @param name Nombre del usuario
     * @param password Contraseña del usuario
     * @param birthDate Fecha de nacimiento del usuario
     */
    public Person( String name, String password, String birthDate) {}

    /**
     * Metodo que registra al usuario
     * @return Nuevo usuario creado
     */
    abstract Person register();

    /**
     * Metodo que revisa la validez de la contraseña
     * @param password Contraseña del usuario
     * @return Validez de la contraseña
     */
    abstract boolean checkPassword(String password);

    /**
     * Metodo que revisa la validez de la fecha introducida
     * @param date Fecha introducida
     * @return Validez de la fecha
     */
    abstract boolean checkDate(String date);

    public abstract String getId();

}
