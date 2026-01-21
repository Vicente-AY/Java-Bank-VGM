package Access;
import Person.User;
import Person.Employee;
import Person.Gerente;

import java.util.Objects;
import Person.*;
import Utils.Data;
import java.util.Scanner;
import Account.*;
import java.util.ArrayList;
import Utils.*;

public class AccessScreen {
    ArrayList<User> usersC = new ArrayList<User>();
    ArrayList<Employee> usersE = new ArrayList<Employee>();
    ArrayList<Gerente> usersG = new ArrayList<Gerente>();
    Data  writeUsers = new Data();
    Scanner sc = new Scanner(System.in);
    ArrayList<User> users = new ArrayList<User>();
    String id="";
    User dummyUserC = new User(null, null, null, null);
    Employee dummyUserE = new Employee(null, null, null, null);
    Gerente dummyUserG = new Gerente(null, null, null, null);
    User dummyUser = new User(null, null, null, null);
    Data dataAccess = new Data();


    public void menu(){
        users = dataAccess.readUsers();
        int option=0;
        option = sc.nextInt();
        while(option!=3){
            System.out.println("Welcome to JavaBank ");
            System.out.println("1. Create Account");
            System.out.println("2. Log In");
            System.out.println("3. Close Application");
            System.out.println("Please enter your numbered choice (1, 2 or 3)");
            option = sc.nextInt();
            switch (option){
                case 1:
                    User newUser = dummyUser.register();
                    users.add(newUser);
                    writeUsers.writeUsers(users);
                    identificador();
                    break;
                case 2:
                    identificadorlogin();
                    break;
                case 3:
                    return;
            }
        }

    }
    public void identificador(){

        int option=0;
        while (option!=3){
            System.out.println("Que tipo de usuario eres?");
            System.out.println("1. Cliente");
            System.out.println("2. Empleado");
            System.out.println("3. Gerente");
            System.out.println("4. Ir atrás");
            System.out.println("Please enter your numbered choice (1, 2, 3 or 4)");
            option = sc.nextInt();
            switch (option){
                case 1:
                    User newUser = dummyUserC.register();
                    usersC.add(newUser);
                    break;
                case 2:
                    Employee newEmployee = dummyUserE.register();
                    usersE.add(newEmployee);
                    break;
                case 3:
                    Gerente newGerente = dummyUserG.register();
                    usersG.add(newGerente);
                    break;
                case 4:
                    return;
            }
        }
    }

