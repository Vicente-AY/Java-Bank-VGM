package Access;
import Menu.*;
import java.awt.*;
import Person.*;

import java.util.InputMismatchException;
import java.util.Scanner;
import Account.*;
import java.util.ArrayList;
import Utils.*;

/**
 * Clase principal de acceso que gestiona el login y registro
 */
public class AccessScreen {
    ArrayList<Person> personsArray = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    String id="";
    User dummyUserC = new User(null, null, null, null);
    Employee dummyUserE = new Employee(null, null, null, null);
    Gerente dummyUserG = new Gerente(null, null, null, null);
    Data dataAccess = new Data();
    UsersMenu menuUser = new UsersMenu();
    EmployeeMenu menuEmpployee = new EmployeeMenu();
    ManagerMenu menuManager = new ManagerMenu();

    /**
     * Inicia la interfaz de usuario principal.
     */
    public void menu(){
        //Carga los datos en un ArrayList
        personsArray = dataAccess.chargeData();
        int option=0;
        while (option != 3) {
            try {
                System.out.println("Welcome to JavaBank ");
                System.out.println("1. Create Account");
                System.out.println("2. Log In");
                System.out.println("3. Close Application");
                System.out.println("Please enter your numbered choice (1, 2 or 3)");
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        dummyUserC.register(personsArray);
                        break;
                    case 2:
                        login(personsArray);
                        break;
                    case 3:
                        dataAccess.saveData(personsArray);
                        return;
                    default:
                        System.out.println("Invalid option. Please try again");
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.err.println("Error please introduce a number");
                sc.nextLine();
                option = 0;
            }
        }
    }

    /**
     * Gestiona el acceso de usuarios mediante ID y contraseña.
     * Valida si el usuario existe, si está activo y controla el número de intentos.
     * * @param personsArray Lista de personas registradas
     */
    public void login(ArrayList<Person> personsArray) {
        System.out.println("Please enter user ID: ");
        id = sc.nextLine();
        Person currentPerson = null;
        for (int i = 0; i < personsArray.size(); i++) {
            if (id.equals(personsArray.get(i).getId())) {
                currentPerson = personsArray.get(i);
                break;
            }
        }
        if (currentPerson == null) {
            System.out.println("Stated ID is not found, please enter a valid id");
        }
        else {
            if (!currentPerson.active) {
                System.out.println("The account associated with this ID is blocked.\n Contact a system admin for more information.");
            }
            else {
                int tries = 0;
                while (tries != 3) {
                    System.out.println("Please enter password: ");
                    String pass = sc.nextLine();
                    if (pass.equals(currentPerson.password)) {
                        System.out.println("You have successfully logged in");
                        if(currentPerson instanceof User){
                            menuUser.menuAccess(currentPerson, personsArray);
                            return;
                        }
                        else if(currentPerson instanceof Employee){
                            menuEmpployee.menuAccess(currentPerson, personsArray);
                            return;
                        }
                        else{
                            menuManager.menuAccess(currentPerson, personsArray);
                            return;
                        }

                    }
                    else {
                        System.out.println("Wrong password, please try again");
                        tries++;
                        if (tries == 3) {
                            System.out.println("You have failed to log in, you account has been blocked.\n Please contact a system admin to resolve this issue.");
                            currentPerson.active = false;
                        }
                    }
                }
            }
        }
    }
}