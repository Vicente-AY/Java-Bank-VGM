package Utils;
import Person.*;
import Shop.ShopItem;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase de utilidad encargada de la persistencia de datos del sistema.
 */
public class Data implements Serializable{
    ArrayList<Person> personsArray = new ArrayList<Person>();
    HashMap<String, String> debtors = new HashMap<String, String>();
    ArrayList<ShopItem> shopItemsArray = new ArrayList<ShopItem>();
    private static final File personList = new File("Persons.dat");
    private static final File debtorList = new File("Debtors.dat");
    private static final File executionDay = new File("Execution.txt");
    private static final File productList = new File("Products.dat");
    private static final long serialVersionUID = 1L;

    /**
     * Carga la lista de personas desde el archivo binario "Persons.dat".
     * Si el archivo no existe o está vacío, devuelve una lista nueva y vacía.
     * @return Una ArrayList conteniendo los objetos Person.
     * @throws IOException Si ocurre un error de lectura en el archivo.
     * @throws ClassNotFoundException Si el objeto leído no coincide con la clase Person.
     */
    public ArrayList<Person> chargeData() {

        if (personList.exists() && personList.length() > 0) {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(personList))) {
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

        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(personList))){
            output.reset();
            output.writeObject(personsArray);
            output.flush();
        }
        catch(IOException e){
            System.err.println("Error writing data " + e.getMessage());
        }
    }

    /**
     * Carga la lista de personas con deudas pendientes en el archivo binario debtors.dat
     * @return HashMap debtors con el id del deudor y la fecha de inicio de la deuda
     */
    public HashMap<String, String> chargeDebtors() {

        if(debtorList.exists() && debtorList.length() > 0) {
            try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(debtorList))){
                debtors = (HashMap<String, String>) input.readObject();
            }
            catch(IOException | ClassNotFoundException e) {
                System.err.println("Error charging Users data " + e.getMessage());
            }
        }
        return debtors;
    }

    /**
     * Guarda la lista de deudores sobreescribiendo su anterior versión
     * @param debtors HashMap de deudores
     */
    public void saveDebtors(HashMap<String, String> debtors) {

        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(debtorList))){
            output.reset();
            output.writeObject(debtors);
            output.flush();
        }
        catch(IOException e){
            System.err.println("Error writing data " + e.getMessage());
        }
    }

    /**
     * Metodo que carga el ultimo dia que se ejecutaon las deudas
     * @return
     */
    public String chargeLastExecutionDay(){

        try(BufferedReader br = new BufferedReader(new FileReader(executionDay))){
            String lastDayExecution = br.readLine();
            return lastDayExecution;
        }
        catch(IOException ioe) {
            System.err.println("Error reading File " + ioe.getMessage());
            return "";
        }
    }

    /**
     * Guarda en archivo texto el ultimo dia que se ejecutó la deuda
     * @param lastExecutionDay
     */
    public void saveLastExecutionDay(String lastExecutionDay){

        try(PrintWriter out = new PrintWriter(new FileWriter(executionDay, false))){
            out.print(lastExecutionDay);
        }
        catch(IOException ioe) {
            System.err.println("Error writing data " + ioe.getMessage());
        }
    }

    /**
     * Carga los articulos de la tienda
     * @return
     */
    public ArrayList<ShopItem> chargeItems() {

        if (productList.exists() && productList.length() > 0) {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(productList))) {
                shopItemsArray = (ArrayList<ShopItem>) input.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error charging Users " + e.getMessage());
            }
        }
        return shopItemsArray;
    }

    /**
     * Guarda los cambios en los articulos de la tienda
     * @param shopItems
     */
    public void saveItems(ArrayList<ShopItem> shopItems){

        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(productList))){
            output.reset();
            output.writeObject(shopItems);
            output.flush();
        }
        catch(IOException e){
            System.err.println("Error writing data " + e.getMessage());
        }
    }
}