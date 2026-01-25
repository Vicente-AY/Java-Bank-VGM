package Menu;

import Person.Person;
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
            System.out.println("1. Create BankAccount");
            System.out.println("2. Make a deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Money");
            System.out.println("5. Recharge SIM card");
            System.out.println("6. Log Out");
            System.out.println("Please enter your numbered choice (1, 2, 3, 4, 5 or 6)");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    //bankAccount  newBA = new bankAccount(dummyBankAccount.getEntity(), dummyBankAccount.getOffice(),  dummyBankAccount.calcDC(), null, null, null);
                    break;
                case 2:

                    break;
                case 3:
                    return;
                case 4:
                    return;
                case 5:
                    return;
                case 6:
                    return;
                default:
                    System.out.println("Please enter a valid choice");
                    break;
            }
        }
    }
}
