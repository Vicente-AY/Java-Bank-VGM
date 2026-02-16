package Menu;
import java.util.InputMismatchException;
import java.util.Scanner;
import Account.*;
import Person.*;
import Person.Gerente;
import Shop.ShopItem;
import Utils.Data;
import java.util.ArrayList;

/**
 * Clase encargada de gestionar la interfaz de usuario y las operaciones
 * exclusivas para los usuarios con rol de Gerente (Manager).
 */
public class ManagerMenu {

    Data dataAccess = new Data();
    Employee dummyEmployee = new Employee(null, null, null, null);
    User dummyUser = new User (null, null, null, null);
    Gerente dummyManager = new Gerente(null, null, null, null);
    Scanner scanner = new Scanner(System.in);
    BankAccount dummyDebitAcount = new DebitAccount(null, null, null, null, null, null);
    BankAccount dummyCreditAcount = new CreditAccount(null, null, null, null, null, null, 0.0);

    /**
     * Proporciona acceso al menú administrativo del gerente.
     * Permite la gestión de cuentas bancarias
     * con privilegios elevados.
     * @param currentManager El Gerente que ha iniciado sesión.
     */
    public void menuAccess(Person currentManager, ArrayList<Person> persons) {

        ArrayList<ShopItem> shopItems = dataAccess.chargeItems();
        int option = 0;
        while (true) {
            try {
                System.out.println("Welcome to the Manger´s Menu \n" + currentManager.name);
                System.out.println("1. Create Users | 2. Delete Users | 3. Create BankAccount");
                System.out.println("4. Delete Bank Account | 5. Reativate Account | 6 List of Users");
                System.out.println("7. Create Card | 8. Manage Shop");
                System.out.println("9. Log Out");
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        menuCreateUsers(persons);
                        break;
                    case 2:
                        ((Gerente) currentManager).deleteSystemAccount(persons);
                        break;
                    case 3:
                        createNewBankAccount(persons);
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
                        createCard(persons);
                        break;
                    case 8:
                        manageShop(shopItems);
                        break;
                    case 9:
                        System.out.println("Login out");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;

                }
            }
            catch (InputMismatchException e) {
                System.out.println("Error please introduce a number");
                scanner.nextLine();
                option = 0;
            }
        }
    }

    /**
     * Menú que permite la creación de los diferentes tipos de usuarios
     * @param persons listado de todos los usuarios del sistema
     */
    public void menuCreateUsers(ArrayList<Person> persons){

        int option = 0;
        while (true) {
            try {
                System.out.println("Which User Create");
                System.out.println("1. Create new User | 2. Create new Employee | 3. Create new Manager");
                System.out.println("4. Back");
                option = scanner.nextInt();
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
            catch (InputMismatchException e) {
                System.out.println("Error please introduce a number");
                scanner.nextLine();
                option = 0;
            }
        }
    }

    /**
     * Metodo que permite crear nuevas cuentas bancarias para los usuarios del banco
     * @param persons listado de los usuarios del sistema
     */
    public void createNewBankAccount(ArrayList<Person> persons){

        int option = 0;
        while(true) {
            try {
                System.out.println("Which Bank Account type do yo want to create?");
                System.out.println("1. Create a new Debit Account");
                System.out.println("2. Create a new Credit Account");
                System.out.println("3. Back");
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        DebitAccount.createDebitAccount(persons);
                        break;
                    case 2:
                        CreditAccount.createCreditAccount(persons);
                        break;
                    case 3:
                        System.out.println("Cancelling new Bank Account creation");
                        return;
                    default:
                        System.out.println("Please enter a valid option");
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Error please introduce a number");
                scanner.nextLine();
                option = 0;
            }
        }
    }

    /**
     * Metodo que muestra todos los usuarios del banco
     * @param persons listado ocn los usuarios del sistema
     */
    public void listOfPeople(ArrayList<Person> persons){

        int option = 0;
        while(true) {
            try {
                System.out.println("What type of user do you want to list?");
                System.out.println("1. Users | 2. Employees | 3. Managers");
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
                        break;
                    case 2:
                        System.out.println("- - - - List of Employees - - -");
                        for (Person person : persons) {
                            if (person instanceof Employee) {
                                System.out.println(person.getId() + " " + person.getName());
                                System.out.println("- - - - - - ");
                            }
                        }
                        break;
                    case 3:
                        System.out.println("- - - - List of Managers - - -");
                        for (Person person : persons) {
                            if (person instanceof Gerente) {
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
            catch (InputMismatchException e) {
                System.out.println("Error please introduce a number");
                scanner.nextLine();
                option = 0;
            }
        }
    }

    /**
     * Metodo que permite la creación de tarjetas enlazandolas con la cuenta bancaria del usuario
     * @param persons lista con los usuarios del sistema
     */
    public void createCard(ArrayList<Person> persons){

        User destinationUser = null;
        BankAccount accountForCard = null;
        System.out.println("Please enter the id of the user the card is for");
        String id = scanner.nextLine();

        for(Person person : persons){
            if(id.equals(person.getId()) && person instanceof User){
                destinationUser = (User) person;
                break;
            }
        }
        if(destinationUser == null){
            System.out.println("The id is not correct or doest not correspond to an User");
            return;
        }

        if(destinationUser.getBankAccounts().isEmpty()){
            System.out.println("The user has no bank accounts");
            return;
        }

        System.out.println("Select the account the Card will associate with");
        for(int i = 0; i < destinationUser.getBankAccounts().size(); i++){
            System.out.println("Option " + (i+1) + " " + destinationUser.getBankAccounts().get(i).getAccNumber());
        }

        int option = scanner.nextInt();
        scanner.nextLine();

        accountForCard = destinationUser.getBankAccounts().get(option -1);

        Card.createNewCard(accountForCard, destinationUser.getName());
    }

    /**
     * Metodo que permite el manejo de los articulos de la tienda la tienda
     * @param shopItems listado con los articulos de la tienda
     */
    public void manageShop(ArrayList<ShopItem> shopItems){

        ShopItem.createItem(shopItems);
        dataAccess.saveItems(shopItems);
    }
}
