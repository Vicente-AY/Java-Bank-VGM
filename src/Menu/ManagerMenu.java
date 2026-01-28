package Menu;
import java.util.Scanner;

import Account.BankAccount;
import Account.CreditAccount;
import Account.DebitAccount;
import Person.*;
import Person.Gerente;

import java.util.ArrayList;

/**
 * Clase encargada de gestionar la interfaz de usuario y las operaciones
 * exclusivas para los usuarios con rol de Gerente (Manager).
 */
public class ManagerMenu {
    Employee dummyEmployee = new Employee(null, null, null, null);
    User dummyUser = new User (null, null, null, null);
    Gerente dummyManager = new Gerente(null, null, null, null);
    Scanner scanner = new Scanner(System.in);
    BankAccount dummyDebitAcount = new DebitAccount(null, null, null, null, null, null);
    BankAccount dummyCreditAcount = new CreditAccount(null, null, null, null, null, null, 0.0, 0.0);

    /**
     * Proporciona acceso al menú administrativo del gerente.
     * Permite la gestión de cuentas bancarias
     * con privilegios elevados.
     * @param currentManager El Gerente que ha iniciado sesión.
     */
    public void menuAccess(Person currentManager, ArrayList<Person> persons) {

        while (true) {
            System.out.println("Welcome to the Manger´s Menu \n" + currentManager.name);
            System.out.println("1. Create Users");
            System.out.println("2. Delete Users");
            System.out.println("3. Create Bank Account");
            System.out.println("4. Delete Bank Account");
            System.out.println("5. Reactivate Account");
            System.out.println("6. List of Users");
            System.out.println("7. Log Out");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    menuCreateUsers(persons);
                    break;
                case 2:
                    ((Gerente) currentManager).deleteSystemAccount(persons);
                    break;
                case 3:
                    createNewBankAccount(persons, currentManager);
                    break;
                case 4:
                    ((Gerente) currentManager).deleteBankAccount(persons);
                    break;
                case 5:
                    ((Gerente) currentManager).reactivate(persons);
                    break;
                case 6:
                    listOfPeople(persons);
                    break;
                case 7:
                    System.out.println("Login out");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;

            }
        }
    }

    public void menuCreateUsers(ArrayList<Person> persons){
        while (true) {
            System.out.println("Which User Create");
            System.out.println("1. Create new User");
            System.out.println("2. Create new Employee");
            System.out.println("3. Create new Manager");
            System.out.println("4. Back");
            System.out.println("Please enter your numbered choice (1, 2, 3 or 4");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    dummyUser.register(persons);
                    break;
                case 2:
                    dummyEmployee.register(persons);
                    break;
                case 3:
                    dummyManager.register(persons);
                    break;
                case 4:
                    System.out.println("Closing Menu");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    public void createNewBankAccount(ArrayList<Person> persons, Person currentManager){
        while(true) {
            System.out.println("Enter a valid option");
            System.out.println("1. Create a new Debit Account");
            System.out.println("2. Create a new Credit Account");
            System.out.println("3. Back");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    ((DebitAccount) dummyDebitAcount).createDebitAccount(persons);
                    break;
                case 2:
                    ((CreditAccount) dummyCreditAcount).createCreditAccount(persons);
                    break;
                case 3:
                    System.out.println("Cancelling new Bank Account creation");
                    return;
                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
        }
    }

    public void listOfPeople(ArrayList<Person> persons){
        while(true) {
            System.out.println("What type of user do you want to list?");
            System.out.println("1. Users");
            System.out.println("2. Employees");
            System.out.println("3. Managers");
            System.out.println("4. Back");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    System.out.println("- - - List of clients - - -");
                    for(Person person : persons) {
                        if(person instanceof User){
                            System.out.println(person.getId() + " " + person.getName());
                            System.out.println("- - - - - - ");
                        }
                    }
                    break;
                case 2:
                    System.out.println("- - - - List of Employees - - -");
                    for(Person person : persons) {
                        if(person instanceof Employee){
                            System.out.println(person.getId() + " " + person.getName());
                            System.out.println("- - - - - - ");
                        }
                    }
                    break;
                case 3:
                    System.out.println("- - - - List of Managers - - -");
                    for(Person person : persons) {
                        if(person instanceof Gerente){
                            System.out.println(person.getId() + " " + person.getName());
                            System.out.println("- - - - - - ");
                        }
                    }
                    break;
                case 4:
                    System.out.println("Returning");
                    return;
                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
        }
    }
}
