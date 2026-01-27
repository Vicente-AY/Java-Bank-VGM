package Person;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase abstracta que sirve como base para todos los tipos de usuarios del sistema.
 * Define los atributos comunes de identidad y los métodos de validación
 */
public abstract class Person implements Serializable {

    public String name="", birthDate ="", password="";
    public boolean active=true;

    /**
     * Constructor para inicializar los datos básicos de una persona.
     * @param name      Nombre y apellidos del usuario.
     * @param password  Clave de seguridad del usuario.
     * @param birthDate Fecha de nacimiento del usuario.
     */
    public Person( String name, String password, String birthDate) {
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
    }

    /**
     * Metodo abstracto que debe implementar la lógica de registro
     * específica según el tipo de persona (Cliente, Empleado o Gerente).
     * @return Una nueva instancia del objeto que extiende de Person.
     */
    public abstract void register(ArrayList<Person> persons);

    /**
     * Define los requisitos que debe cumplir la contraseña del usuario.
     * @param password La cadena de texto a validar.
     * @return true si la contraseña cumple con las reglas.
     */
    abstract boolean checkPassword(String password);

    /**
     * Valida que la fecha introducida sea correcta y cumpla con el formato.
     * @param date La fecha de cumpleaños de la Persona
     * @return true si la fecha existe y es válida; false si el formato o día son incorrectos.
     */
    abstract boolean checkDate(String date);

    public abstract String getId();

    public String getName(){
        return name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive(){
        return active;
    }
}
