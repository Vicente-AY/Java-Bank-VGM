package Person;

import Account.BankAccount;
import Utils.Data;
import java.time.Year;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Representa a un empleado de la entidad bancaria.
 * Esta clase extiende Person y proporciona funcionalidades
 * como el registro de personal y validaciones de seguridad.
 * @see Person
 */
public class Employee extends Person {
    transient Data dataAccess = new Data();
    private static final long serialVersionUID = 1L;
    String employeeId;

    /**
     * Constructor para inicializar un empleado.
     * @param name       Nombre completo del empleado.
     * @param password   Contraseña de acceso al sistema.
     * @param birthDate  Fecha de nacimiento en formato dd/mm/yyyy.
     * @param employeeId Identificador único asignado al empleado.
     */
    public Employee(String name, String password, String birthDate, String employeeId) {
        super(name, password, birthDate);
        this.employeeId = employeeId;
        this.active = true;
    }

    /**
     * Gestiona el proceso de registro de un nuevo empleado.
     * Incluye la captura de datos por consola, validación de contraseña y fecha,
     * y la generación automática de ID.
     * @return Un nuevo empleado.
     * @see #checkPassword(String)
     * @see #checkDate(String)
     */
    @Override
    public Employee register(ArrayList<Person> persons){

        Scanner sc = new Scanner(System.in);
        String name, birthdate, password;
        boolean checkP=false, checkD=false;
        System.out.println("Please enter your name and surnames");
        name = sc.nextLine();

        //Validación de contraseña
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
            checkPassword(password);
        }

        //Validación de fecha
        System.out.println("Please enter your birthdate (dd/mm/yyyy)");
        birthdate = sc.nextLine();
        checkD = checkDate(birthdate);
        while(!checkD){
            System.out.println("The date you entered is incorrect, please try again");
            System.out.println("Remember to use the following format: dd/mm/yyyy");
            birthdate = sc.nextLine();
            checkD = checkDate(birthdate);
        }
        ArrayList<Person> personsArray = dataAccess.chargeData();
        ArrayList<Person> EmployeeArray = new ArrayList<>();
        for(Person person : personsArray) {
            if(person instanceof Employee || person instanceof Gerente){
                EmployeeArray.add(person);
            }
        }
        int id = 1;
        if(!EmployeeArray.isEmpty()) {
            for (Person employee : EmployeeArray) {
                if (createId(id).equals(employee.getId())) {
                    id++;
                }
            }
        }
        String newId = createId(id);
        Employee newEmployee = new Employee(name, password, birthdate, newId);
        System.out.println("The register process has ended successfully");
        System.out.println("Your data:");
        System.out.println("Name: " + name);
        System.out.println("Birthdate: " + birthdate);
        System.out.println("Password: " + password);
        System.out.println("Id: " + newId);
        return newEmployee;
    }

    /**
     * Valida la contraseña mediante una expresión regular (Regex).
     * Requisitos: Al menos una mayúscula, una minúscula, un número, un carácter especial y 8 caracteres.
     * @param password La cadena de texto a validar.
     * @return true si cumple los requisitos de seguridad, false en caso contrario.
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
     * Valida que una fecha sea cronológicamente correcta.
     * Comprueba límites de meses, días según el mes y años bisiestos para febrero.
     * @param date Fecha en formato String (dd/mm/yyyy).
     * @return true si la fecha es válida y coherente, false si no.
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

    /**
     * Convierte un número entero en un ID de empleado con formato de 8 dígitos.
     * @param id El número identificador.
     * @return Cadena de 8 caracteres como identificador final.
     */
    public String createId(int id) {

        return String.format("%08d", id);
    }


    /**
     * Borra las cuenta bancarias de los clientes
     * @param persons la lista de usuarios
     */
    public void DeleteBankAccount(ArrayList<Person> persons){
        Scanner sc = new Scanner(System.in);
        System.out.println("Tell me the Client's ID");
        String comprovId = sc.nextLine();
        boolean encontrado = false;
        ArrayList<Person> userList = new ArrayList<>();

        for(Person person : persons){
            if(person instanceof User){
                userList.add(person);
            }
        }

        for (int i = 0; i < userList.size(); i++) {
            if (Objects.equals(userList.get(i).getId(), comprovId)) {
                encontrado = true;
                Person selectedUser = userList.get(i);
                System.out.println("Client with ID " + comprovId + " exists");

                // Mostrar las cuentas bancarias del usuario
                if(((User) selectedUser).getBankAccounts().isEmpty()){
                    System.out.println("This client has no bank accounts");
                    return;
                }

                System.out.println("Bank accounts for client " + selectedUser.name + ":");
                for(int j = 0; j < ((User) selectedUser).getBankAccounts().size(); j++){
                    BankAccount account = ((User) selectedUser).getBankAccounts().get(j);
                    System.out.println((j+1) + ". Alias: " + account.accountAlias +
                            " | Balance: " + account.getBalance());
                }

                System.out.println("Which bank account do you want to delete?");
                System.out.println("Tell Alias of the Bank Account (or type 'cancel' to abort):");
                String comprovBA = sc.nextLine();

                if(comprovBA.equalsIgnoreCase("cancel")){
                    System.out.println("Operation cancelled");
                    return;
                }

                // Buscar la cuenta bancaria por alias
                BankAccount accountToDelete = null;
                for(BankAccount account : ((User) selectedUser).getBankAccounts()){
                    if(account.accountAlias.equals(comprovBA)){
                        accountToDelete = account;
                        break;
                    }
                }

                if(accountToDelete != null){
                    if(accountToDelete.getBalance() > 0){
                        System.out.println("ERROR: Cannot delete account with balance greater than 0");
                        System.out.println("Current balance: " + accountToDelete.getBalance());
                        return;
                    }

                    System.out.println("Are you sure you want to delete account '" +
                            accountToDelete.accountAlias + "'? (Y/N)");
                    String confirmation = sc.nextLine().toLowerCase();

                    if(confirmation.equals("y") || confirmation.equals("yes")){
                        ((User) selectedUser).getBankAccounts().remove(accountToDelete);
                        System.out.println("[MANAGER] Bank account '" + accountToDelete.accountAlias +
                                "' has been deleted successfully");
                    } else {
                        System.out.println("Operation cancelled");
                    }
                } else {
                    System.out.println("Bank account with alias '" + comprovBA + "' not found");
                }
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Client with ID " + comprovId + " not found");
        }
    }

    /**
     * Reactiva cuentas de clientes bloqueadas
     * @param persons Lista de usuarios
     */
    public void ReactivateClientAccount(ArrayList<Person> persons){
        Scanner sc = new Scanner(System.in);
        System.out.println("=== REACTIVATE CLIENT ACCOUNT ===");
        System.out.println("Enter the Client's ID:");
        String clientId = sc.nextLine();
        ArrayList <Person> userList = new ArrayList<>();

        for(Person person : persons){
            if(person instanceof User){
                userList.add(person);
            }
        }

        boolean found = false;
        for(Person user : userList){
            if(user.getId().equals(clientId)){
                found = true;
                if(user.active){
                    System.out.println("Client " + user.name + " (ID: " + clientId +
                            ") is already active");
                } else {
                    user.active = true;
                    System.out.println("[MANAGER] Client " + user.name + " (ID: " + clientId +
                            ") has been reactivated successfully");
                }
                break;
            }
        }

        if(!found){
            System.out.println("Client with ID " + clientId + " not found");
        }
    }

    /** @return El identificador de empleado. */
    @Override
    public String getId(){
        return employeeId;
    }
}
