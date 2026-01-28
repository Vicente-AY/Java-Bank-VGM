package Menu;
import Account.*;
import Person.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Clase encargada de gestionar la interfaz de usuario para los empleados.
 */
public class EmployeeMenu {

    BankAccount dummyDebitAcount = new DebitAccount(null, null, null, null, null, null);
    BankAccount dummyCreditAcount = new CreditAccount(null, null, null, null, null, null, 0.0, 0.0);
    private transient Scanner scanner = new Scanner(System.in);

    /**
     * Muestra el menú de opciones disponibles para un empleado y gestiona sus acciones.
     * * @param currentEmployee El Empleado que ha iniciado sesión.
     */
    public void menuAccess(Person currentEmployee, ArrayList<Person> persons){

        while (true) {
            System.out.println("Welcome to the Employee´s Menu \n" + currentEmployee.name);
            System.out.println("1. Delete BankAccount");
            System.out.println("2. Create a new Bank Account");
            System.out.println("3. Reactivate client Account");
            System.out.println("4. Delete User");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    ((Employee) currentEmployee).deleteBankAccount(persons);
                    break;
                case 2:
                    createNewBankAccount(persons, currentEmployee);
                case 3:
                    ((Employee) currentEmployee).reactivate(persons);
                    break;
                case 4:
                    ((Employee) currentEmployee).deleteUser(persons);
                default:
                    System.out.println("Please enter a valid choice");
                    break;
            }
        }
    }

    public void createNewBankAccount(ArrayList<Person> persons, Person currentEmployee){
        while(true) {
            System.out.println("Enter a valid option");
            System.out.println("1. Create a new Debit Account");
            System.out.println("2. Create a new Credit Account");
            System.out.println("3. Back");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    ((DebitAccount) dummyDebitAcount).createDebitAccount(persons);
                    return;
                case 2:
                    ((CreditAccount) dummyCreditAcount).createCreditAccount(persons);
                    return;
                case 3:
                    System.out.println("Cancelling new Bank Account creation");
                    return;
                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
        }
    }
}
