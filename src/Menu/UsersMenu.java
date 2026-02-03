package Menu;
import Person.*;
import Account.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que gestiona el menú interactivo para los usuarios
 * Permite al usuario final administrar sus cuentas bancarias
 */
public class UsersMenu {
    BankAccount selectedBankAccount = null;
    Scanner sc = new Scanner(System.in);
    double amount;

    /**
     * Proporciona acceso a las funcionalidades disponibles para el cliente.
     * @param currentUser El cliente que ha iniciado sesión.
     * @param persons Lista de clientes de la aplicacion
     */
    public void menuAccess(Person currentUser, ArrayList<Person> persons){
        double outstandingDebt = 0;
        boolean userDebtor = ((User) currentUser).getDebtor();
        boolean userBloquedAccounts = ((User) currentUser).getBloquedAccounts();

        for(BankAccount bankAccount : ((User) currentUser).getBankAccounts()){
            if(bankAccount.getBalance() < 0){
                outstandingDebt += selectedBankAccount.getBalance();
            }
        }

        selectedBankAccount = null;
        //Imprimimos el menú, si tiene cuenta seleccionada la mostramos por consola también
        int option = 0;
        while (true) {
            try {
                System.out.println("Welcome " + currentUser.name);
                if (selectedBankAccount != null) {
                    if (selectedBankAccount instanceof DebitAccount) {
                        System.out.println("Selected account: " + selectedBankAccount.accNumber + " Balance: " + selectedBankAccount.balance + " Type: Debit Account");
                    } else {
                        System.out.println("Selected account: " + selectedBankAccount.accNumber + " Balance: " + selectedBankAccount.balance + " Type: Credit Account");
                    }
                }
                if(userDebtor && outstandingDebt < 0){
                    System.out.println("0. PAY DEBT");
                }
                System.out.println("1. Select a BankAccount");
                System.out.println("2. Make a deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Transfer Money");
                System.out.println("5. Recharge SIM card");
                System.out.println("6. See Bank Account History");
                System.out.println("7. Log Out");

                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        selectedBankAccount = selectAccount((User) currentUser);
                        break;
                    case 2, 3, 5:
                        if (selectedBankAccount == null) {
                            System.out.println("Please select and account first");
                            break;
                        }
                        if(userBloquedAccounts){
                            System.out.println("Your accounts have been bloqued. Please contact an Employee");
                            break;
                        }
                        if(userDebtor && selectedBankAccount instanceof CreditAccount && option != 2){
                            System.out.println("Your account has credit suspended. Please contact an Employee");
                            break;
                        }
                        System.out.println("Enter the amount you want to perform the operation");
                        amount = sc.nextDouble();
                        sc.nextLine();
                        if(option == 2) {
                            selectedBankAccount.deposit(amount);
                        }
                        if(option == 3) {
                            selectedBankAccount.withdraw(amount);
                        }
                        if(option == 5) {
                            selectedBankAccount.rechargeSIM(amount);
                        }
                        break;
                    case 4:
                        if(userBloquedAccounts){
                            System.out.println("Your accounts have been bloqued. Please contact an Employee");
                            break;
                        }
                        if(userDebtor && selectedBankAccount instanceof CreditAccount){
                            System.out.println("Your account has credit suspended. Contact an Employee");
                            break;
                        }
                        if (selectedBankAccount == null) {
                            System.out.println("Please select and account first");
                            break;
                        }
                        selectedBankAccount.transfer(persons);
                        break;
                    case 6:
                        if (selectedBankAccount == null) {
                            System.out.println("Please select and account first");
                            break;
                        }
                        bankAccountHistory(selectedBankAccount);
                        break;
                    case 7:
                        return;
                    case 0:
                        if(!userDebtor){
                            System.out.println("Invalid option");
                            break;
                        }
                        else{
                            System.out.println("Do you want to pay with a deposito or with a transfer?");
                            selectedBankAccount.payDebts(currentUser);
                            break;
                        }
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.err.println("Please enter a valid number");
                sc.nextLine();
                option = 0;
            }
            catch (NullPointerException e) {
                System.err.println("System Error");
            }
            catch (Exception e) {
                System.err.println("Unexpected Error");
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
        if(user.bankAccounts.isEmpty()){
            System.out.println("You need to create a bank account first. Contact an Employee");
            return null;
        }
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
        while(true) {
            try {
                int option = sc.nextInt();
                sc.nextLine();
                //seleccionamos la cuenta que el usuario quiere utilizar
                foundBankAccount = user.bankAccounts.get(option - 1);
                if (foundBankAccount instanceof DebitAccount) {
                    System.out.println("Selected account: " + foundBankAccount.accNumber + " Balance: " + foundBankAccount.balance + " Type: Debit Account");
                    break;
                } else {
                    System.out.println("Selected account: " + foundBankAccount.accNumber + " Balance: " + foundBankAccount.balance + " Type: Credit Account. Limit: " + -((CreditAccount) foundBankAccount).getCreditLimit());
                    break;
                }
            }
            catch (InputMismatchException e) {
                System.err.println("Error please introduce a number");
            }
            catch (IndexOutOfBoundsException e) {
                System.err.println("Error please introduce a valid option");
            }
        }
        return foundBankAccount;
    }

    public void bankAccountHistory(BankAccount bankAccount) {

        if (bankAccount.getHistory().isEmpty()) {
            System.out.println("This account does not have any history yet");
        }
        else {
            System.out.println("- - - Bank Account History - - -");
            String headerFormat = "%-20s | %16s | %-30s | %12s | %12s%n";
            String rowFormat = "%-20s | %16.2f | %-30s | %12.2f | %12.2f%n";
            System.out.printf(headerFormat, "Date", "Previous Balance", "Operation Type", "Amount", "Balance");
            for (BankAccountHistory history : bankAccount.getHistory()) {
                String operation = history.getOperationType();
                if (history.getDestinationAccount() != null) {
                    operation += ": " + history.getDestinationAccount().getAccNumber();
                }
                    System.out.printf(rowFormat,
                            history.getTransactionDate(),
                            history.getPreviousBalance(),
                            operation,
                            history.getTransactionAmount(),
                            history.getNewBalance());
                }
            System.out.println("- - - - - - - - - - - - -  - - -");
        }
    }
}