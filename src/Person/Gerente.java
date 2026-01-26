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

    /**
     * Constructor para inicializar un Gerente.
     * @param name       Nombre del Gerente.
     * @param password   Contraseña de seguridad.
     * @param birthDate  Fecha de nacimiento (dd/mm/yyyy).
     * @param gerenteId  ID específico asignado al gerente.
     */
    public Gerente(String name, String password, String birthDate, String gerenteId) {
        super(name, password, birthDate);
        this.managerId = managerId;
    }

    /**
     * Inicia el proceso de registro de un nuevo Gerente por consola.
     * Valida datos de seguridad y genera un ID de 8 dígitos de forma automática.
     * @return Una nuevo Gerente
     */
    @Override
    public Gerente register(ArrayList<Person> persons) {
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
        ArrayList<Person> EmployeeArray = new ArrayList<>();
        for(Person person : persons) {
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
        Gerente newGerente = new Gerente(name, password, birthdate, newId);
        System.out.println("The register process has ended successfully");
        System.out.println("Your data:");
        System.out.println("Name: " + name);
        System.out.println("Birthdate: " + birthdate);
        System.out.println("Password: " + password);
        System.out.println("Id: " + newId);
        return newGerente;
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
    private void deleteBankAccount(){
        ArrayList<User> users = new ArrayList<>();
        //Aqui cargar datos de usuarios
        System.out.println("Enter the User ID number you want to select");
        Scanner sc = new Scanner(System.in);
        String userSelection = sc.nextLine();
        User selectedUser = null;
        BankAccount eraseAccount = null;
        for(User user : users){
            if(userSelection.equals(user.getId())){
                selectedUser = user;
                break;
            }
        }
        if(selectedUser != null){
            for(int i = 0; i < selectedUser.getBankAccounts().size(); i++){
                System.out.println((i + 1) + ". " + selectedUser.getBankAccounts().get(i));
            }
            System.out.println("Select the account you want to delete. Type 0 to cancell");
            Scanner sc2 = new Scanner(System.in);
            int accountSelection = sc2.nextInt();
            if(accountSelection == 0){
                System.out.println("Operation aborted");
                return;
            }
            else {
                eraseAccount = selectedUser.getBankAccounts().get(accountSelection -1);
                if (eraseAccount.getBalance() > 0) {
                    System.out.println("Cancelling operation, the account must be at 0 before deletion");
                    return;
                }
                else {
                    System.out.println("Are you sure do you want to erase Y/N");
                    String choice = sc.nextLine().toLowerCase();
                    if (choice.equals("y") || choice.equals("yes")) {
                        //borrar cuenta bancaria
                    }
                    else{
                        System.out.println("Operation aborted");
                        return;
                    }
                }
            }
        }
    }

    /**
     * Desbloquea el acceso de un usuario que haya fallado sus intentos de login.
     * @param blockedUser Instancia del usuario a reactivar.
     */
    private void unlockAccount(User blockedUser){
        blockedUser.setActive(true);
        System.out.println(blockedUser.getName() + " Has been unlocked");
    }

    public void listOfClients(ArrayList<Person> persons){
        for(Person person : persons){
            System.out.println("- - - -");
            System.out.println(person.getName());
        }
    }

    /**
     * Crea empleados
     */
    private void CreateEmployee(ArrayList<Person> persons){
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
        ArrayList<Person> EmployeeArray = new ArrayList<>();
        for(Person person : persons) {
            if(person instanceof Employee || person instanceof Gerente){
                EmployeeArray.add(person);
            }
        }
        int id = 1;
        if(!EmployeeArray.isEmpty()) {
            for (Person customer : EmployeeArray) {
                if (customer.getId().equals(String.valueOf(id))) {
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
    }

    /**
     * Crea gerentes
     */
    private void CreateManager(ArrayList<Person> persons){
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
        while(!checkD) {
            System.out.println("The date you entered is incorrect, please try again");
            System.out.println("Remember to use the following format: dd/mm/yyyy");
            birthdate = sc.nextLine();
            checkD = checkDate(birthdate);
        }
        ArrayList<Person> ManagerArray = new ArrayList<>();
        for(Person person : persons) {
            if(person instanceof Employee || person instanceof Gerente){
                ManagerArray.add(person);
            }
        }
        int id = 1;
        if(!ManagerArray.isEmpty()) {
            for (Person customer : ManagerArray) {
                if (customer.getId().equals(String.valueOf(id))) {
                    id++;
                }
            }
        }
        String newId = createId(id);
        Gerente newGerente = new Gerente(name, password, birthdate, newId);
        System.out.println("The register process has ended successfully");
        System.out.println("Your data:");
        System.out.println("Name: " + name);
        System.out.println("Birthdate: " + birthdate);
        System.out.println("Password: " + password);
        System.out.println("Id: " + newId);
    }

    /**
     * Borra empleados
     */
    private void DeleteEmployee(ArrayList<Person> persons ){
        Scanner sc = new Scanner(System.in);
        System.out.println("Tell me the Employee's ID");
        String comprovId = sc.nextLine();
        boolean encontrado = false;
        Person removed = null;
        for (int i = 0; i < persons.size(); i++) {
            if (Objects.equals(persons.get(i).getId(), comprovId)) {
                removed = persons.remove(i);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Manager with ID " + managerId + " not found");
        }
        else {
            persons.remove(removed);
            System.out.println("[MANAGER] Manager " + removed.name + " (ID: " + comprovId + ") has been removed");
        }

    }

    /**
     * Borra gerentes
     * @param managerId el id que tiene el gerente
     */
    private void DeleteManager(ArrayList<Person> persons, String managerId){
        Scanner sc = new Scanner(System.in);
        System.out.println("Tell me the Manager's ID");
        String comprovId = sc.nextLine();
        boolean encontrado = false;
        Person removed = null;
        for (int i = 0; i < persons.size(); i++) {
            if (Objects.equals(persons.get(i).getId(), comprovId)) {
                removed = persons.remove(i);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Manager with ID " + managerId + " not found");
        }
        else {
            persons.remove(removed);
            System.out.println("[MANAGER] Manager " + removed.name + " (ID: " + comprovId + ") has been removed");
        }
    }

    /**
     * Borra las cuenta bancarias de los clientes
     */
    private void DeleteBankAccount(ArrayList<Person> persons){
        Scanner sc = new Scanner(System.in);
        System.out.println("Tell me the Client's ID");
        String comprovId = sc.nextLine();
        boolean encontrado = false;
        Person selectedUser = null;

        for (int i = 0; i < persons.size(); i++) {
            if (Objects.equals(persons.get(i).getId(), comprovId)) {
                encontrado = true;
                selectedUser = persons.get(i);
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
     */
    private void ReactivateClientAccount(ArrayList<Person> persons){
        Scanner sc = new Scanner(System.in);
        System.out.println("=== REACTIVATE CLIENT ACCOUNT ===");
        System.out.println("Enter the Client's ID:");
        String clientId = sc.nextLine();

        boolean found = false;
        for(Person user : persons){
            if(user.getId().equals(clientId)){
                found = true;
                if(user.active){
                    System.out.println("Client " + user.name + " (ID: " + clientId +
                            ") is already active");
                } else {
                    user.setActive(true);
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

    /**
     * Reactiva cuentas de empleados bloqueadas
     */
    private void ReactivateEmployee(ArrayList<Person> persons){
        Scanner sc = new Scanner(System.in);
        System.out.println("=== REACTIVATE EMPLOYEE ACCOUNT ===");
        System.out.println("Enter the Employee's ID:");
        String employeeId = sc.nextLine();

        boolean found = false;
        for(Person employee : persons){
            if(employee.getId().equals(employeeId)){
                found = true;
                if(employee.active){
                    System.out.println("Employee " + employee.name + " (ID: " + employeeId +
                            ") is already active");
                } else {
                    employee.setActive(true);
                    System.out.println("[MANAGER] Employee " + employee.name + " (ID: " +
                            employeeId + ") has been reactivated successfully");
                }
                break;
            }
        }

        if(!found){
            System.out.println("Employee with ID " + employeeId + " not found");
        }
    }

    /**
     * Reactiva cuentas de gerentes bloqueadas
     */
    private void ReactivateManager(ArrayList<Person> persons){
        Scanner sc = new Scanner(System.in);
        System.out.println("=== REACTIVATE MANAGER ACCOUNT ===");
        System.out.println("Enter the Manager's ID:");
        String managerId = sc.nextLine();

        boolean found = false;
        for(Person gerente : persons){
            if(gerente.getId().equals(managerId)){
                found = true;
                if(gerente.active){
                    System.out.println("Manager " + gerente.name + " (ID: " + managerId +
                            ") is already active");
                } else {
                    gerente.setActive(true);
                    System.out.println("[MANAGER] Manager " + gerente.name + " (ID: " +
                            managerId + ") has been reactivated successfully");
                }
                break;
            }
        }

        if(!found){
            System.out.println("Manager with ID " + managerId + " not found");
        }

    }

}