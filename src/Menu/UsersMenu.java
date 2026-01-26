package Menu;
import Person.*;
import Account.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que gestiona el menú interactivo para los usuarios
 * Permite al usuario final administrar sus cuentas bancarias
 */
public class UsersMenu {
    DebitAccount dummyDebitAccount = new DebitAccount(null, null, null, null, null, null);
    BankAccount selectedBankAccount = null;

    /**
     * Proporciona acceso a las funcionalidades disponibles para el cliente.
     * @param currentUser El cliente que ha iniciado sesión.
     */
    public void menuAccess(Person currentUser, ArrayList<Person> persons){

        int option = 0;
        while (true) {
            System.out.println("Welcome " + currentUser.name);
            System.out.println("1. Select a BankAccount");
            System.out.println("2. Make a deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Money");
            System.out.println("5. Recharge SIM card");
            System.out.println("6. Log Out");
            System.out.println("Please enter your numbered choice (1, 2, 3, 4, 5 or 6)");
            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();
            switch (option) {
                case 1:

                    return;
                case 2:
                    return;
                case 3:
                    return;
                case 4:
                    return;
                case 5:
                    return;
                case 6:
                    return;
                default: System.out.println("Opción no valida");
                break;
            }
        }
    }
}

