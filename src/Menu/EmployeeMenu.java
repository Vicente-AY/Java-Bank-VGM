package Menu;

import Person.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Clase encargada de gestionar la interfaz de usuario para los empleados.
 */
public class EmployeeMenu {

    Scanner scanner = new Scanner(System.in);

    /**
     * Muestra el menú de opciones disponibles para un empleado y gestiona sus acciones.
     * * @param currentEmployee El Empleado que ha iniciado sesión.
     */
    public void menuAccess(Person currentEmployee, ArrayList<Person> persons){

        while (true) {
            System.out.println("Welcome " + currentEmployee.name);
            System.out.println("1. Delete BankAccount");
            System.out.println("2. Reactivate a client Account");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    ((Employee) currentEmployee).DeleteBankAccount(persons);
                    break;
                case 2:
                    ((Employee) currentEmployee).ReactivateClientAccount(persons);
                    break;
                default:
                    System.out.println("Please enter a valid choice");
                    break;
            }
        }
    }
}
