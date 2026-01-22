package Person;
import Account.BankAccount;
import Utils.Data;
import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Representa a un cliente final del banco.
 * Proporciona funcionalidades para el registro personal, gestión de contraseñas
 * y mantiene un registro de las cuentas bancarias de las que es titular.
 * @see Person
 */
public class User extends Person {

    Data dataAccess = new Data();
    Scanner input = new Scanner(System.in);
    public String userId = "";
    public ArrayList<BankAccount> bankAccounts = new ArrayList<>();

    /**
     * Constructor para inicializar un usuario con sus datos básicos.
     * @param name      Nombre completo del usuario.
     * @param password  Contraseña de acceso.
     * @param birthDate Fecha de nacimiento (dd/mm/yyyy).
     * @param userId    ID único de cliente.
     */
    public User(String name, String password, String birthDate, String userId) {

        super(name, password, birthDate);
        this.active = true;
        this.userId = userId;
    }

    /**
     * Gestiona el proceso de registro de un nuevo cliente, pidiedo datos por consola.
     * @return Una nueva instancia de {@link User} con los datos validados.
     */
    @Override
    public User register() {
        /**
         * @param sc Variable que llama al escáner
         * @param checkP Booleano que determina que se han cumplido con los requerimientos de la contraseña
         * @param checkD Booleano que determina si la fecha de nacimiento introducida es válida
         * @param id Número identificador del usuario
         * @param newUser Constructor de objeto de la clase
         * @see Person
         */
        Scanner sc = new Scanner(System.in);
        String name, birthdate, password;
        boolean checkP = false, checkD = false;
        System.out.println("Please enter your name and surnames");
        name = sc.nextLine();

            System.out.println("Please enter your password");
            password = sc.nextLine();
            checkP = checkPassword(password);
            while (!checkP) {
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
            while (!checkD) {
                System.out.println("The date you entered is incorrect, please try again");
                System.out.println("Remember to use the following format: dd/mm/yyyy");
                birthdate = sc.nextLine();
                checkD = checkDate(birthdate);
            }
            ArrayList<Person> personsArray = dataAccess.chargeData();
            ArrayList<Person> clientArray = new ArrayList<>();
            for(Person person : personsArray) {
                if(person instanceof User){
                    clientArray.add(person);
                }
            }
            int id = 1;
            if(!clientArray.isEmpty()) {
                for (Person customer : clientArray) {
                    if (customer.getId().equals(String.valueOf(id))) {
                        id++;
                    }
                }
            }
            userId = String.valueOf(id);
            User newUser = new User(name, password, birthdate, userId);
            System.out.println("The register process has ended successfully");
            System.out.println("Your data:");
            System.out.println("Name: " + name);
            System.out.println("Birthdate: " + birthdate);
            System.out.println("Password: " + password);
            System.out.println("Id: " + id);
            return newUser;
        }

    /**
     * Valida la fecha de nacimiento del usuario.
     * @param date Fecha en formato String.
     * @return true si la fecha es válida; false en caso contrario.
     */
    @Override
    public boolean checkDate(String date) {
        String regex = "[,\\.\\s]";
        String[] myArray = date.split(regex);
        int element1 = Integer.parseInt(myArray[0]);
        int element2 = Integer.parseInt(myArray[1]);
        int element3 = Integer.parseInt(myArray[2]);
        int year = Year.now().getValue();

            if (element1 > 32 || element1 < 0) {//check if the day is between 1 and 31
                return false;
            }
            if (element2 == 4 || element2 == 6 || element2 == 9 || element2 == 11) {//check if it is a 30-day month
                if (element1 > 30) {
                    return false;
                }
            }
            if (element2 == 2) { //check if february
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
     * Valida la contraseña mediante una expresión regular.
     * Requiere: 1 Mayúscula, 1 Minúscula, 1 Número, 1 Carácter especial y longitud mín. de 8.
     * @param password Contraseña a verificar.
     * @return true si cumple el patrón.
     */
    @Override
    public boolean checkPassword(String password) { //regex password

        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if (password.matches(pattern)) {
            return true;
        } else {
            return false;
        }
    }

    //Getters y Setters

    /** @return Identificador único del usuario. */
    @Override
    public String getId() {
        return userId;
    }

    /** @param active Estado de activación de la cuenta. */
    public void setActive ( boolean active){
        this.active = active;
    }

    /** @return Nombre completo del usuario. */
    public String getName () {
        return name;
    }

    /** @return Lista de todas las cuentas bancarias asociadas. */
    public ArrayList<BankAccount> getBankAccounts () {
        return bankAccounts;
    }
}