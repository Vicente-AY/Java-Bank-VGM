package Person;

/**
 * Clase con los atributos de una persona
 */
public abstract class Person {
    /**
     * @param name Cadena con el nombre del usuario
     * @param birthDate Cadena con la fecha de nacimiento del usuario
     * @param password Cadena con la contraseña del usuario
     * @param active Booleano que determina el estado de la cuenta
     */
    public String name="", birthDate ="", password="";
    public boolean active=true;

    /**
     * Constructor con parámetros
     * @param name Nombre del usuario
     * @param password Contraseña del usuario
     * @param birthDate Fecha de nacimiento del usuario
     */
    protected String id;


    public Person( String name, String password, String birthDate) {}

    /**
     * Metodo que registra al usuario
     * @return Nuevo usuario creado
     */
    public abstract Person register();

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
