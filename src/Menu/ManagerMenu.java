package Menu;
import java.util.Scanner;
import Person.*;

import java.util.ArrayList;

/**
 * Clase encargada de gestionar la interfaz de usuario y las operaciones
 * exclusivas para los usuarios con rol de Gerente (Manager).
 */
public class ManagerMenu {

    Scanner scanner = new Scanner(System.in);

    /**
     * Proporciona acceso al menú administrativo del gerente.
     * Permite la gestión de cuentas bancarias
     * con privilegios elevados.
     * @param currentManager El Gerente que ha iniciado sesión.
     */
    public void menuAccess(Person currentManager, ArrayList<Person> persons) {

        while (true) {
            System.out.println("Welcome " + currentManager.name);
            System.out.println("1. See list of persons");
            System.out.println("2. Log Out");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    ((Gerente) currentManager).listOfClients(persons);
                    break;
                case 2:
                    System.out.println("Loggin out");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;

            }
        }
    }
}
