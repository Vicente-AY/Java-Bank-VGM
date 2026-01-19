package Access;
import Person.User;

import java.util.Scanner;

import java.util.ArrayList;

/**
 * Clase que maneja el funcionamiento del programa
 */
public class AccessScreen {
    /**
     * @param users ArrayList que almacena los usuarios registrados en el programa
     * @param sc Variable que llama al escaner
     * @param id Numero de identificación del usuario
     * @param dummyUser Constructor de objeto para crear un usuario de prueba
     * @see User
     * @see Person
     */
    ArrayList<User> users = new ArrayList<User>();
    Scanner sc = new Scanner(System.in);
    String id="";
    User dummyUser = new User(null, null, null, null);

    /**
     * Metodo que muestra la interfaz del menú principal del programa
     */
    public void menu(){
/**
 * @param option Entero que almacena la opción seleccionada
 * @see User
 */
        int option=0;
        while(option!=3){
            System.out.println("Welcome to JavaBank ");
            System.out.println("1. Create Account");
            System.out.println("2. Log In");
            System.out.println("3. Close Application");
            System.out.println("Please enter your numbered choice (1, 2 or 3)");
            option = sc.nextInt();
            switch (option){
                case 1:
                    User newUser = dummyUser.register();
                    users.add(newUser);

                    break;
                case 2:
                    login();
                    break;
                case 3:
                    return;
            }
        }

    }

    /**
     * Metodo que muestra la interfaz del menú de cuentas del usuario
     * @param currentUser Usuario actual que está usando el programa
     */
    public void accountMenu(User currentUser){
        /**
         * @param option Entero que almacena la opción seleccionada
         */
        int option=0;
        System.out.println("Welcome " + currentUser.name);
        System.out.println("1. Create BankAccount");
        System.out.println("2. Make a deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer Money");
        System.out.println("5. Recharge SIM card");
        System.out.println("6. Log Out");
        System.out.println("Please enter your numbered choice (1, 2, 3, 4, 5 or 6)");
        while(option!=6){
            switch (option){
                case 1:
                  //bankAccount  newBA = new bankAccount(dummyBankAccount.getEntity(), dummyBankAccount.getOffice(),  dummyBankAccount.calcDC(), null, null, null);
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    return;
                case 4:
                    return;
                case 5:
                    return;
                case 6:
                    return;
            }
        }
    }

    /**
     * Metodo que permite al usuario iniciar sesión en la cuenta
     */
    public void login(){
        /**
         * @param tries Variable que representa el número de intentos que tiene el usuario para iniciar sesión
         * @param pass Variable que comprueba si la contraseña introducida coincide con la del usuario
         */
        System.out.println("Please enter user id: ");
        id = sc.nextLine();
        User currentUser =  null;
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).id.equals(id)){
                currentUser =  users.get(i);
            }
        }
        if (currentUser == null){
            System.out.println("Stated id is not found, please enter a valid id");
            return;
        }
        else{
            if(!currentUser.active){
                System.out.println("The account associated with this id is blocked.\n Contact a system admin for more information.");
            }
            else{
                int tries = 0;
                while (tries != 3){
                    System.out.println("Please enter password: ");
                    String pass = sc.nextLine();
                    if(pass.equals(currentUser.password)){
                        System.out.println("You have successfully logged in");
                        accountMenu(currentUser);
                    }
                    else{
                        System.out.println("Wrong password, please try again");
                        tries++;
                        if(tries == 3){
                            System.out.println("You have failed to log in, you account has been blocked.\n Please contact a system admin to resolve this issue.");
                            currentUser.active = false;
                        }
                    }
            }

            }

        }
    }
}
