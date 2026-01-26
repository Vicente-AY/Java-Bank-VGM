package Menu;
import java.util.Scanner;
import Person.*;
import Person.Gerente;

import java.util.ArrayList;

/**
 * Clase encargada de gestionar la interfaz de usuario y las operaciones
 * exclusivas para los usuarios con rol de Gerente (Manager).
 */
public class ManagerMenu {

    Scanner scanner = new Scanner(System.in);
    ArrayList<Person> persons = new ArrayList<>();
    /**
     * Proporciona acceso al menú administrativo del gerente.
     * Permite la gestión de cuentas bancarias
     * con privilegios elevados.
     * @param currentManager El Gerente que ha iniciado sesión.
     */
    public void menuAccess(Person currentManager, ArrayList<Person> persons) {

        while (true) {
            System.out.println("Welcome " + currentManager.name);
            System.out.println("1. Create Users");
            System.out.println("2. Delete Users");
            System.out.println("3. Create Bank Account");
            System.out.println("4. Delete Bank Account");
            System.out.println("5. Reactivate Account");
            System.out.println("6. Log Out");
            System.out.println("Please enter your numbered choice (1, 2, 3, 4, 5 or 6)");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    menuCreate();
                    break;
                case 2:
                    menuDelete();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    menuReactivate();
                case 6:
                    System.out.println("Login out");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;

            }
        }
    }

    public void menuCreate(){
        while (true) {
            System.out.println("Which User Create");
            System.out.println("1. Create Employee");
            System.out.println("2. Create Manager");
            System.out.println("3. Return");
            System.out.println("Please enter your numbered choice (1, 2, or 3");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    CreateEmployee(persons);
                    break;
                case 2:
                    CreateManager(persons);
                    break;
                case 3:
                    System.out.println("Return");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;

            }
        }
    }

    public void menuDelete(){
        while (true) {
            System.out.println("Which user Delete");
            System.out.println("1. Delete Employee");
            System.out.println("2. Delete Manager");
            System.out.println("3. Return");
            System.out.println("Please enter your numbered choice (1, 2, or 3");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    DeleteEmployee(persons);
                    break;
                case 2:
                    DeleteManager(persons);
                    break;
                case 3:
                    System.out.println("Return");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;

            }
        }
    }

    public void menuReactivate(){
        while (true) {
            System.out.println("Which user reactivate");
            System.out.println("1. Reactivate Client");
            System.out.println("2. Reactivate Employee");
            System.out.println("3. Reactivate Manager");
            System.out.println("4. Return");
            System.out.println("Please enter your numbered choice (1, 2, 3 or 4");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    ReactivateClientAccount(persons);
                    break;
                case 2:
                    ReactivateEmployee(persons);
                    break;
                case 3:
                    ReactivateManager(persons);
                    break;
                case 4:
                    System.out.println("Return");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;

            }
        }
    }
}