    public void identificadorlogin(){

        int option=0;
        while (option!=3){
            System.out.println("Que tipo de usuario eres?");
            System.out.println("1. Cliente");
            System.out.println("2. Empleado");
            System.out.println("3. Gerente");
            System.out.println("4. Ir atrás");
            System.out.println("Please enter your numbered choice (1, 2, 3 or 4)");
            option = sc.nextInt();
            sc.nextLine();
            switch (option){
                case 1:
                    loginC();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    loginG();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

    }

    public void accountMenu(User currentUser){
        int option=0;
        System.out.println("Welcome " + currentUser.name);
        System.out.println("1. Create BankAccount");
        System.out.println("2. Make a deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer Money");
        System.out.println("5. Recharge SIM card");
        System.out.println("6. Log Out");
        System.out.println("Please enter your numbered choice (1, 2, 3, 4, 5 or 6)");
        Scanner scanner = new Scanner(System.in);
        option = scanner.nextInt();

        while(option!=6){
            switch (option){
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
            }
        }
    }

    public void accountMenu(Employee currentEmployee){
        int option=0;
        System.out.println("Welcome " + currentEmployee.name);
        System.out.println("1. Create BankAccount");
        System.out.println("2. Make a deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer Money");
        System.out.println("5. Recharge SIM card");
        System.out.println("6. Log Out");
        System.out.println("Please enter your numbered choice (1, 2, 3, 4, 5 or 6)");
        while(option!=6){
            switch (option){
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
            }
        }
    }

    public void accountMenu(Gerente currentGerente){
        int option=0;
        System.out.println("Welcome " + currentGerente.name);
        System.out.println("1. Create BankAccount");
        System.out.println("2. Make a deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer Money");
        System.out.println("5. Recharge SIM card");
        System.out.println("6. Log Out");
        System.out.println("Please enter your numbered choice (1, 2, 3, 4, 5 or 6)");
        while(option!=6){
            switch (option){
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
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    public void loginC(){
        System.out.println("Please enter user id: ");
        id = sc.nextLine();
        User currentUser =  null;
        for (int i = 0; i < usersC.size(); i++) {
            if(usersC.get(i).id.equals(id)){
                currentUser = usersC.get(i);
            }
        }
        if (currentUser == null){
            System.out.println("Stated id is not found, please enter a valid id");
            return;
        }
        else{
            if(!currentUser.active){
                System.out.println("The account associated with this id is blocked.\n Contact a system admin for more information.");
            }
            else{
                int tries = 0;
                while (tries != 3){
                    System.out.println("Please enter password: ");
                    String pass = sc.nextLine();
                    if(pass.equals(currentUser.password)){
                        System.out.println("You have successfully logged in");
                        accountMenu(currentUser);
                    }
                    else{
                        System.out.println("Wrong password, please try again");
                        tries++;
                        if(tries == 3){
                            System.out.println("You have failed to log in, you account has been blocked.\n Please contact a system admin to resolve this issue.");
                            currentUser.active = false;
                        }
                    }
                }
            }
        }
    }

    public BankAccount bankAcoountCreation(User currentUser){
        CreditAccount dummyCreditAccount = new CreditAccount(null, null, null, null, null, 0.0, 0.0);
        DebitAccount dummyDebitAccount = new DebitAccount(null, null, null, null, null, null);
        System.out.println("Select the type of bank account you want to create: ");
        System.out.println("1. Debit Account");
        System.out.println("2. Credit Account");
        Scanner scan = new Scanner(System.in);
        int option = scan.nextInt();
        while(true){
            switch (option){
                case 1:
                    DebitAccount newDebitAccount = dummyDebitAccount.createDebitAccount(dummyDebitAccount, currentUser);
                case 2:
                    CreditAccount newCreditAccount = dummyCreditAccount.createCreditAccount(dummyCreditAccount, currentUser);
                default:
                    System.out.println("Invalid option. Please try again.");
            }

        }
    }

    public void loginE(){
        System.out.println("Please enter user id: ");
        id = sc.nextLine();
        Employee currentEmployee =  null;
        for (int i = 0; i < usersE.size(); i++) {
            if(Objects.equals(usersE.get(i).id, id)){
                currentEmployee =  usersE.get(i);
            }
        }
        if (currentEmployee == null){
            System.out.println("Stated id is not found, please enter a valid id");
            return;
        }
        else{
            if(!currentEmployee.active){
                System.out.println("The account associated with this id is blocked.\n Contact a system admin for more information.");
            }
            else{
                int tries = 0;
                while (tries != 3){
                    System.out.println("Please enter password: ");
                    String pass = sc.nextLine();
                    if(pass.equals(currentEmployee.password)){
                        System.out.println("You have successfully logged in");
                        accountMenu(currentEmployee.register());
                    }
                    else{
                        System.out.println("Wrong password, please try again");
                        tries++;
                        if(tries == 3){
                            System.out.println("You have failed to log in, you account has been blocked.\n Please contact a system admin to resolve this issue.");
                            currentEmployee.active = false;
                        }
                    }
                }

            }

        }
    }

    public void loginG(){
        System.out.println("Please enter user id: ");
        id = sc.nextLine();
        Gerente currentGerente =  null;
        for (int i = 0; i < usersG.size(); i++) {
            if(Objects.equals(usersG.get(i).id, id)){
                currentGerente =  usersG.get(i);
            }
        }
        if (currentGerente == null){
            System.out.println("Stated id is not found, please enter a valid id");
            return;
        }
        else{
            if(!currentGerente.active){
                System.out.println("The account associated with this id is blocked.\n Contact a system admin for more information.");
            }
            else{
                int tries = 0;
                while (tries != 3){
                    System.out.println("Please enter password: ");
                    String pass = sc.nextLine();
                    if(pass.equals(currentGerente.password)){
                        System.out.println("You have successfully logged in");
                        accountMenu(currentGerente.register());
                    }
                    else{
                        System.out.println("Wrong password, please try again");
                        tries++;
                        if(tries == 3){
                            System.out.println("You have failed to log in, you account has been blocked.\n Please contact a system admin to resolve this issue.");
                            currentGerente.active = false;
                        }
                    }
                }

            }

        }
    }

}
