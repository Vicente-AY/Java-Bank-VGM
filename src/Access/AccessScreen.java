package Access;
import Person.User;
import Person.Employee;
import Person.Gerente;
import Menu.*;

import java.awt.*;
import java.util.Objects;
import Person.*;
import Utils.Data;
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
        while(option!=3){
            System.out.println("Welcome to JavaBank ");
            System.out.println("1. Create Account");
            System.out.println("2. Log In");
            System.out.println("3. Close Application");
            System.out.println("Please enter your numbered choice (1, 2 or 3)");
            option = sc.nextInt();
            sc.nextLine();
            switch (option){
                case 1:
                    registration();
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
    }

    /**
     * Gestiona el proceso de registro de nuevos usuarios.
     * Permite elegir entre Cliente, Empleado o Gerente y guarda los cambios.
     */
    public void registration() {

        //Cada vez que se registra satisfactoriamente se actualiza el archivo con los nuevos datos
        int option = 0;
        while (option != 3) {
            System.out.println("Welcome to the registration Process");
            System.out.println("Please type the type of user");
            System.out.println("1. Client");
            System.out.println("2. Employee");
            System.out.println("3. Manager");
            System.out.println("4. Back");
            System.out.println("Please enter your numbered choice (1, 2, 3 or 4)");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    User newUser = dummyUserC.register();
                    personsArray.add(newUser);
                    break;
                case 2:
                    Employee newEmployee = dummyUserE.register();
                    personsArray.add(newEmployee);
                    break;
                case 3:
                    Gerente newGerente = dummyUserG.register();
                    personsArray.add(newGerente);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option. Please try again");
                    break;
            }
        }
    }

    /**
     * Gestiona el acceso de usuarios mediante ID y contraseña.
     * Valida si el usuario existe, si está activo y controla el número de intentos.
     * * @param personsArray Lista de personas registradas
     */
    public void login(ArrayList<Person> personsArray) {
        System.out.println("Please enter user id: ");
        id = sc.nextLine();
        Person currentPerson = null;
        for (int i = 0; i < personsArray.size(); i++) {
            if (personsArray.get(i).getId().equals(id)) {
                currentPerson = personsArray.get(i);
            }
        }
        if (currentPerson == null) {
            System.out.println("Stated id is not found, please enter a valid id");
            return;
        } else {
            if (!currentPerson.active) {
                System.out.println("The account associated with this id is blocked.\n Contact a system admin for more information.");
            } else {
                int tries = 0;
                while (tries != 3) {
                    System.out.println("Please enter password: ");
                    String pass = sc.nextLine();
                    if (pass.equals(currentPerson.password)) {
                        System.out.println("You have successfully logged in");
                        if(currentPerson instanceof User){
                            menuUser.menuAccess(currentPerson);
                        }
                        else if(currentPerson instanceof Employee){
                            menuEmpployee.menuAccess(currentPerson);
                        }
                        else{
                            menuManager.menuAccess(currentPerson);
                        }

                    } else {
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