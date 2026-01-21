package Person;
import Account.BankAccount;

import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase con los metodos que el usuario puede utilizar
 */
public class User extends Person {
    /**
     * @param id Número identificador del usuario
     * @param bankAccounts Lista con las cuentas bancarias del usuario
     */
    public String id = "";
    public ArrayList<BankAccount> bankAccounts = new ArrayList<>();

    public User(String name, String password, String birthDate, String id) {
        /**
         * Constructor con parámetros
         * @param id Cadena con el número de identificación del usuario
         * @see Person
         */
        public User(String name, String password, String birthDate, String id) {
            super(name, password, birthDate);
            this.active = true;
            this.id = id;
        }

        /**
         * Metodo para realizar el registro del usuario
         * @see Person
         */
    }
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
            checkPassword(password);
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
            id = id + 1;
            User newUser = new User(name, password, birthdate, id);
            System.out.println("The register process has ended");
            System.out.println("Your data:");
            System.out.println("Name: " + name);
            System.out.println("Birthdate: " + birthdate);
            System.out.println("Password: " + password);
            System.out.println("Id: " + id);
            return newUser;
        }

        /**
         * Metodo que comprueba la validez de la fecha con la que el usuario interactua
         * @see Person
         */
        @Override
        public boolean checkDate (String date){
            /**
             * @param regex Variable que representa el formato en el que hay que introducir la fecha
             * @param myArray Array que gestiona las partes de la fecha
             * @param element1 Entero que representa el número asignado al día
             * @param element2 Entero que representa el número asignado al mes
             * @param element3 Entero que representa el número asignado al año
             * @param year Año actual
             */
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
         * Metodo que comprueba la validez de la contraseña introducida por el usuario
         * @see Person
         */
        @Override
        public boolean checkPassword (String password){ //regex password
            /**
             * @param pattern Cadena con los caracteres que se pueden escribir en el campo de contraseña
             */
            String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
            if (password.matches(pattern)) {
                return true;
            } else {
                return false;
            }
        }


        @Override
        public String getId() {
            return id;
        }


        public void setActive ( boolean active){
            this.active = active;
        }

        public String getName () {
            return name;
        }

        public ArrayList<BankAccount> getBankAccounts () {
            return bankAccounts;
        }


        public boolean canCreateAccount() {
            return false; // Los clientes NO pueden crear cuentas
        }


        public boolean canDeleteAccount() {
            return false; // Los clientes NO pueden borrar cuentas
        }


        public double getWithdrawalLimit () {
            return 1000.0; // Máximo 1000€
        }

}