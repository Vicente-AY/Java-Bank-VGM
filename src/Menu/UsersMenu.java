package Menu;
import Person.Person;
import Account.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que gestiona el menú interactivo para los usuarios
 * Permite al usuario final administrar sus cuentas bancarias
 */
public class UsersMenu {

    /**
     * Proporciona acceso a las funcionalidades disponibles para el cliente.
     * @param currentUser El cliente que ha iniciado sesión.
     */
    public void menuAccess(Person currentUser, ArrayList<Person> persons){

        int option = 0;
        while (true) {
            System.out.println("Welcome " + currentUser.name);
            System.out.println("1. Create BankAccount");
            System.out.println("2. Make a deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Money");
            System.out.println("5. Recharge SIM card");
            System.out.println("6. Request Card");
            System.out.println("7. Log Out");
            System.out.println("Please enter your numbered choice (1, 2, 3, 4, 5, 6 or 7)");
            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    bankAcoountCreation(currentUser);
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
                case 7:
                    return;
                    default: System.out.println("Opción no valida");
                    break;
            }
        }
    }
    /**
     * Gestiona el submenú de creación de nuevas cuentas bancarias.
     * Permite al usuario elegir entre una cuenta de débito o una de crédito,
     * @param currentUser El usuario al que se le asignará la nueva cuenta creada.
     */
    public BankAccount bankAcoountCreation(Person currentUser) {
        CreditAccount dummyCreditAccount = new CreditAccount(null, null, null, null, null, null, 0.0, 0.0);
        DebitAccount dummyDebitAccount = new DebitAccount(null, null, null, null, null, null);
        System.out.println("Select the type of bank account you want to create: ");
        System.out.println("1. Debit Account");
        System.out.println("2. Credit Account");
        Scanner scan = new Scanner(System.in);
        int option = scan.nextInt();
        while (true) {
            switch (option) {
                case 1:
                    dummyDebitAccount.createDebitAccount(dummyDebitAccount, currentUser);
                case 2:
                    dummyCreditAccount.createCreditAccount(dummyCreditAccount, currentUser);
                default:
                    System.out.println("Invalid option. Please try again.");
            }

        }
    }
}

