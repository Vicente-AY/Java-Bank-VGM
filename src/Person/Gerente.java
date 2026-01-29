package Person;
import Utils.Data;
import Account.BankAccount;

import java.time.Year;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Representa al usuario con el rol de Gerente en el sistema bancario.
 * Hereda de Person y posee privilegios superiores para gestionar
 * tanto a empleados como cuentas.
 */
public class Gerente extends Person {
    String managerId;
    public static int id =0;
    private static final long serialVersionUID = 1L;

    /**
     * Constructor para inicializar un Gerente.
     * @param name       Nombre del Gerente.
     * @param password   Contraseña de seguridad.
     * @param birthDate  Fecha de nacimiento (dd/mm/yyyy).
     * @param managerId  ID específico asignado al gerente.
     */
    public Gerente(String name, String password, String birthDate, String managerId) {
        super(name, password, birthDate);
        this.managerId = managerId;
    }

    /**
     * Inicia el proceso de registro de un nuevo Gerente por consola.
     * Valida datos de seguridad y genera un ID de 8 dígitos de forma automática.
     * @return Una nuevo Gerente
     */
    @Override
    public void register(ArrayList<Person> persons) {

        Scanner sc = new Scanner(System.in);
        String name, birthdate, password;
        boolean checkP=false, checkD=false;
        System.out.println("Please Gerente enter your name and surnames");
        name = sc.nextLine();

        System.out.println("Please enter your password");
        password = sc.nextLine();
        checkP = checkPassword(password);
        while (!checkP){
            System.out.println("The password you entered is incorrect");
            System.out.println("The password must contain:");
            System.out.println("* 1 uppercase letter");
            System.out.println("* 1 lowercase letter");
            System.out.println("* 1 number");
            System.out.println("* 1 special character");
            password = sc.nextLine();
            checkP = checkPassword(password);
        }

        System.out.println("Please enter your birthdate (dd/mm/yyyy)");
        birthdate = sc.nextLine();
        checkD = checkDate(birthdate);
        while(!checkD){
            System.out.println("The date you entered is incorrect, please try again");
            System.out.println("Remember to use the following format: dd/mm/yyyy");
            birthdate = sc.nextLine();
            checkD = checkDate(birthdate);
        }
        int id = 0;
        int currentIdInt;
        if(!persons.isEmpty()) {
            for (Person employee : persons) {
                currentIdInt = Integer.parseInt(employee.getId());
                if (currentIdInt > id) {
                    id = currentIdInt;
                }
            }
        }
        String newId = createId(id + 1);
        Gerente newGerente = new Gerente(name, password, birthdate, newId);
        System.out.println("The register process has ended successfully");
        System.out.println("Your data:");
        System.out.println("Name: " + name);
        System.out.println("Birthdate: " + birthdate);
        System.out.println("Password: " + password);
        System.out.println("Id: " + newId);
        persons.add(newGerente);
    }

