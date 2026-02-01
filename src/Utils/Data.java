package Utils;
import Person.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase de utilidad encargada de la persistencia de datos del sistema.
 */
public class Data implements Serializable{
    ArrayList<Person> personsArray = new ArrayList<Person>();
    HashMap<String, String> debtors = new HashMap<String, String>();
    private static final File personsList = new File("Persons.dat");
    private static final File debtorsList = new File("Debtors.dat");
    private static final long serialVersionUID = 1L;


    /**
     * Carga la lista de personas desde el archivo binario "Persons.dat".
     * Si el archivo no existe o está vacío, devuelve una lista nueva y vacía.
     * @return Una ArrayList conteniendo los objetos Person.
     * @throws IOException Si ocurre un error de lectura en el archivo.
     * @throws ClassNotFoundException Si el objeto leído no coincide con la clase Person.
     */
    public ArrayList<Person> chargeData() {

        if (personsList.exists() && personsList.length() > 0) {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(personsList))) {
                personsArray = (ArrayList<Person>) input.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error charging Users " + e.getMessage());
            }
        }
        /*Gerente gerente = new Gerente("Alberto Acosta Aguilar", "123456zZ%", "01/01/1991", "00000001");
        personsArray.add(gerente);*/
        return personsArray;
    }

    /**
     * Guarda la lista completa de personas en el archivo binario.
     * Este proceso sobrescribe el archivo actual con la versión más reciente
     * @param personsArray La lista de objetos Person que se desea persistir.
     * @throws IOException Si ocurre un error de escritura en el archivo.
     */
    public void saveData(ArrayList<Person> personsArray){

        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(personsList))){
            output.reset();
            output.writeObject(personsArray);
            output.flush();
        }
        catch(IOException e){
            System.err.println("Error writing data " + e.getMessage());
        }
    }

    public HashMap<String, String> chargeDebtors() {

        if(debtorsList.exists() && debtorsList.length() > 0) {
            try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(debtorsList))){
                debtors = (HashMap<String, String>) input.readObject();
            }
            catch(IOException | ClassNotFoundException e) {
                System.err.println("Error charging Users data " + e.getMessage());
            }
        }
        return debtors;
    }

    public void saveDebtors(HashMap<String, String> debtors) {

        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(debtorsList))){
            output.reset();
            output.writeObject(debtorsList);
            output.flush();
        }
        catch(IOException e){
            System.err.println("Error writing data " + e.getMessage());
        }
    }
}