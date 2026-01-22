package Person;

import Account.BankAccount;
import Utils.Data;
import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Representa a un empleado de la entidad bancaria.
 * Esta clase extiende Person y proporciona funcionalidades
 * como el registro de personal y validaciones de seguridad.
 * @see Person
 */
public class Employee extends Person {
    Data dataAccess = new Data();
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
    public Employee register(){

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
     * Convierte un número entero en un ID de empleado con formato de 8 dígitos.
     * @param id El número identificador.
     * @return Cadena de 8 caracteres como identificador final.
     */
    public String createId(int id){
        String newId ="";
        for (int i= String.valueOf(id).length(); i < 8; i++){
            newId = "0" + newId;
        }
        return newId;
    }

    /* PERMISOS DE EMPLEADO
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
    */

    /** @return El identificador de empleado. */
    @Override
    public String getId(){
        return employeeId;
    }
}
