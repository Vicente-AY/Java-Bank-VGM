package Person;

import Account.BankAccount;

import java.time.Year;
import java.util.Scanner;

/**
 * Clase con los atributos de un empleado del banco
 */
public class Employee extends Person {
    /**
     * @param employeeId Número identificador del empleado
     * @param id Número identificador
     */
    final int employeeId;
    public static int id = 0;

    /**
     * Constructor con parámetros
     * @param id Cadena que almacena el número de identificación
     * @param employeeId Entero que establece el número identificador propio del empleado
     * @see Person
     */
    public Employee(String id, String name, String password, String birthDate, int employeeId) {
        super( name, password, birthDate);
        this.employeeId = employeeId;
    }

    /**
     * Metodo que permite al empleado registrar usuarios
     * @see Person
     * @see User
     */
    @Override
    public User register(){
        /**
         * @param newId Variable que crea un nuevo número identificador
         */
        Scanner sc = new Scanner(System.in);
        String name, birthdate, password;
        boolean checkP=false, checkD=false;
        System.out.println("Please enter your name and surnames");
        name = sc.nextLine();

        System.out.println("Please enter your password");
        password = sc.nextLine();
        checkPassword(password);
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

        System.out.println("Please enter your birthdate (dd/mm/yyyy)");
        birthdate = sc.nextLine();
        checkD = checkDate(birthdate);
        while(!checkD){
            System.out.println("The date you entered is incorrect, please try again");
            System.out.println("Remember to use the following format: dd/mm/yyyy");
            birthdate = sc.nextLine();
            checkD = checkDate(birthdate);
        }
         id += 1;
        String newId = createId(id);
        User newUser = new User(name, password, birthdate, newId);
        System.out.println("The register process has ended");
        System.out.println("Your data:");
        System.out.println("Name: " + name);
        System.out.println("Birthdate: " + birthdate);
        System.out.println("Password: " + password);
        System.out.println("Id: " + newId);
        return newUser;
    }

    /**
     * Metodo que comprueba la validez de la contraseña introducida
     * @see User
     * @see Person
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
     * Metodo que comprueba la validez de la fecha introducida
     * @see User
     * @see Person
     */
    @Override
    public boolean checkDate(String date){
        String regex = "[,\\.\\s]";
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
     * Metodo que permite al empleado crear un nuevo número identificador
     * @param id Entero con el número identificador
     * @return Número identificador nuevo
     */
    public String createId(int id){
        /**
         * @param newId Número identificador nuevo
         */
        String newId ="";
        for (int i= String.valueOf(id).length(); i < 8; i++){
            newId = "0" + newId;
        }
        return newId;
    }

    /**
     * Metodo para acceder a la creación de la cuenta bancaria
     * @return
     */
    public BankAccount createBankAccount(){
        return null;
    }
    // PERMISOS DE EMPLEADO
    public boolean canCreateAccount() {
        return true; // Los empleados SÍ pueden crear cuentas
    }

    public boolean canDeleteAccount() {
        return false; // Los empleados NO pueden borrar cuentas
    }

    public double getWithdrawalLimit() {
        return Double.MAX_VALUE; // Sin límite
    }

    public boolean canManageEmployees() {
        return false; // Solo gerentes pueden gestionar empleados
    }

    public boolean canUnblockAccounts() {
        return false; // Solo gerentes pueden desbloquear
    }

    // Método para crear cuenta bancaria para un cliente
    public BankAccount createBankAccountForClient(User client) {
        if (!canCreateAccount()) {
            System.out.println("You don't have permission to create accounts");
            return null;
        }
        System.out.println("Creating bank account for client: " + client.name);
        // Aquí iría la lógica real de creación
        return null;
    }

    @Override
    public String getId(){
        return String.valueOf(employeeId);
    }
}
