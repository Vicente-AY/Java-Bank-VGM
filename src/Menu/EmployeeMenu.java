package Menu;
import Account.*;
import Person.*;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Clase encargada de gestionar la interfaz de usuario para los empleados.
 */
public class EmployeeMenu {

    BankAccount dummyDebitAcount = new DebitAccount(null, null, null, null, null, null);
    BankAccount dummyCreditAcount = new CreditAccount(null, null, null, null, null, null, 0.0);
    Scanner scanner = new Scanner(System.in);

    /**
     * Muestra el menú de opciones disponibles para un empleado y gestiona sus acciones.
     * * @param currentEmployee El Empleado que ha iniciado sesión.
     */
    public void menuAccess(Person currentEmployee, ArrayList<Person> persons){

        int option = 0;
        while (true) {
            try {
                System.out.println("Welcome to the Employee´s Menu \n" + currentEmployee.name);
                System.out.println("1. Delete BankAccount");
                System.out.println("2. Create a new Bank Account");
                System.out.println("3. Reactivate client Account");
                System.out.println("4. Delete User");
                System.out.println("5. List of Clients");
                System.out.println("6. Log off");
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        ((Employee) currentEmployee).deleteBankAccount(persons);
                        break;
                    case 2:
                        createNewBankAccount(persons, currentEmployee);
                        break;
                    case 3:
                        ((Employee) currentEmployee).reactivate(persons);
                        break;
                    case 4:
                        ((Employee) currentEmployee).deleteUser(persons);
                        break;
                    case 5:
                        listOfPeople(persons);
                        break;
                    case 6:
                        System.out.println("Logging off");
                        return;
                    default:
                        System.out.println("Please enter a valid choice");
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.err.println("Error please introduce a number");
                scanner.nextLine();
                option = 0;
            }
        }
    }

    public void createNewBankAccount(ArrayList<Person> persons, Person currentEmployee){
        int option = 0;
        while(true) {
            try {
                System.out.println("Enter a valid option");
                System.out.println("1. Create a new Debit Account");
                System.out.println("2. Create a new Credit Account");
                System.out.println("3. Back");
                option = scanner.nextInt();
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
            catch (InputMismatchException e) {
                System.err.println("Error please introduce a number");
                scanner.nextLine();
                option = 0;
            }
        }
    }


    public void listOfPeople(ArrayList<Person> persons){
        int option = 0;
        while(true) {
            try {
                System.out.println("What type of user do you want to list?");
                System.out.println("1. Users");
                System.out.println("2. Employees");
                System.out.println("3. Managers");
                System.out.println("4. Back");
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        System.out.println("- - - List of clients - - -");
                        for (Person person : persons) {
                            if (person instanceof User) {
                                System.out.println(person.getId() + " " + person.getName());
                                System.out.println("- - - - - - ");
                            }
                        }
                    case 2:
                        System.out.println("- - - - List of Employees - - -");
                        for (Person person : persons) {
                            if (person instanceof Employee) {
                                System.out.println(person.getId() + " " + person.getName());
                                System.out.println("- - - - - - ");
                            }
                        }
                    case 3:
                        System.out.println("- - - - List of Managers - - -");
                        for (Person person : persons) {
                            if (person instanceof Gerente) {
                                System.out.println(person.getId() + " " + person.getName());
                                System.out.println("- - - - - - ");
                            }
                        }
                    case 4:
                        System.out.println("Returning");
                        return;
                    default:
                        System.out.println("Please enter a valid option");
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.err.println("Error please introduce a number");
                scanner.nextLine();
                option = 0;
            }
        }
    }
}