    /**
     * Valida la contraseña mediante expresión regular.
     * @param password Contraseña a verificar.
     * @return true si coincide con el patrón.
     */
    @Override
    public boolean checkPassword(String password){ //regex password
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if(password.matches(pattern)){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Valida la existencia real de una fecha y bisiestos.
     * @param date Fecha en String.
     * @return true si es válida.
     */
    @Override
    public boolean checkDate(String date){
        String regex = "[,//.\\s]";
        String[] myArray = date.split(regex);
        int element1 = Integer.parseInt(myArray[0]);
        int element2 = Integer.parseInt(myArray[1]);
        int element3 = Integer.parseInt(myArray[2]);
        int year = Year.now().getValue();

        if (element1 > 32 || element1 <0){//check if the day is between 1 and 31
            return false;
        }
        if(  element2 == 4 || element2 == 6 || element2 == 9 ||  element2 == 11 ){//check if it is a 30-day month
            if (element1 >30){
                return false;
            }
        }
        if (element2 == 2  ) { //check if february
            if (element3 % 4 == 0) {
                if (element1 > 29) {//leap year
                    return false;
                }
            } else {
                if (element1 > 28) {//normal year
                    return false;
                }
            }
        }
        if (element3 < 1900 || element3 > year) {
            return false;
        }
        return true;
    }

    /**Devuelve el id del Gerente*/
    @Override
    public String getId() {
        return managerId;
    }

    /**
     * Formatea un número entero a un ID de 8 caracteres con relleno de ceros.
     * @param id Número base.
     * @return String de 8 dígitos.
     */
    public String createId(int id) {

        return String.format("%08d", id);
    }

    /**
     * Permite eliminar una cuenta bancaria de un usuario seleccionado.
     * Requisito: La cuenta debe tener saldo 0.0 para poder ser borrada.
     */
    public void deleteBankAccount(ArrayList<Person> persons) {

        Scanner sc = new Scanner(System.in);

        //Busca al usuario por id
        System.out.println("Enter the User ID number you want to select");
        String userId = sc.nextLine();
        User selectedUser = null;
        BankAccount eraseAccount = null;

        for(Person person : persons) {
            if(person instanceof User && userId.equals(person.getId())) {
                selectedUser = (User) person;
                break;
            }
        }
        if(selectedUser == null) {
            System.out.println("Error: User not found");
            return;
        }
        //Selecciona la cuenta bancaria a eliminar
        if (selectedUser != null) {
            if(selectedUser.getBankAccounts().isEmpty()) {
                System.out.println("The user dont have linked bank accounts");
                return;
            }
            System.out.println("Bank Accounts from: " + selectedUser.getName());
            for (int i = 0; i < selectedUser.getBankAccounts().size(); i++) {
                System.out.println((i + 1) + ". Acc Num: " + selectedUser.getBankAccounts().get(i).getAccNumber());
            }
            System.out.println("Select the account you want to delete. Type 0 to cancell");
            int accountSelection = sc.nextInt();
            sc.nextLine();
            if (accountSelection == 0) {
                System.out.println("Operation aborted");
                return;
            }
            else {
                eraseAccount = selectedUser.getBankAccounts().get(accountSelection - 1);
                //si la cuenta bancaria tiene balance  positivo o negativo no podrá ser eliminada
                if (eraseAccount.getBalance() != 0) {
                    System.out.println("Cancelling operation, the account must be at 0 before deletion");
                }
                else {
                    System.out.println("Are you sure do you want to erase" + selectedUser.getName() + "´s bank account " + eraseAccount.getAccNumber() + "? (Y/N)");
                    String choice = sc.nextLine().toLowerCase();
                    if (choice.equals("y") || choice.equals("yes")) {
                        selectedUser.getBankAccounts().remove(accountSelection - 1);
                        System.out.println("Bank Account Deleted successfully");
                    }
                    else {
                        System.out.println("Operation aborted");
                    }
                }
            }
        }
        else{
            System.out.println("User not found");
        }
    }

    /**
     * Borra usuarios del sistema
     * @param persons ArrayList de todos los usuarios
     */
    public void deleteSystemAccount(ArrayList<Person> persons){

        Scanner sc = new Scanner(System.in);
        System.out.println("Tell me the System Account's ID");
        String id = sc.nextLine();
        Person removed = null;
        //buscamos al usuario por ID
        for(Person person : persons) {
            if(person.getId().equals(id)) {
                removed = person;
                break;
            }
        }
        if(removed == null) {
            System.out.println("ID "  + id + " not found");
            return;
        }
        //Si es un usuario y todavia tiene cuentas asociadas paramos la operación
        if(removed instanceof User) {
            if(!((User) removed).getBankAccounts().isEmpty()){
                System.out.println("Sorry, the client still have linked bank accounts. Delete them first");
                return;
            }
        }
        //nos aseguramos que el usuario que realiza la operacion quiera borrar al usuario seleccionado
        System.out.println("(Yes/No) Do you really want to delete: ");
        if(removed instanceof User) {
            System.out.println("User " + removed.getName() + "? With ID " + removed.getId());
        }
        if(removed instanceof Employee) {
            System.out.println("Employee " + removed.getName() + "? With ID " + removed.getId());
        }
        if(removed instanceof Gerente) {
            System.out.println("Manager " + removed.getName() + "? With ID " + removed.getId());
        }
        String decision = sc.nextLine().toLowerCase();
        if(decision.equals("yes")) {
            persons.remove(removed);
            System.out.println(removed.getName() + " has been removed");
        }
        else {
            System.out.println("Cancelling Operation");
        }
    }

    /**
     * Reactiva cuentas bloqueadas
     */
    public void reactivate(ArrayList<Person> persons){

        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce the ID you want to reactivate:");
        String id = sc.nextLine();
        Person personToReactivate = null;
        for(Person person : persons){
            if(person.getId().equals(id)){
                personToReactivate = person;
                break;
            }
        }
        if(personToReactivate == null){
            System.out.println("Person with ID " + id + " not found");
            return;
        }

        if(personToReactivate.getActive()){
            System.out.println(personToReactivate.getName() + " Is arleady active");
        }
        else{
            personToReactivate.setActive(true);
            System.out.println(personToReactivate.getName() + " has been reactivated successfully");
        }
    }

}