package Menu;
import Person.*;
import Account.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que gestiona el menú interactivo para los usuarios
 * Permite al usuario final administrar sus cuentas bancarias
 */
public class UsersMenu {
    DebitAccount dummyDebitAccount = new DebitAccount(null, null, null, null, null, null);
    BankAccount selectedBankAccount = null;
    Scanner sc = new Scanner(System.in);
    double amount;

    /**
     * Proporciona acceso a las funcionalidades disponibles para el cliente.
     * @param currentUser El cliente que ha iniciado sesión.
     */
    public void menuAccess(Person currentUser, ArrayList<Person> persons){

        //Imprimimos el menú, si tiene cuenta seleccionada la mostramos por consola también
        int option = 0;
        while (true) {
            System.out.println("Welcome " + currentUser.name);
            if(selectedBankAccount != null){
                if(selectedBankAccount instanceof DebitAccount) {
                    System.out.println("Selected account: " + selectedBankAccount.accNumber + " Balance: " + selectedBankAccount.balance + " Type: Debit Account");
                }
                else {
                    System.out.println("Selected account: " + selectedBankAccount.accNumber + " Balance: " + selectedBankAccount.balance + " Type: Credit Account");
                }
            }
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
                    selectedBankAccount = selectAccount((User) currentUser);
                    break;
                case 2:
                    if(selectedBankAccount == null){
                        System.out.println("Pleas select and account first");
                        selectedBankAccount = selectAccount((User) currentUser);
                    }
                    System.out.println("Enter the amount you want to Deposit");
                    amount = sc.nextDouble();
                    sc.nextLine();
                    selectedBankAccount.deposit(amount);
                    break;
                case 3:
                    if(selectedBankAccount == null){
                        System.out.println("Pleas select and account first");
                        selectedBankAccount = selectAccount((User) currentUser);
                    }
                    System.out.println("Enter the amount you want to Withdraw");
                    amount = sc.nextDouble();
                    sc.nextLine();
                    selectedBankAccount.withdraw(amount);
                    break;
                case 4:
                    if(selectedBankAccount == null){
                        System.out.println("Pleas select and account first");
                        selectedBankAccount = selectAccount((User) currentUser);
                    }
                    selectedBankAccount.transfer(persons);
                    break;
                case 5:
                    if(selectedBankAccount == null){
                        System.out.println("Pleas select and account first");
                        selectedBankAccount = selectAccount((User) currentUser);
                    }
                    System.out.println("Enter the amount you want to Recharge");
                    amount = sc.nextDouble();
                    sc.nextLine();
                    selectedBankAccount.rechargeSIM(amount);
                    break;
                case 6:
                    return;
                default: System.out.println("Opción no valida");
                break;
            }
        }
    }

    /**
     * Muestra las cuentas vinculadas a un usuario y permite seleccionar una para operar.
     * @param user El usuario cliente cuya cuenta se desea seleccionar.
     */
    public BankAccount selectAccount(User user) {

        //mostramos por pantalla las cuentas bancarias asociadas para que el usuario pueda seleccionarla
        BankAccount foundBankAccount = null;
        System.out.println("Select the account you want to use by typing the number of the option");
        for(int i = 0; i < user.bankAccounts.size(); i++) {
            String aliasBA = user.bankAccounts.get(i).accountAlias;
            if(user.bankAccounts.get(i) instanceof DebitAccount){
                System.out.println("Option " + (i + 1) + ": " + aliasBA + " Type: Debit Account Balance: " + user.getBankAccounts().get(i).getBalance());
            }
            else{
                System.out.println("Option " + (i + 1) + ": " + aliasBA + " Type: Credit Account Balance: " + user.getBankAccounts().get(i).getBalance());
            }
        }
        try {
            int option = sc.nextInt();
            sc.nextLine();
            //seleccionamos la cuenta que el usuario quiere utilizar
            foundBankAccount = user.bankAccounts.get(option - 1);
            if(foundBankAccount instanceof DebitAccount){
                System.out.println("Selected account: " + foundBankAccount.accNumber + " Balance: " + foundBankAccount.balance + " Type: Debit Account");
            }
            else{
                System.out.println("Selected account: " + foundBankAccount.accNumber + " Balance: " + foundBankAccount.balance + " Type: Credit Account");
            }
        }
        catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return foundBankAccount;
    }
}